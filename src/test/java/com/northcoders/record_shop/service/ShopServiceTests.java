package com.northcoders.record_shop.service;


import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.model.Artist;
import com.northcoders.record_shop.model.Genre;
import com.northcoders.record_shop.repository.ShopRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.*;

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


    @Test
    @DisplayName("addAlbum returns the saved album")
    void testAddAlbum() {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Set<Artist> artists = new HashSet<>(List.of(new Artist("Artist1")));
        Album album = Album.builder().name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtists(artists).build();
        Album mockAlbum = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtists(artists).build();

        Mockito.when(shopRepository.save(album)).thenReturn(mockAlbum);
        Album result = shopServiceImplementation.addAlbum(album);
        assertAll(() -> {
            assertEquals(mockAlbum, result);
            assertEquals(1L, result.getId());
                }
        );
    }

    @Test
    @DisplayName("addAlbum throws an error when an album with an existing id is added")
    void testAddAlbum() {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Set<Artist> artists = new HashSet<>(List.of(new Artist("Artist1")));
        Album album = Album.builder().name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtists(artists).build();
        Mockito.when(shopRepository.existsById(album.getId())).thenReturn(true);

        assertThrows(Exception.class, shopServiceImplementation.addAlbum(album));

    }

}
