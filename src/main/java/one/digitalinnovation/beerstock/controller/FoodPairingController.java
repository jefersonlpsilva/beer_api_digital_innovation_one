package one.digitalinnovation.beerstock.controller;

/**
 *  Construido a partir de desenvolvimento TDD. 
 *  Sendo assim, não construi a classe de teste Service (não estou certo que é necessário)
 *  Jeferson silva 02/julho/2021
 */


import lombok.AllArgsConstructor;
import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.exception.FoodPairingAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.FoodPairingNotFoundException;
import one.digitalinnovation.beerstock.service.FoodPairingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1/foodpairing")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FoodPairingController implements FoodPairingControllerDocs {

    private final FoodPairingService foodPairingService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FoodPairingDTO createFoodPairing(@RequestBody @Valid FoodPairingDTO foodPairingDTO) throws FoodPairingAlreadyRegisteredException {
        return foodPairingService.createFoodPairing(foodPairingDTO);
    }

    @GetMapping("/{dishName}")
    public FoodPairingDTO findByName(@PathVariable String dishName) throws FoodPairingNotFoundException {
        return foodPairingService.findByDishName(dishName);
    }

    @GetMapping
    public List<FoodPairingDTO> listFoodPairings() {
        return  foodPairingService.listAll();
    }
    
    @PutMapping("/{id}")
    public FoodPairingDTO updateById(@PathVariable Long id, @RequestBody FoodPairingDTO foodPairingDTO) throws FoodPairingNotFoundException {
        return foodPairingService.updateById(id, foodPairingDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteById(@PathVariable Long id) throws FoodPairingNotFoundException {
        foodPairingService.deleteById(id);
    }
}    