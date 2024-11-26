package com.jotech.mssc_brewery.service.v2;

import com.jotech.mssc_brewery.web.model.BeerDto;
import com.jotech.mssc_brewery.web.model.v2.BeerDtoV2;

import java.util.UUID;

public interface BeerServiceV2 {
    BeerDtoV2 getBeerById(UUID beerId);

    BeerDtoV2 saveNewBeer(BeerDtoV2 beerDtoV2);

    void updateBeer(UUID beerId, BeerDtoV2 beerDtoV2);

    void deleteBeerById(UUID beerId);
}
