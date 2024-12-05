package com.northcoders.record_shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.model.Genre;
import com.northcoders.record_shop.model.Genres;
import com.northcoders.record_shop.service.ShopServiceImplementation;
import org.checkerframework.checker.units.qual.A;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.assertj.MockMvcTester;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest
public class ShopControllerTests {

    @Mock
    private ShopServiceImplementation shopServiceImplementation;

    @InjectMocks
    private ShopController shopController;

    @Autowired
    private MockMvc mockMvc;

    private ObjectMapper mapper;

    @BeforeEach
    public void setup(){
        mockMvc = MockMvcBuilders.standaloneSetup(shopController).build();
        mapper = new ObjectMapper();
    }


    @Test
    @DisplayName("GET all albums")
    public void testGetAllAlbums() throws Exception {
        Album album = Album.builder().id(1L).name("No Longer Hooman").releaseYear(2024).stock(100).build();
        List<Album> albumList = new ArrayList<>();
        albumList.add(album);
        Mockito.when(shopServiceImplementation.getAllAlbums()).thenReturn(albumList);

        this.mockMvc.perform(
                MockMvcRequestBuilders.get("/api/v1/album/"))
//                .andExpect(MockMvcResultMatchers.jsonPath("$.length").value(1))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(status().isOk());
    }
}