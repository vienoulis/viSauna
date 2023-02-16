package ru.vienoulis.visauna.model.callback;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import ru.vienoulis.visauna.dto.PriceSlot;

@Data
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode()
public class PriseSlotAndHoursCQD implements CallbackQueryData {
    PriceSlot priceSlot;
    int hours;
}
