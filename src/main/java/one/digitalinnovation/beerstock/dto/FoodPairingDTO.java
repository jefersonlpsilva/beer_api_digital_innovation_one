package one.digitalinnovation.beerstock.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovation.beerstock.enums.DishTemperatureType;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodPairingDTO {
    
    private Long id;

    @NotNull
    @Size(min = 1, max = 200)
    private String dishName;

    @NotNull
    @Size(min = 1, max = 1000)
    private String recipe;

    @NotNull
    @Enumerated(EnumType.STRING)
    private DishTemperatureType temperature;
}
