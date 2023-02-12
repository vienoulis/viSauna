package ru.vienoulis.visauna.model.callback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import ru.vienoulis.visauna.model.CallbackQueryTypes;

@Data
@Builder(toBuilder = true)
@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
public class TestCQD extends CallbackQueryData {
    int number;
    String someString;
    CallbackQueryTypes callbackQueryTypes;
}


