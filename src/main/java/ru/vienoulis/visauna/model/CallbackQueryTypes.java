package ru.vienoulis.visauna.model;

import lombok.Getter;

@Getter
public enum CallbackQueryTypes {
    BIG_HALL("Большой"), SMALL_HALL("Малый");
    private String hallBtnName;

    CallbackQueryTypes(String hallBtnName) {
        this.hallBtnName = hallBtnName;
    }
}
