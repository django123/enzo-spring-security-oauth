package com.enzo.enums;

public enum EnumCivility {

    MALE(0),
    FEMALE(1),
    FEMALE_(2);

    private int code;

    EnumCivility(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public static EnumCivility fromId(int id) {
        for (EnumCivility type : values()) {
            if (type.getCode() == id) {
                return type;
            }
        }
        return null;
    }


}
