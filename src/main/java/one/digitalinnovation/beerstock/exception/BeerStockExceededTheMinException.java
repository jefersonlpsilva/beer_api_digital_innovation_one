package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BeerStockExceededTheMinException extends Exception {

    public BeerStockExceededTheMinException(Long id, int quantityToIncrement) {
        super(String.format("Beers with %s ID to increment informed exceeds the min stock capacity: %s", id, quantityToIncrement));
    }
}
