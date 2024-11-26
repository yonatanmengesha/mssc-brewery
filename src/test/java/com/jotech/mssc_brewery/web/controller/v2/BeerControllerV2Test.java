package com.jotech.mssc_brewery.web.controller.v2;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jotech.mssc_brewery.service.v2.BeerServiceV2;
import com.jotech.mssc_brewery.web.model.v2.BeerDtoV2;
import com.jotech.mssc_brewery.web.model.v2.BeerStyleEnum;
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


@RunWith(SpringRunner.class)
@WebMvcTest(BeerControllerV2.class)
public class BeerControllerV2Test {

    BeerDtoV2 validBeerV2;

    @MockBean
    BeerServiceV2 beerServiceV2;

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Before
    public void setUp() throws Exception {


        validBeerV2 =   BeerDtoV2.builder().
                   id(UUID.randomUUID())
                   .beerName("Blue Moon")
                   .beerStyle(BeerStyleEnum.IPA)
                   .upc(12345678910L)
                   .build();
    }

    @Test
    public void getBeer()  throws Exception{

        //given

        given(beerServiceV2.getBeerById(any(UUID.class))).willReturn(validBeerV2);
        mockMvc.perform(get("/api/v2/beer/" + validBeerV2.getId().toString()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id",is(validBeerV2.getId().toString())))
                .andExpect(jsonPath("$.beerName",is("Blue Moon")))
                .andExpect( jsonPath("$.beerStyle",is(validBeerV2.getBeerStyle().toString())));

    }

    @Test
    public void handlePostBeer() throws  Exception {

        BeerDtoV2 beerDtoV2 = validBeerV2;
        beerDtoV2.setId(null);

        BeerDtoV2 savedBeerDtoV2 = BeerDtoV2
                .builder()
                .id(UUID.randomUUID())
                .beerName("new Beer")
                .beerStyle(BeerStyleEnum.ALE)
                .build();

        String beerDtoV2Json = objectMapper.writeValueAsString(beerDtoV2);

        given(beerServiceV2.saveNewBeer(any())).willReturn(savedBeerDtoV2);

        mockMvc.perform(post("/api/v2/beer")
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoV2Json))
                .andExpect(status().isCreated());



    }

    @Test
    public void handleUpdate()  throws  Exception{

        BeerDtoV2 beerDtoV2 = validBeerV2;

        String beerDtoV2Json = objectMapper.writeValueAsString(beerDtoV2);

        mockMvc.perform(put("/api/v2/beer/" + beerDtoV2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoV2Json)
        ).andExpect(status().isNoContent());
    }

    @Test
    public void deleteBeer() throws Exception {

        BeerDtoV2 beerDtoV2 = validBeerV2;

        String beerDtoV2Json = objectMapper.writeValueAsString(beerDtoV2);
        mockMvc.perform(delete("/api/v2/beer/" + validBeerV2.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(beerDtoV2Json))
                .andExpect(status().isNoContent());

        then(beerServiceV2).should().deleteBeerById(any());
    }
}