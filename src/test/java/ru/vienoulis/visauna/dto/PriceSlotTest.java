package ru.vienoulis.visauna.dto;

import org.junit.jupiter.api.Test;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PriceSlotTest {


    @Test
    public void getStringValueTest() {
        var slot1 = new PriceSlot(1, 4, 140000);
        var slot2 = new PriceSlot(5, 8, 180000);
        var slot3 = new PriceSlot(9, 16, 220000);


        var slotTxt1 = "1-4 чел. от 1400 руб/час.";
        var slotTxt2 = "5-8 чел. от 1800 руб/час.";
        var slotTxt3 = "9-16 чел. от 2200 руб/час.";
        assertEquals(slot1.getStringValue(), slotTxt1);
        assertEquals(slot2.getStringValue(), slotTxt2);
        assertEquals(slot3.getStringValue(), slotTxt3);
    }
}