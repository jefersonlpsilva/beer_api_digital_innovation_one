package one.digitalinnovation.beerstock.service;

import lombok.AllArgsConstructor;
import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.entity.FoodPairing;
import one.digitalinnovation.beerstock.exception.FoodPairingAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.FoodPairingNotFoundException;
import one.digitalinnovation.beerstock.mapper.FoodPairingMapper;
import one.digitalinnovation.beerstock.repository.FoodPairingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class FoodPairingService {

    private final FoodPairingRepository foodPairingRepository;
    private final FoodPairingMapper foodPairingMapper = FoodPairingMapper.INSTANCE;

    public FoodPairingDTO createFoodPairing(FoodPairingDTO foodPairingDTO) throws FoodPairingAlreadyRegisteredException { 
        verifyIfIsAlreadyRegistered(foodPairingDTO.getDishName());
        FoodPairing foodPairing = foodPairingMapper.toModel(foodPairingDTO);
        FoodPairing savedFoodPairing = foodPairingRepository.save(foodPairing);
        return foodPairingMapper.toDTO(savedFoodPairing);
    }

   public FoodPairingDTO findByDishName(String dishName) throws FoodPairingNotFoundException {
        FoodPairing foundFoodPairing = foodPairingRepository.findByDishName(dishName)
                .orElseThrow(() -> new FoodPairingNotFoundException(dishName));
        return foodPairingMapper.toDTO(foundFoodPairing);
    }

    private void verifyIfIsAlreadyRegistered(String dishName) throws FoodPairingAlreadyRegisteredException {
        Optional<FoodPairing> optSavedFoodPairing = foodPairingRepository.findByDishName(dishName);
        if (optSavedFoodPairing.isPresent()) {
            throw new FoodPairingAlreadyRegisteredException(dishName);
        }
    }

    public List<FoodPairingDTO> listAll() {
        return foodPairingRepository.findAll()
                .stream()
                .map(foodPairingMapper::toDTO)
                .collect(Collectors.toList());
    }
    
    public FoodPairingDTO updateById(Long id, FoodPairingDTO foodPairingDTO) throws FoodPairingNotFoundException {
        verifyIfExists(id);

        FoodPairing foodPairingToUpdate  = foodPairingMapper.toModel(foodPairingDTO);

        FoodPairing updateFoodPairing  = foodPairingRepository.save(foodPairingToUpdate);
        return foodPairingMapper.toDTO(updateFoodPairing);
    }
    
    public void deleteById(Long id) throws FoodPairingNotFoundException {
        verifyIfExists(id);
        foodPairingRepository.deleteById(id);
    }
    
    private FoodPairing verifyIfExists(Long id) throws FoodPairingNotFoundException {
        return foodPairingRepository.findById(id)
                .orElseThrow( () -> new FoodPairingNotFoundException(id));
    }
}
