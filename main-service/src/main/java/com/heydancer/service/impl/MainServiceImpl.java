package com.heydancer.service.impl;

import com.heydancer.MoverRepository;
import com.heydancer.entity.Mover;
import com.heydancer.entity.Stats;
import com.heydancer.entity.enums.MoverState;
import com.heydancer.exception.UploadFileException;
import com.heydancer.repository.StatsRepository;
import com.heydancer.service.FileService;
import com.heydancer.service.MainService;
import com.heydancer.service.ProducerService;
import com.heydancer.service.enums.ServiceCommands;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;

import java.util.Optional;
import java.util.regex.Pattern;

import static com.heydancer.entity.enums.MoverState.BASIC_STATE;
import static com.heydancer.entity.enums.MoverState.CONFIRMED;
import static com.heydancer.entity.enums.MoverState.WAITING_FOR_ADMIN_CONFIRMATION;
import static com.heydancer.service.enums.ServiceCommands.CANCEL;
import static com.heydancer.service.enums.ServiceCommands.HELP;
import static com.heydancer.service.enums.ServiceCommands.REGISTRATION;
import static com.heydancer.service.enums.ServiceCommands.START;
import static com.heydancer.utils.ResponseMessage.ERROR_EMAIL_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_FIRST_NAME_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_LAST_NAME_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_LINK_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_PHOTO_UPLOAD_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_REGISTRATION_OR_ACTIVATE_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_REGISTRATION_UPLOAD_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_SUBDIVISION_MESSAGE;
import static com.heydancer.utils.ResponseMessage.ERROR_UNKNOWN_STATE_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_AVAILABLE_COMMAND_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_COMMAND_CANCEL_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_COMMAND_LIST_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_EMAIL_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_FIRST_NAME_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_LAST_NAME_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_LINK_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_NO_COMMANDS_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_PHOTO_UPLOAD_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_REGISTRATION_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_RE_REGISTRATION_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_SUBDIVISION_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_SUCCESSFUL_UPLOAD_MESSAGE;
import static com.heydancer.utils.ResponseMessage.NOTIFICATION_WAITING_FOR_CONFIRMATION_MESSAGE;
import static com.heydancer.utils.Validation.EMAIL_PATTERN;
import static com.heydancer.utils.Validation.LINK_PATTERN;
import static com.heydancer.utils.Validation.NAME_PATTERN;

@Log4j
@Service
@RequiredArgsConstructor
public class MainServiceImpl implements MainService {
    private final StatsRepository statsRepository;
    private final MoverRepository moverRepository;
    private final ProducerService producerService;
    private final FileService fileService;

    @Override
    public void processTextMessage(Update update) {
        saveStats(update);

        Mover mover = getMover(update);
        MoverState userState = mover.getState();

        String text = update.getMessage().getText();

        ServiceCommands cmd = ServiceCommands.fromValue(text);
        String output;


        if (CANCEL.equals(cmd)) {
            output = cancelProcess(mover);

        } else if (BASIC_STATE.equals(userState)) {
            output = processServiceCommand(mover, cmd);

        } else if (WAITING_FOR_ADMIN_CONFIRMATION.equals(userState)) {
            output = buildUser(mover, text);

        } else if (CONFIRMED.equals(userState)) {
            output = processServiceCommand(mover, cmd);

        } else {
            log.error(String.format("Unknown state: %s", userState));

            output = ERROR_UNKNOWN_STATE_MESSAGE;
        }

        long chatId = update.getMessage().getChatId();
        sendAnswer(output, chatId);
    }

    @Override
    public void processPhotoMessage(Update update) {
        saveStats(update);

        Mover mover = getMover(update);
        long chatId = update.getMessage().getChatId();

        if (isMoverVerification(chatId, mover)) {
            return;
        }

        try {
            fileService.processPhoto(update.getMessage());
            sendAnswer(NOTIFICATION_SUCCESSFUL_UPLOAD_MESSAGE, chatId);
        } catch (UploadFileException exception) {
            log.error(exception);

            sendAnswer(ERROR_PHOTO_UPLOAD_MESSAGE, chatId);
        }
    }

    private void sendAnswer(String output, long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(output);

        producerService.producerAnswer(sendMessage);
    }

    private String processServiceCommand(Mover mover, ServiceCommands cmd) {
        if (REGISTRATION.equals(cmd)) {
            return registerMover(mover);

        } else if (HELP.equals(cmd)) {
            return help();

        } else if (START.equals(cmd)) {
            return NOTIFICATION_COMMAND_LIST_MESSAGE;

        } else if (BASIC_STATE.equals(mover.getState()))
            return NOTIFICATION_REGISTRATION_MESSAGE;

        else {
            return NOTIFICATION_PHOTO_UPLOAD_MESSAGE;
        }
    }

