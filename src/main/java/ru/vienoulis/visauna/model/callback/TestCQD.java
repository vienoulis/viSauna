package ru.vienoulis.visauna.model.callback;

import lombok.AllArgsConstructor;
import lombok.Data;
import ru.vienoulis.visauna.model.CallbackQueryTypes;

@Data
@AllArgsConstructor
public class TestCQD implements CallbackQueryData {
    int number;
    String someString;
    CallbackQueryTypes callbackQueryTypes;
}
