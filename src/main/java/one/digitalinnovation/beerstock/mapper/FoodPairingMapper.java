package one.digitalinnovation.beerstock.mapper;

import one.digitalinnovation.beerstock.dto.FoodPairingDTO;
import one.digitalinnovation.beerstock.entity.FoodPairing;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface FoodPairingMapper {

    FoodPairingMapper INSTANCE = Mappers.getMapper(FoodPairingMapper.class);

    FoodPairing toModel(FoodPairingDTO foodPairingDTO);

    FoodPairingDTO toDTO(FoodPairing foodPairing);
}
