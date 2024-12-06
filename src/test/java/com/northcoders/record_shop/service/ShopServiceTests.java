package com.northcoders.record_shop.service;


import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
public class ShopServiceTests {

    @Mock
    ShopRepository shopRepository;

    @InjectMocks
    ShopServiceImplementation shopServiceImplementation;

    @Test
    @DisplayName("getAllAlbums responds with the appropriate list of albums")
    void testGetAllAlbums() {
        Album album = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).build();
        List<Album> albums = new ArrayList<>(List.of(album));
        Mockito.when(shopRepository.findAll()).thenReturn(albums);

        List<Album> expected = shopServiceImplementation.getAllAlbums();

        assertAll(() -> {
            assertEquals(expected.size(), albums.size());
            assertEquals(expected, albums);
        });
    }

    @Test
    @DisplayName("getAlbumById responds with the appropriate album")
    void testGetAlbumById() {
        Album album1 = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).build();
        Album album2 = Album.builder().id(2L).name("Purrfection").releaseYear(2022).stock(50).build();
        List<Album> albumList = new ArrayList<>(List.of(album1, album2));
        for(Album album : albumList) {
            Mockito.when(shopRepository.findById(album.getId())).thenReturn(Optional.of(album));
        }
        Album expected1 = shopServiceImplementation.getAlbumById(1L);
        Album expected2 = shopServiceImplementation.getAlbumById(2L);

        assertAll(() -> {
            assertEquals(expected1, album1);
            assertEquals(expected2, album2);
        });
    }

    @Test
    @DisplayName("getAlbumById responds with the appropriate album")
    void testGetAlbumByIdNotFound() {
        Album album1 = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).build();
        Album album2 = Album.builder().id(2L).name("Purrfection").releaseYear(2022).stock(50).build();
        List<Album> albumList = new ArrayList<>(List.of(album1, album2));
        for(Album album : albumList) {
            Mockito.when(shopRepository.findById(album.getId())).thenReturn(Optional.of(album));
        }

        Album expected = shopServiceImplementation.getAlbumById(3L);

         assertNull(expected);
    }
}
