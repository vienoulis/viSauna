package ru.vienoulis.visauna.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder(toBuilder = true)
public class Prices {
    long priceForVisit;
    long priceForClean;
    long priceAndClean;
}
