package ru.vienoulis.visauna.model.callback;

import lombok.Getter;

public enum Action {
    test(TestCQD.class);
    @Getter
    Class<? extends CallbackQueryData> dataClass;

    Action(Class<? extends CallbackQueryData> dataClass) {
        this.dataClass = dataClass;
    }
}
