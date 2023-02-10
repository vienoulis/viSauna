package ru.vienoulis.visauna.dto;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

class HallTest {

    private static Hall bigHall;

    /**
     * 1-4 чел. от 1400 руб/час.
     * 5-8 чел. 1800 руб/час.
     * 9-16 чел. 2200 руб/час
     */
    @BeforeAll
    public static void init() {
        var slot1 = new PriceSlot(1, 4, 140000);
        var slot2 = new PriceSlot(5, 8, 180000);
        var slot3 = new PriceSlot(9, 16, 220000);
        bigHall = new Hall(16);
        bigHall.setPriceFor(slot1);
        bigHall.setPriceFor(slot2);
        bigHall.setPriceFor(slot3);

    }

    @Test
    public void getPriceForTest() {
        Assertions.assertEquals(1400_00L, bigHall.getPriceFor(1, false), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1400_00L, bigHall.getPriceFor(2, false), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1400_00L, bigHall.getPriceFor(4, false), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(5, false), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(6, false), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(7, false), () -> bigHall.getPriceList().toString());

        var weekendTax = 200;
        Assertions.assertEquals(1400_00L + weekendTax, bigHall.getPriceFor(1, true), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1400_00L + weekendTax, bigHall.getPriceFor(2, true), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1400_00L + weekendTax, bigHall.getPriceFor(4, true), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1800_00L + weekendTax, bigHall.getPriceFor(5, true), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1800_00L + weekendTax, bigHall.getPriceFor(6, true), () -> bigHall.getPriceList().toString());
        Assertions.assertEquals(1800_00L + weekendTax, bigHall.getPriceFor(7, true), () -> bigHall.getPriceList().toString());

//        bigHall.setPriceFor(6, 1850_00L);
//        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(5));
//        Assertions.assertEquals(1850_00L, bigHall.getPriceFor(6));
//
//        Assertions.assertEquals(2200_00L, bigHall.getPriceFor(16));
//        bigHall.setPriceFor(11, 2250_00L);
//        Assertions.assertEquals(2250_00L, bigHall.getPriceFor(16));
//
    }

}