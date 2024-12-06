package com.northcoders.record_shop.service;

import com.northcoders.record_shop.exception.InvalidItemException;
import com.northcoders.record_shop.exception.ItemNotFoundException;
import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImplementation implements ShopService{
    @Autowired
    ShopRepository shopRepository;

    @Override
    public List<Album> getAllAlbums() {
        List<Album> albumList = new ArrayList<>();
        shopRepository.findAll().forEach(albumList::add);
        return albumList;
    }
    @Override
    public Album getAlbumById(Long id) {
        if(shopRepository.existsById(id)) {
            return shopRepository.findById(id).orElse(null);
        } else {
            throw new ItemNotFoundException(String.format("Could not find item with an id of %s", id));
        }
    }

    @Override
    public Album addAlbum(Album album) throws Exception {
        if (shopRepository.existsById(album.getId())) {
            throw new InvalidItemException("Cannot save album as its id is already in use.");
        } else if (album.getName().isEmpty() || album.getReleaseYear() == null || album.getStock() == null || album.getAlbumArtists().isEmpty() || album.getGenreSet().isEmpty()) {
            throw new InvalidItemException("Incomplete album entry, please specify all values.");
        } else {
            return shopRepository.save(album);
        }
    }
}
