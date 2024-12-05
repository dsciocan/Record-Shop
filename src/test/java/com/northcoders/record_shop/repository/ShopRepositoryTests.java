package com.northcoders.record_shop.repository;

import com.northcoders.record_shop.model.Album;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.internal.util.collections.Iterables;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DataJpaTest
public class ShopRepositoryTests {

    @Autowired
    ShopRepository shopRepository;

    @Test
    public void testRespository() {
        Album sampleAlbum = Album.builder().name("The Sample").releaseYear(2023).stock(100).build();

        shopRepository.save(sampleAlbum);

        assertEquals(shopRepository.findAll().spliterator().getExactSizeIfKnown(), 1);
    }
}
