package com.heydancer.service.enums;

public enum ServiceCommands {
    HELP("/help"),
    REGISTRATION("/registration"),
    START("/start"),
    CANCEL("/cancel");

    private final String value;

    ServiceCommands(String value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }


    public static ServiceCommands fromValue(String v) {
        for (ServiceCommands c : ServiceCommands.values()) {
            if (c.value.equals(v)) {
                return c;
            }
        }
        return null;
    }
}
