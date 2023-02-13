package ru.vienoulis.visauna.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import ru.vienoulis.visauna.model.callback.Action;

@Data
@AllArgsConstructor
@Builder(toBuilder = true)
public class ShortCallbackData {
    Action action;
    long key;
}
