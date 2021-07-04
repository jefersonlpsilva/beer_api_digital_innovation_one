package one.digitalinnovation.beerstock.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.exception.FoodPairingAlreadyRegisteredException;
import one.digitalinnovation.beerstock.exception.FoodPairingNotFoundException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.List;

@Api("Manages food pairing stock")
public interface FoodPairingControllerDocs {

    @ApiOperation(value = "Food pairing creation operation")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success food pairing creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    FoodPairingDTO createFoodPairing(@RequestBody @Valid FoodPairingDTO foodPairingDTO) throws FoodPairingAlreadyRegisteredException;
    
    @ApiOperation(value = "Returns food pairing found by a given name")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success food pairing found in the system"),
            @ApiResponse(code = 404, message = "Food pairing with given name not found.")
    })
    FoodPairingDTO findByName(@PathVariable String nameDish) throws FoodPairingNotFoundException;

    @ApiOperation(value = "Returns a list of all food pairing registered in the system")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "List of all food pairings registered in the system"),
    })
    List<FoodPairingDTO> listFoodPairings();

    @ApiOperation(value = "Delete a food pairing found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Success Foodpairing deleted in the system"),
            @ApiResponse(code = 404, message = "Foodpairing with given id not found.")
    })
    void deleteById(@PathVariable Long id) throws FoodPairingNotFoundException;

    @ApiOperation(value = "Update a food pairing found by a given valid Id")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Success food pairing creation"),
            @ApiResponse(code = 400, message = "Missing required fields or wrong field range value.")
    })
    FoodPairingDTO updateById(@PathVariable Long id, @RequestBody FoodPairingDTO foodPairingDTO) throws FoodPairingNotFoundException;
}
