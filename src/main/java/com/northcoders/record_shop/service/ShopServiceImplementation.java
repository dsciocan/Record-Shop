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
    public Album getAlbumById(Long id){
        if(shopRepository.existsById(id)) {
            return shopRepository.findById(id).orElse(null);
        } else {
            throw new ItemNotFoundException(String.format("Could not find album with an id of %s", id));
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

    @Override
    public Album updateAlbum(Long id, Album newAlbum) {
        if(!shopRepository.existsById(id)) {
            throw new ItemNotFoundException(String.format("Could not find album with an id of %s", id));
        } else {
            Album album = shopRepository.findById(id).orElse(null);
            album.setName(newAlbum.getName());
            album.setReleaseYear(newAlbum.getReleaseYear());
            album.setStock(newAlbum.getStock());
            album.setGenreSet(newAlbum.getGenreSet());
            album.setAlbumArtists(newAlbum.getAlbumArtists());
            return shopRepository.save(album);
        }
    }
}
