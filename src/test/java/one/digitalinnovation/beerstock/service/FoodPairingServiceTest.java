package one.digitalinnovation.beerstock.service;

import one.digitalinnovation.beerstock.builder.FoodPairingDTOBuilder;
import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.entity.FoodPairing;
import one.digitalinnovation.beerstock.exception.FoodPairingAlreadyRegisteredException;

import one.digitalinnovation.beerstock.exception.FoodPairingNotFoundException;
import one.digitalinnovation.beerstock.mapper.FoodPairingMapper;
import one.digitalinnovation.beerstock.repository.FoodPairingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class FoodPairingServiceTest {

    private static final long VALID_FOOD_PAIRING_ID = 1L;
    private static final long INVALID_FOOD_PAIRING_ID = 1L;

    @Mock
    private FoodPairingRepository foodPairingRepository;

    private FoodPairingMapper foodPairingMapper = FoodPairingMapper.INSTANCE;

    @InjectMocks
    private FoodPairingService foodPairingService;

    @Test
    void whenFoodPairingInformedThenItShouldBeCreated() throws FoodPairingAlreadyRegisteredException {
        // given
        FoodPairingDTO expectedFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing expectedSavedFoodPairing = foodPairingMapper.toModel(expectedFoodPairingDTO);

        // when
        when(foodPairingRepository.findByDishName(expectedFoodPairingDTO.getDishName())).thenReturn(Optional.empty());
        when(foodPairingRepository.save(expectedSavedFoodPairing)).thenReturn(expectedSavedFoodPairing);

        //then
        FoodPairingDTO createdFoodPairingDTO = foodPairingService.createFoodPairing(expectedFoodPairingDTO);

        assertThat(createdFoodPairingDTO.getId(), is(equalTo(expectedFoodPairingDTO.getId())));
        assertThat(createdFoodPairingDTO.getDishName(), is(equalTo(expectedFoodPairingDTO.getDishName())));
    }
    
    
    @Test
    void whenAlreadyRegisteredFoodPairingInformedThenAnExceptionShouldBeThrown() {
        // given
        FoodPairingDTO expectedFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing duplicatedFoodPairing = foodPairingMapper.toModel(expectedFoodPairingDTO);

        // when
        when(foodPairingRepository.findByDishName(expectedFoodPairingDTO.getDishName())).thenReturn(Optional.of(duplicatedFoodPairing));

        // then
        assertThrows(FoodPairingAlreadyRegisteredException.class, () -> foodPairingService.createFoodPairing(expectedFoodPairingDTO));
    }


    @Test
    void whenValidFoodPairingNameIsGivenThenReturnAFoodPairing() throws FoodPairingNotFoundException {
        // given
        FoodPairingDTO expectedFoundFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing expectedFoundFoodPairing = foodPairingMapper.toModel(expectedFoundFoodPairingDTO);

        // when
        when(foodPairingRepository.findByDishName(expectedFoundFoodPairing.getDishName())).thenReturn(Optional.of(expectedFoundFoodPairing));

        // then
        FoodPairingDTO foundFoodPairingDTO = foodPairingService.findByDishName(expectedFoundFoodPairingDTO.getDishName());

        assertThat(foundFoodPairingDTO, is(equalTo(expectedFoundFoodPairingDTO)));
    }

    @Test
    void whenNotRegisteredFoodPairingNameIsGivenThenThrowAnException() {
        // given
        FoodPairingDTO expectedFoundFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();

        // when
        when(foodPairingRepository.findByDishName(expectedFoundFoodPairingDTO.
                getDishName())).thenReturn(Optional.empty());

        // then
        assertThrows(FoodPairingNotFoundException.class, () -> foodPairingService.findByDishName(expectedFoundFoodPairingDTO.getDishName()));
    }

    @Test
    void whenListFoodPairingIsCalledThenReturnAListOfFoodPairings() {
        // given
        FoodPairingDTO expectedFoundFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing expectedFoundFoodPairing = foodPairingMapper.toModel(expectedFoundFoodPairingDTO);

        //when
        when(foodPairingRepository.findAll()).thenReturn(Collections.singletonList(expectedFoundFoodPairing));

        //then
        List<FoodPairingDTO> foundListFoodPairingsDTO = foodPairingService.listAll();

        assertThat(foundListFoodPairingsDTO, is(not(empty())));
        assertThat(foundListFoodPairingsDTO.get(0), is(equalTo(expectedFoundFoodPairingDTO)));
    }

    @Test
    void whenListFoodPairingIsCalledThenReturnAnEmptyListOfFoodPairings() {
        //when
        when(foodPairingRepository.findAll()).thenReturn(Collections.EMPTY_LIST);

        //then
        List<FoodPairingDTO> foundListFoodPairingsDTO = foodPairingService.listAll();

        assertThat(foundListFoodPairingsDTO, is(empty()));
    }

    
    @Test
    void whenUpdatedFoodPairingByIdThenItShouldBeSave() throws FoodPairingNotFoundException {
        // given
        FoodPairingDTO expectedFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing expectedSavedFoodPairing = foodPairingMapper.toModel(expectedFoodPairingDTO);
        FoodPairing expectedReturndFoodPairing = foodPairingMapper.toModel(expectedFoodPairingDTO);
        
        // when
        when(foodPairingRepository.findById(expectedFoodPairingDTO.getId())).thenReturn(Optional.of(expectedSavedFoodPairing));
        when(foodPairingRepository.save(expectedSavedFoodPairing)).thenReturn(expectedReturndFoodPairing);
        
        //then
        FoodPairingDTO updatedFoodPairingDTO = foodPairingService.updateById(VALID_FOOD_PAIRING_ID, expectedFoodPairingDTO);

        assertThat(updatedFoodPairingDTO.getId(), is(equalTo(expectedReturndFoodPairing.getId())));
        assertThat(updatedFoodPairingDTO.getDishName(), is(equalTo(expectedReturndFoodPairing.getDishName())));
        assertThat(updatedFoodPairingDTO.getRecipe(), is(equalTo(expectedReturndFoodPairing.getRecipe())));
    }

    @Test
    void whenUpdateFoodPairingThenAnExceptionShouldBeThrown()  throws FoodPairingNotFoundException {
        // given
        FoodPairingDTO notFoundFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing expectedToFound = foodPairingMapper.toModel(notFoundFoodPairingDTO);
        expectedToFound.setId(INVALID_FOOD_PAIRING_ID);
        
        // when
        when(foodPairingRepository.findById(expectedToFound.getId())).thenReturn(Optional.empty());
        
        // then
        assertThrows(FoodPairingNotFoundException.class, () -> foodPairingService.updateById( INVALID_FOOD_PAIRING_ID, notFoundFoodPairingDTO));
    }
    
    @Test
    void whenExclusionIsCalledWithValidIdThenAFoodPairingShouldBeDeleted() throws FoodPairingNotFoundException{
        // given
        FoodPairingDTO expectedDeletedFoodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairing expectedDeletedFoodPairing = foodPairingMapper.toModel(expectedDeletedFoodPairingDTO);

        // when
        when(foodPairingRepository.findById(expectedDeletedFoodPairingDTO.getId())).thenReturn(Optional.of(expectedDeletedFoodPairing));
        doNothing().when(foodPairingRepository).deleteById(expectedDeletedFoodPairingDTO.getId());

        // then
        foodPairingService.deleteById(expectedDeletedFoodPairingDTO.getId());

        verify(foodPairingRepository, times(1)).findById(expectedDeletedFoodPairingDTO.getId());
        verify(foodPairingRepository, times(1)).deleteById(expectedDeletedFoodPairingDTO.getId());
    }
}
