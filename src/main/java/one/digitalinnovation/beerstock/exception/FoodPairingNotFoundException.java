package one.digitalinnovation.beerstock.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FoodPairingNotFoundException extends Exception {

    public FoodPairingNotFoundException(String beerName) {
        super(String.format("Food pairing with name %s not found in the system.", beerName));
    }

    public FoodPairingNotFoundException(Long id) {
        super(String.format("Food pairing with id %s not found in the system.", id));
    }
}
