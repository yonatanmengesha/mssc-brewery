package com.jotech.mssc_brewery.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;

import com.jotech.mssc_brewery.service.BeerService;
import com.jotech.mssc_brewery.web.model.BeerDto;
import com.jotech.mssc_brewery.service.v2.BeerServiceV2;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Deprecated
@RunWith(SpringRunner.class)
@WebMvcTest(BeerController.class)
public class BeerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    BeerService beerService;

    BeerDto validBeer;

    @Before
    public void setUp() throws Exception {

        validBeer = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("Beer1")
                .beerStyle("PALE ALE")
                .upc(123456789012L)
                .build();
    }

    @Test
    public void getBeer() throws Exception {

        given(beerService.getBeerById(any(UUID.class))).willReturn(validBeer);

        mockMvc.perform(get("/api/v1/beer/" + validBeer.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id" , is(validBeer.getId().toString())))
                .andExpect(jsonPath("$.beerName",is("Beer1")));
    }

    @Test
    public void handlePostBeer() throws Exception {

        //given

        BeerDto beerDto = validBeer;
        beerDto.setId(null);  //UUID.randomUUID()
        BeerDto savedBeerDto = BeerDto.builder()
                .id(UUID.randomUUID())
                .beerName("new Beer")
                .build();
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);


        given(beerService.saveNewBeer(any())).willReturn(savedBeerDto);

        mockMvc.perform(post("/api/v1/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isCreated());
    }

    @Test
    public void handleUpdate() throws Exception {
      //given
        BeerDto beerDtoV2 = validBeer;

        String beerDtoJson = objectMapper.writeValueAsString(beerDtoV2);

        //when

        mockMvc.perform(put("/api/v1/beer/" + validBeer.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());

        //then

        then(beerService).should().updateBeer(any(),any());


    }

    @Test
    public void handleDelete() throws Exception{

        BeerDto beerDto = validBeer;
        String beerDtoJson = objectMapper.writeValueAsString(beerDto);

        mockMvc.perform(delete("/api/v1/beer/" + beerDto.getId())
                .contentType(MediaType.APPLICATION_JSON_UTF8)
                .content(beerDtoJson))
                .andExpect(status().isNoContent());


        then(beerService).should().deleteBeerById(any());

    }
}