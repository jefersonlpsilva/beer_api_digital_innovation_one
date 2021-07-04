package one.digitalinnovation.beerstock.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DishTemperatureType {

    HOT("Hot"),
    COLD("Cold");
    
    private final String description;
}
