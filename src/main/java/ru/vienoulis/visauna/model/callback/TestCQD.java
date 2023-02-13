package ru.vienoulis.visauna.model.callback;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.vienoulis.visauna.model.CallbackQueryTypes;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode()
public class TestCQD implements CallbackQueryData {
    int number;
    String someString;
    CallbackQueryTypes callbackQueryTypes;
}


