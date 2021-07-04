package one.digitalinnovation.beerstock.repository;

import one.digitalinnovation.beerstock.entity.FoodPairing;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodPairingRepository extends JpaRepository<FoodPairing, Long> {

    Optional<FoodPairing> findByDishName(String dishName);
}
