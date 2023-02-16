package ru.vienoulis.visauna.model.callback;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.vienoulis.visauna.dto.PriceSlot;

@Data
@SuperBuilder(toBuilder = true)
public class PriceSlotCQD implements CallbackQueryData {
    PriceSlot priceSlot;
}
