package com.heydancer.service.impl;

import com.heydancer.MoverRepository;
import com.heydancer.dto.MoverDTO;
import com.heydancer.entity.Mover;
import com.heydancer.exception.NotFoundException;
import com.heydancer.mapper.MoverMapper;

import com.heydancer.service.MoverService;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.List;

import static com.heydancer.entity.enums.MoverState.BASIC_STATE;
import static com.heydancer.entity.enums.MoverState.CONFIRMED;
import static com.heydancer.entity.enums.MoverState.WAITING_FOR_ADMIN_CONFIRMATION;
import static com.heydancer.model.RabbitQueue.ANSWER_MESSAGE;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MoverServiceImpl implements MoverService {
    private final MoverRepository moverRepository;
    private final RabbitTemplate rabbitTemplate;
    private final MoverMapper moverMapper;

    @Override
    public List<MoverDTO> getAllConfirmed() {
        List<Mover> movers = moverRepository.findMoversByState(CONFIRMED);
        return moverMapper.toDTOList(movers);
    }

    @Override
    public List<MoverDTO> getMoversInConfirmationStatus() {
        List<Mover> movers = moverRepository.findMoversByState(WAITING_FOR_ADMIN_CONFIRMATION);
        return moverMapper.toDTOList(movers);
    }

    @Override
    @Transactional
    public void changeRegistrationState(Long moverId, Boolean status) {
        Mover mover = check(moverId);

        if (status) {
            confirm(mover);
            sendMessage(mover.getTelegramId(), "Регистрация завершена");
        } else {
            reset(mover);
            sendMessage(mover.getTelegramId(), "Регистрация отклонена, проверьте корректность данных");
        }

        moverMapper.toDTO(mover);
    }

    @Override
    public void replyToAll(String message) {
        moverRepository.findMoversByState(CONFIRMED)
                .forEach(mover -> sendMessage(mover.getTelegramId(), message));
    }

    @Override
    public List<MoverDTO> getAllByLastName(String filter) {
        List<Mover> movers = moverRepository.findByLastName(filter.toLowerCase());

        return moverMapper.toDTOList(movers);
    }

    @Override
    @Transactional
    public void save(Mover mover, String firstName, String lastName, String email, String link, String subdivision) {
        mover.setFirstName(firstName);
        mover.setLastName(lastName);
        mover.setEmail(email);
        mover.setSubdivision(subdivision);
        mover.setLink(link);

        moverRepository.save(mover);
        sendMessage(mover.getTelegramId(), "Ваши данные были изменены: \n" +
                mover.getFirstName() + "\n" +
                mover.getLastName() + "\n" +
                mover.getEmail() + "\n" +
                mover.getSubdivision() + " " + mover.getLink());

    }


    private void sendMessage(long chatId, String text) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText(text);

        rabbitTemplate.convertAndSend(ANSWER_MESSAGE, sendMessage);
    }

    private void confirm(Mover mover) {
        mover.setIsActive(true);
        mover.setState(CONFIRMED);
    }

    private void reset(Mover mover) {
        mover.setFirstName(null);
        mover.setLastName(null);
        mover.setEmail(null);
        mover.setLink(null);
        mover.setSubdivision(null);
        mover.setIsActive(false);
        mover.setState(BASIC_STATE);
    }

    private Mover check(long moverId) {
        return moverRepository.findById(moverId)
                .orElseThrow(() -> new NotFoundException(String.format("Mover not found. Mover id: %s", moverId)));
    }
}
