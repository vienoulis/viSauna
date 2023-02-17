package ru.vienoulis.visauna.model.callback;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.vienoulis.visauna.model.CallbackQueryTypes;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode()
public class CalculateHallCQD implements CallbackQueryData {
    CallbackQueryTypes hallType;
}
