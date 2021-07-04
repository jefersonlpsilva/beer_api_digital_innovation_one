package one.digitalinnovation.beerstock.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import one.digitalinnovation.beerstock.enums.DishTemperatureType;

import javax.persistence.*;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FoodPairing {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String dishName;
    
    @Column(nullable = false, length = 1000)
    
    private String recipe;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DishTemperatureType temperature;
}
