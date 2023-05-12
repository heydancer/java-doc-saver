package com.heydancer.service;

import org.telegram.telegrambots.meta.api.objects.Message;

public interface FileService {
    void processPhoto(Message telegramMessage);
}
