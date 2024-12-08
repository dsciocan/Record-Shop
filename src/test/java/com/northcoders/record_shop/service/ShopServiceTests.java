package com.northcoders.record_shop.service;


import com.northcoders.record_shop.exception.InvalidItemException;
import com.northcoders.record_shop.exception.ItemNotFoundException;
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

    @Mock
    ArtistService artistService;

    @InjectMocks
    ShopServiceImplementation shopServiceImplementation;

//GET ALL ALBUMS
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

    //GET ALBUM BY ID
    @Test
    @DisplayName("getAlbumById responds with the appropriate album")
    void testGetAlbumById() {
        Album album1 = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).build();
        Album album2 = Album.builder().id(2L).name("Purrfection").releaseYear(2022).stock(50).build();
        List<Album> albumList = new ArrayList<>(List.of(album1, album2));
        for(Album album : albumList) {
            Mockito.when(shopRepository.findById(album.getId())).thenReturn(Optional.of(album));
            Mockito.when(shopRepository.existsById(album.getId())).thenReturn(true);
        }
        Album expected1 = shopServiceImplementation.getAlbumById(1L);
        Album expected2 = shopServiceImplementation.getAlbumById(2L);

        assertAll(() -> {
            assertEquals(expected1, album1);
            assertEquals(expected2, album2);
        });
    }

    @Test
    @DisplayName("getAlbumById throws the right exception when album is not found")
    void testGetAlbumByIdNotFound() {
        Album album1 = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).build();
        Album album2 = Album.builder().id(2L).name("Purrfection").releaseYear(2022).stock(50).build();
        List<Album> albumList = new ArrayList<>(List.of(album1, album2));
        for(Album album : albumList) {
            Mockito.when(shopRepository.findById(album.getId())).thenReturn(Optional.of(album));
        }

         assertThrows(ItemNotFoundException.class, () -> shopServiceImplementation.getAlbumById(3L));
    }


    //POST ALBUM
    @Test
    @DisplayName("addAlbum returns the saved album")
    void testAddAlbum() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Artist artist = Artist.builder().name("Artist1").build();
        Album album = Album.builder().name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtist(artist).build();
        Album mockAlbum = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtist(artist).build();

        Mockito.when(shopRepository.save(album)).thenReturn(mockAlbum);
        Mockito.when(artistService.getArtistByName(artist.getName())).thenReturn(artist);

        Album result = shopServiceImplementation.addAlbum(album);
        assertAll(() -> {
            assertEquals(mockAlbum, result);
            assertEquals(1L, result.getId());
                }
        );
    }

    @Test
    @DisplayName("addAlbum throws an error when an album with an existing id is added")
    void testAddAlbumExistingId() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Artist artist = Artist.builder().name("Artist1").build();
        Album album = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtist(artist).build();
        Mockito.when(shopRepository.existsById(album.getId())).thenReturn(true);
        Mockito.when(artistService.getArtistByName(artist.getName())).thenReturn(artist);

        assertThrows(InvalidItemException.class, ()-> shopServiceImplementation.addAlbum(album));
    }
    @Test
    @DisplayName("addAlbum throws the appropriate exception when an album with insufficient/null data is added")
    void testAddAlbumInvalidEntry() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        Artist artist = Artist.builder().name("Artist1").build();
        Album album = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).genreSet(genreList).albumArtist(artist).build();
        Mockito.when(shopRepository.existsById(album.getId())).thenReturn(false);
        Mockito.when(artistService.getArtistByName(artist.getName())).thenReturn(artist);

        assertThrows(InvalidItemException.class, ()-> shopServiceImplementation.addAlbum(album));
    }

    //UPDATE ALBUM
    @Test
    @DisplayName("updateAlbum updates album with data passed in as an argument")
    void testUpdateAlbum() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Artist artist = Artist.builder().name("Artist1").build();
        Album album = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtist(artist).build();
        Album newAlbum = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(50).genreSet(genreList).albumArtist(artist).build();
        Mockito.when(shopRepository.save(album)).thenReturn(newAlbum);
        Mockito.when(shopRepository.existsById(1L)).thenReturn(true);
        Mockito.when(shopRepository.findById(1L)).thenReturn(Optional.of(album));
        Mockito.when(artistService.getArtistByName(artist.getName())).thenReturn(artist);

        assertEquals(newAlbum, shopServiceImplementation.updateAlbum(1L, newAlbum));
    }

    @Test
    @DisplayName("updateAlbum throws the appropriate error when no album can be found at specified id")
    void testUpdateAlbumInvalidId() throws Exception {
        Set<Genre> genreList = new HashSet<>();
        genreList.add(Genre.Dance);
        genreList.add(Genre.Pop);
        Artist artist = Artist.builder().name("Artist1").build();
        Set<Artist> artists = new HashSet<>(List.of(artist));
        Album album = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(100).genreSet(genreList).albumArtist(artist).build();
        Album newAlbum = Album.builder().id(1L).name("Some Album Name").releaseYear(2024).stock(50).genreSet(genreList).albumArtist(artist).build();
        Mockito.when(shopRepository.findById(album.getId())).thenReturn(Optional.of(album));
        Mockito.when(shopRepository.existsById(2L)).thenReturn(false);

        assertThrows(ItemNotFoundException.class, () -> shopServiceImplementation.updateAlbum(2L, newAlbum));
    }


}
