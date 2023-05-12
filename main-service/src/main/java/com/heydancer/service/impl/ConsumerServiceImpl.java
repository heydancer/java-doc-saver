package com.heydancer.service.impl;

import com.heydancer.service.ConsumerService;
import com.heydancer.service.MainService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.Update;

import static com.heydancer.model.RabbitQueue.PHOTO_MESSAGE_UPDATE;
import static com.heydancer.model.RabbitQueue.TEXT_MESSAGE_UPDATE;

@Log4j
@Service
@RequiredArgsConstructor
public class ConsumerServiceImpl implements ConsumerService {
    private final MainService mainService;

    @Override
    @RabbitListener(queues = TEXT_MESSAGE_UPDATE)
    public void consumeTextMessageUpdates(Update update) {
        log.debug("NODE: text message is received");

        mainService.processTextMessage(update);
    }

    @Override
    @RabbitListener(queues = PHOTO_MESSAGE_UPDATE)
    public void consumePhotoMessageUpdates(Update update) {
        log.debug("NODE: photo message is received");


        mainService.processPhotoMessage(update);
    }
}
