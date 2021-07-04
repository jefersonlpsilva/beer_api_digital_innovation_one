package one.digitalinnovation.beerstock.builder;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.enums.DishTemperatureType;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodPairingDTOBuilder {

    @Builder.Default
    private Long id = 1L;

    @Builder.Default
    private String dishName = "Frites and Fried Foods";
    
    @Builder.Default
    private String recipe = "Even if the Belgians make the best frites in the world ...";//and are the largest consumers per person, those salty potato sticks are still an American staple cuisine. In general, fries (or frites) are light in flavor profile, so opt for a brew that will help cleanse the palate. 'In general ask for a beer to cleanse your palate without washing away all the salty flavors, cutting through and bringing out the taste of the food,” says Stroobandt, who prefers a nice Stella Artois with his frites. The same recommendation goes for most fried, salty dishes.'";

    @Builder.Default
    private DishTemperatureType temperature = DishTemperatureType.HOT;
    
    public FoodPairingDTO toFoodPairingDTO() {
        return new FoodPairingDTO(id,
                dishName,
                recipe,
                temperature);
    }
    
}
