package com.jotech.mssc_brewery.web.controller;

import com.jotech.mssc_brewery.service.BeerService;
import com.jotech.mssc_brewery.web.model.BeerDto;
import com.jotech.mssc_brewery.service.v2.BeerServiceV2;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@Deprecated
@RequestMapping("/api/v1/beer")
@RestController
public class BeerController {

    private final BeerService beerService;

    public BeerController(BeerService beerService) {
        this.beerService = beerService;
    }

    @GetMapping("/{beerId}")
    public ResponseEntity<BeerDto> getBeer(@PathVariable("beerId") UUID beerId){

            return new ResponseEntity<>(beerService.getBeerById(beerId), HttpStatus.OK);
    }

    @PostMapping  // POST - create new beer
    public ResponseEntity handlePostBeer(@RequestBody BeerDto beerDto){

        BeerDto savedDto = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();
        //todo add hostname to url
        headers.add("Location", "/api/v1/beer/" + savedDto.getId().toString());

        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    

   /* @PostMapping
    public ResponseEntity  handlePostBeer(@RequestBody BeerDto beerDto){

        BeerDto savedBeer = beerService.saveNewBeer(beerDto);

        HttpHeaders headers = new HttpHeaders();

        //todo add hostname tp url
        headers.add("Location","/api/v1/beer/"+savedBeer.getId().toString());


        return new ResponseEntity(headers,HttpStatus.CREATED);
    }*/

    @PutMapping("/{beerId}")
    public ResponseEntity  handleUpdate(@PathVariable("beerId")UUID beerId ,@RequestBody BeerDto beerDto){


        beerService.updateBeer(beerId, beerDto);


        return new ResponseEntity(HttpStatus.NO_CONTENT);


    }

    @DeleteMapping("/{beerId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteBeer(@PathVariable("beerId") UUID beerId){

        beerService.deleteBeerById(beerId);
    }
}
