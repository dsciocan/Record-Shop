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
    ArtistService artistService;

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
        if(!shopRepository.existsById(id)) {
            throw new ItemNotFoundException(String.format("Could not find album with an id of %s", id));
        } else {
            return shopRepository.findById(id).orElse(null);
        }
    }

    @Override
    public Album addAlbum(Album album) throws Exception {
//        if (shopRepository.existsById(album.getId())) {
//            throw new InvalidItemException("Cannot save album as its id is already in use.");
         if (album.getName().isEmpty() || album.getReleaseYear() == null || album.getStock() == null || album.getAlbumArtist() == null || album.getGenreSet().isEmpty()) {
            throw new InvalidItemException("Incomplete album entry, please specify all values.");
        } else {
             album.setAlbumArtist(artistService.getArtistByName(album.getAlbumArtist().getName()));
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
            album.setAlbumArtist(artistService.getArtistByName(newAlbum.getAlbumArtist().getName()));
            album.setImage(newAlbum.getImage());
            return shopRepository.save(album);
        }
    }

    @Override
    public String deleteAlbum(Long id) {
        if(shopRepository.existsById(id)) {
            shopRepository.deleteById(id);
            return "Album deleted successfully.";
        } else {
            throw new ItemNotFoundException(String.format("Album at the id of %s does not exist or may have already been deleted", id));
        }
    }
}
