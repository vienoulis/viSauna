package ru.vienoulis.visauna.service;

public final class TextService {
    public static final String BIG_HALL_PRICE = "1-4 чел. от 1400 руб/час.\n" +
            "5-8 чел. 1800 руб/час.\n" +
            "9-16 чел. 2200 руб/час";
    public static final String SMALL_HALL_PRICE = "2 чел. от 1000руб/час .\n" +
            "3-4 чел. 1200 руб/час\n" +
            "5-10 чел. 1400 руб/час";

    private TextService() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }
}
