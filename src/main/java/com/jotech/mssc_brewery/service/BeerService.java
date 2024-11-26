package com.jotech.mssc_brewery.service;

import com.jotech.mssc_brewery.web.model.BeerDto;
import com.jotech.mssc_brewery.web.model.v2.BeerDtoV2;

import java.util.UUID;

public interface BeerService {

    BeerDto getBeerById(UUID beerId);

    BeerDto saveNewBeer(BeerDto beerDto);

    void updateBeer(UUID beerId, BeerDto beerDto);

    void deleteBeerById(UUID beerId);
}
