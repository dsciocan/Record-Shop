package com.northcoders.record_shop.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.model.Artist;
import com.northcoders.record_shop.model.Genre;
import com.northcoders.record_shop.service.ShopServiceImplementation;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
                MockMvcRequestBuilders.get("/api/v1/album"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET album by id, valid id")
    public void testGetAlbumById() throws Exception {
        Album album1 = Album.builder().id(1L).name("No Longer Hooman").releaseYear(2024).stock(100).build();
        Album album2 = Album.builder().id(2L).name("Purrfection").releaseYear(2022).stock(50).build();
        List<Album> albumList = new ArrayList<>();
        albumList.add(album1);
        albumList.add(album2);
        for (Album album : albumList) {
            Mockito.when(shopServiceImplementation.getAlbumById(album.getId())).thenReturn(album);
        };


        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/album/1"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(status().isOk());
        this.mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/album/2"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(2L))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("GET album by id, invalid id")
    public void testGetAlbumByIdUnhappy() throws Exception {
        Album album1 = Album.builder().id(1L).name("No Longer Hooman").releaseYear(2024).stock(100).build();
        Album album2 = Album.builder().id(2L).name("Purrfection").releaseYear(2022).stock(50).build();
        List<Album> albumList = new ArrayList<>();
        albumList.add(album1);
        albumList.add(album2);
        albumList.forEach(album -> Mockito.when(shopServiceImplementation.getAlbumById(album.getId())).thenReturn(album));

        this.mockMvc.perform(
                        MockMvcRequestBuilders.get("/api/v1/album/3"))
                .andExpect(status().isNotFound());

    }

    @Test
    @DisplayName("POST album , valid entry")
    public void testPostAlbum() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Set<Artist> artists = new HashSet<>(List.of(new Artist("Artist1")));
        Album album = new Album(1L, "Sample", 2021, 100, genreList, artists);
        String json = mapper.writeValueAsString(album);

       Mockito.when(shopServiceImplementation.addAlbum(album)).thenReturn(album);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.post("/api/v1/album").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(status().isCreated());

    }

    @Test
    @DisplayName("PUT album, valid entry")
    public void testPutAlbum() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Set<Artist> artists = new HashSet<>(List.of(new Artist("Artist1")));
        Album album = new Album(1L, "Sample", 2021, 100, genreList, artists);
        Album newAlbum = new Album(1L, "Sample", 2021, 50, genreList, artists);
        String json = mapper.writeValueAsString(newAlbum);

        Mockito.when(shopServiceImplementation.updateAlbum(1L, newAlbum)).thenReturn(newAlbum);

        this.mockMvc.perform(
                        MockMvcRequestBuilders.put("/api/v1/album/1").contentType(MediaType.APPLICATION_JSON).content(json))
                .andExpect(MockMvcResultMatchers.jsonPath("$.stock").value(50))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L))
                .andExpect(status().isOk());
    }
}
