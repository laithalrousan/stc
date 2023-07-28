package com.stc.stc.utils;

public enum PermissionLevel {
    EDIT(1),
    VIEW(2);
    private final int value;
    PermissionLevel(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
