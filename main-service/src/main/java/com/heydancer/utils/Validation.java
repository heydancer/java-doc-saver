package com.heydancer.utils;

public final class Validation {
    public static final String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
            "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

    public static final String NAME_PATTERN = "^[А-ЯЁ][а-яё]+$";

    public static final String LINK_PATTERN = "^(1[0-2]|[1-9]|ночь)$";
}