    private boolean isMoverVerification(long chatId, Mover mover) {
        MoverState state = mover.getState();

        if (!mover.getIsActive()) {
            sendAnswer(ERROR_REGISTRATION_OR_ACTIVATE_MESSAGE, chatId);
            return true;

        } else if (!CONFIRMED.equals(state)) {
            sendAnswer(ERROR_REGISTRATION_UPLOAD_MESSAGE, chatId);
            return true;
        }

        return false;
    }

    private String cancelProcess(Mover mover) {
        if (mover.getState().equals(WAITING_FOR_ADMIN_CONFIRMATION)) {
            mover.setFirstName(null);
            mover.setLastName(null);
            mover.setEmail(null);
            mover.setLink(null);
            mover.setState(BASIC_STATE);
            moverRepository.save(mover);

            return NOTIFICATION_COMMAND_CANCEL_MESSAGE;
        }

        return NOTIFICATION_NO_COMMANDS_MESSAGE;
    }

    private String help() {
        return NOTIFICATION_AVAILABLE_COMMAND_MESSAGE;
    }

    private String registerMover(Mover mover) {
        if (mover.getIsActive()) {
            return NOTIFICATION_RE_REGISTRATION_MESSAGE;
        } else if (mover.getFirstName() != null && mover.getLastName() != null && mover.getEmail() != null) {
            return NOTIFICATION_WAITING_FOR_CONFIRMATION_MESSAGE;
        }

        mover.setState(WAITING_FOR_ADMIN_CONFIRMATION);
        moverRepository.save(mover);

        return NOTIFICATION_FIRST_NAME_MESSAGE;
    }

    private String buildUser(Mover telegramUser, String text) {
        if (telegramUser.getFirstName() != null && telegramUser.getLastName() != null && telegramUser.getEmail() != null &&
                telegramUser.getLink() != null && telegramUser.getSubdivision() != null) {

            return NOTIFICATION_WAITING_FOR_CONFIRMATION_MESSAGE;

        } else if (telegramUser.getFirstName() == null) {
            if (Pattern.matches(NAME_PATTERN, text)) {
                telegramUser.setFirstName(text);
                moverRepository.save(telegramUser);
            } else {
                return ERROR_FIRST_NAME_MESSAGE;
            }

            return NOTIFICATION_LAST_NAME_MESSAGE;

        } else if (telegramUser.getLastName() == null) {
            if (Pattern.matches(NAME_PATTERN, text)) {
                telegramUser.setLastName(text);
                moverRepository.save(telegramUser);
            } else {
                return ERROR_LAST_NAME_MESSAGE;
            }

            return NOTIFICATION_EMAIL_MESSAGE;

        } else if (telegramUser.getEmail() == null) {
            if (Pattern.matches(EMAIL_PATTERN, text)) {
                telegramUser.setEmail(text);
                moverRepository.save(telegramUser);
            } else {
                return ERROR_EMAIL_MESSAGE;
            }

            return NOTIFICATION_LINK_MESSAGE;

        } else if (telegramUser.getLink() == null) {
            if (Pattern.matches(LINK_PATTERN, text)) {
                telegramUser.setLink(text);
                moverRepository.save(telegramUser);
            } else {
                return ERROR_LINK_MESSAGE;
            }

            return NOTIFICATION_SUBDIVISION_MESSAGE;

        } else {
            if (Pattern.matches(NAME_PATTERN, text)) {
                telegramUser.setSubdivision(text);
                moverRepository.save(telegramUser);
            } else {
                return ERROR_SUBDIVISION_MESSAGE;
            }
        }

        return NOTIFICATION_WAITING_FOR_CONFIRMATION_MESSAGE;
    }

    private Mover getMover(Update update) {
        User telegramUser = update.getMessage().getFrom();
        Optional<Mover> optionalMover = moverRepository.findByTelegramId(telegramUser.getId());

        return optionalMover.orElseGet(() -> saveMover(update));
    }

    private Mover saveMover(Update update) {
        User telegramUser = update.getMessage().getFrom();
        Mover newMover = Mover.builder()
                .telegramId(telegramUser.getId())
                .isActive(false)
                .state(BASIC_STATE)
                .build();

        return moverRepository.save(newMover);
    }

    private void saveStats(Update update) {
        Stats stats = Stats.builder()
                .event(update)
                .build();

        statsRepository.save(stats);
    }
}
