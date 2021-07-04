package one.digitalinnovation.beerstock.controller;

import one.digitalinnovation.beerstock.builder.FoodPairingDTOBuilder;
import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.exception.BeerNotFoundException;
import one.digitalinnovation.beerstock.exception.FoodPairingNotFoundException;
import one.digitalinnovation.beerstock.service.FoodPairingService;
import org.junit.jupiter.api.BeforeEach;
import static one.digitalinnovation.beerstock.utils.JsonConvertionUtils.asJsonString;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.view.json.MappingJackson2JsonView;
import java.util.Collections;

import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class FoodPairingControllerTest {

    private static final String FOODPAIRING_API_URL_PATH = "/api/v1/foodpairing";
    private static final long VALID_FOOD_PAIRING_ID = 1l;
    private static final long INVALID_FOOD_PAIRING_ID = 2l;
    
    private MockMvc mockMvc;

    @Mock
    private FoodPairingService foodPairingService;
    
    @InjectMocks
    private FoodPairingController foodPairingController;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(foodPairingController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .setViewResolvers((s, locale) -> new MappingJackson2JsonView())
                .build();
    }
    
    @Test
    void whenPostIsCalledThenAFoodPairingIsCreated() throws Exception {
        //givin
        FoodPairingDTO foodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        
        //when
        when(foodPairingService.createFoodPairing(foodPairingDTO)).thenReturn(foodPairingDTO);
        
        //then
        mockMvc.perform(post(FOODPAIRING_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(foodPairingDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.dishName", is(foodPairingDTO.getDishName())))
                .andExpect(jsonPath("$.recipe", is(foodPairingDTO.getRecipe())))
                .andExpect(jsonPath("$.temperature", is(foodPairingDTO.getTemperature().toString())));         
    }

    @Test
    void whenPOSTIsCalledWithoutRequiredFieldThenAnErrorIsReturned() throws Exception {
        // given
        FoodPairingDTO foodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        foodPairingDTO.setDishName("");
        
        // then
        mockMvc.perform(MockMvcRequestBuilders.post(FOODPAIRING_API_URL_PATH)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(foodPairingDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void whenGETIsCalledWithRegisteredDishNameThenFoundStatusIsReturned() throws Exception {
        // given
        FoodPairingDTO foodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();

        //when
        when(foodPairingService.findByDishName(foodPairingDTO.getDishName())).thenReturn(foodPairingDTO);
        
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(FOODPAIRING_API_URL_PATH + "/" + foodPairingDTO.getDishName())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(foodPairingDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dishName", is(foodPairingDTO.getDishName())))
                .andExpect(jsonPath("$.recipe", is(foodPairingDTO.getRecipe())))
                .andExpect(jsonPath("$.temperature", is(foodPairingDTO.getTemperature().toString())));
    }

    @Test
    void whenGETIsCalledWithoutRegisteredDishNameThenNotFoundStatusIsReturned() throws Exception {
        // given
        FoodPairingDTO foodPairingDTO = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();

        //when
        when(foodPairingService.findByDishName(foodPairingDTO.getDishName())).thenThrow(FoodPairingNotFoundException.class);

        // then
        mockMvc.perform(MockMvcRequestBuilders.get(FOODPAIRING_API_URL_PATH + "/" + foodPairingDTO.getDishName())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    
    @Test 
    void whenGETListWithFoodPairingIsCalldedThenOkStatusIsReturn() throws Exception {
        //given
        FoodPairingDTO foodPairingDto = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        
        //when
        when(foodPairingService.listAll()).thenReturn(Collections.singletonList(foodPairingDto));
        
        //
        mockMvc.perform(MockMvcRequestBuilders.get(FOODPAIRING_API_URL_PATH)
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    
    @Test
    void whenGetListWithoutFoodPairingsIsCalledThenOkStatusIsReturned() throws Exception {
        //given
        FoodPairingDTO foodPairingDto = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        
        //when
        when(foodPairingService.listAll()).thenReturn(Collections.singletonList(null));
        
        //then
        mockMvc.perform(MockMvcRequestBuilders.get(FOODPAIRING_API_URL_PATH)
                                              .contentType(MediaType.APPLICATION_JSON))
                                              .andExpect(status().isOk());
        
    }
    
    @Test
    void whenDELETEIsCalledWithValidIdThenNoContentStatusIsReturned() throws Exception {
        //given
        FoodPairingDTO foodPairingDto = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        
        doNothing().when(foodPairingService).deleteById(foodPairingDto.getId());

        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(FOODPAIRING_API_URL_PATH + "/" + foodPairingDto.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

     @Test
     void whenDELETEIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        //when
        doThrow(FoodPairingNotFoundException.class).when(foodPairingService).deleteById(INVALID_FOOD_PAIRING_ID);
     
        // then
        mockMvc.perform(MockMvcRequestBuilders.delete(FOODPAIRING_API_URL_PATH + "/" + INVALID_FOOD_PAIRING_ID)
               .contentType(MediaType.APPLICATION_JSON))
               .andExpect(status().isNotFound());
     }

    @Test
    void whenPUTIsCalledThenAFoodPairingIsShoudBeSave() throws Exception {
        // given
        FoodPairingDTO expectedToSaveFoodPairing = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        FoodPairingDTO savedFoodPairing = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();

        //when
        when(foodPairingService.updateById(VALID_FOOD_PAIRING_ID, expectedToSaveFoodPairing)).thenReturn(savedFoodPairing);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(FOODPAIRING_API_URL_PATH + "/" + expectedToSaveFoodPairing.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedToSaveFoodPairing)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.dishName", is(savedFoodPairing.getDishName())))
                .andExpect(jsonPath("$.recipe", is(savedFoodPairing.getRecipe())));
    }

    @Test
    void whenPUTIsCalledWithInvalidIdThenNotFoundStatusIsReturned() throws Exception {
        FoodPairingDTO expectedToSaveFoodPairing = FoodPairingDTOBuilder.builder().build().toFoodPairingDTO();
        expectedToSaveFoodPairing.setId(null);
        //when
        doThrow(BeerNotFoundException.class).when(foodPairingService).updateById(INVALID_FOOD_PAIRING_ID, expectedToSaveFoodPairing);

        // then
        mockMvc.perform(MockMvcRequestBuilders.put(FOODPAIRING_API_URL_PATH + "/" + INVALID_FOOD_PAIRING_ID)
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(expectedToSaveFoodPairing)))
                .andExpect(status().isNotFound());
    }
}