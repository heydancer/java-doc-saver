package com.heydancer.utils;

public final class ResponseMessage {
    public static final String ERROR_FIRST_NAME_MESSAGE = "Некорректный формат имени." +
            "Имя должно быть написано на русском языке, а первая буква в верхнем регистре.";
    public static final String ERROR_LAST_NAME_MESSAGE = "Некорректный формат фамилии. " +
            "Фамилия должна быть написана на русском языке, а первая буква в верхнем регистре.";
    public static final String ERROR_EMAIL_MESSAGE = "Некорректный формат электронной почты.";
    public static final String ERROR_SUBDIVISION_MESSAGE = "Некорректный формат подразделения. " +
            "Название подразделения должно быть написано на русском языке, а первая буква в верхнем регистре.";
    public static final String ERROR_LINK_MESSAGE = "Некорректный формат звена. " +
            "Если вы работаете в дневную смену введите число соотвествующее вашему звену, если в ночную введите: ночь";
    public static final String ERROR_UNKNOWN_STATE_MESSAGE = "Неизвестная ошибка, введите /cancel и попробуйте снова!";
    public static final String ERROR_PHOTO_UPLOAD_MESSAGE = "К сожалению, загрузка фото не удалась. Повторите попытку позже.";
    public static final String ERROR_REGISTRATION_UPLOAD_MESSAGE = "Только зарегистрированный пользователь может отправлять фотографии.";
    public static final String ERROR_REGISTRATION_OR_ACTIVATE_MESSAGE = "Зарегистрируйтесь или активируйте свою учетную запись.";
    public static final String NOTIFICATION_WAITING_FOR_CONFIRMATION_MESSAGE = "Ожидайте подтверждение администратором.";
    public static final String NOTIFICATION_REGISTRATION_MESSAGE = "Зарегистрируйтесь.";
    public static final String NOTIFICATION_FIRST_NAME_MESSAGE = "Введите ваше имя на русском языке:";
    public static final String NOTIFICATION_LAST_NAME_MESSAGE = "Введите вашу фамилию на русском языке:";
    public static final String NOTIFICATION_EMAIL_MESSAGE = "Введите электронную почту привязанную к вашему ARM:";
    public static final String NOTIFICATION_LINK_MESSAGE = "Введите звено в котором вы работает:";
    public static final String NOTIFICATION_SUBDIVISION_MESSAGE = "Введите название подразделения в котором работаете";
    public static final String NOTIFICATION_RE_REGISTRATION_MESSAGE = "Вы уже зарегистрированы!";
    public static final String NOTIFICATION_SUCCESSFUL_UPLOAD_MESSAGE = "Документ успешно загружен!";
    public static final String NOTIFICATION_COMMAND_CANCEL_MESSAGE = "Команда отмененена.";
    public static final String NOTIFICATION_NO_COMMANDS_MESSAGE = "В данный момент нет запущеных команд для отмены. " +
            "Для просмотра доступных команда используйте: /help  ";
    public static final String NOTIFICATION_PHOTO_UPLOAD_MESSAGE = "Загрузите фотографию заявки на услугу с подписью клиента " +
            "или воспользуйтесь командной: /help";
    public static final String NOTIFICATION_COMMAND_LIST_MESSAGE = "Приветствую! Чтобы смотреть список доступных команд введите: /help";
    public static final String NOTIFICATION_AVAILABLE_COMMAND_MESSAGE = "Список доступных команд: \n" +
            "/cancel - отмена выполнения текущей команды \n" +
            "/registration - регистрация пользователя. ";






}
