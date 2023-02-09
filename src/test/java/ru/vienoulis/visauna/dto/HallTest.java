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
        bigHall = new Hall(16);
        bigHall.setPriceFor(1, 1400_00L );
        bigHall.setPriceFor(5, 1800_00L );
        bigHall.setPriceFor(9, 2200_00L );

    }

    @Test
    public void getPriceForTest(){
        Assertions.assertEquals(1400_00L, bigHall.getPriceFor(1));
        Assertions.assertEquals(1400_00L, bigHall.getPriceFor(2));
        Assertions.assertEquals(1400_00L, bigHall.getPriceFor(4));
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(5));
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(6));
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(7));

        bigHall.setPriceFor(6, 1850_00L);
        Assertions.assertEquals(1800_00L, bigHall.getPriceFor(5));
        Assertions.assertEquals(1850_00L, bigHall.getPriceFor(6));

        Assertions.assertEquals(2200_00L, bigHall.getPriceFor(16));
        bigHall.setPriceFor(11, 2250_00L);
        Assertions.assertEquals(2250_00L, bigHall.getPriceFor(16));


    }

}