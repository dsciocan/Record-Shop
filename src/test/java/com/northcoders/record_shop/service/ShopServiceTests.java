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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

}
