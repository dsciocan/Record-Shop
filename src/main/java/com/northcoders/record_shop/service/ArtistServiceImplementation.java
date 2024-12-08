package com.northcoders.record_shop.service;

import com.northcoders.record_shop.exception.InvalidItemException;
import com.northcoders.record_shop.exception.ItemNotFoundException;
import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.model.Artist;
import com.northcoders.record_shop.repository.ArtistRepository;
import com.northcoders.record_shop.repository.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ArtistServiceImplementation implements ArtistService{

    @Autowired
    ArtistRepository artistRepository;

    @Override
    public Artist getArtistByName(String name) {
        for(Artist artist : artistRepository.findAll()) {
            if(artist.getName().equalsIgnoreCase(name)) {
                return artist;
            }
        }
        Artist newArtist = Artist.builder().name(name).build();
        artistRepository.save(newArtist);
        return newArtist;
    }

    @Override
    public List<Artist> getAllArtists() {
        List<Artist> artistList = new ArrayList<>();
        artistRepository.findAll().forEach(artistList::add);
        return artistList;
    }

    @Override
    public Artist getArtistById(Long id){
        if(!artistRepository.existsById(id)) {
            throw new ItemNotFoundException(String.format("Could not find artist with an id of %s", id));
        } else {
            return artistRepository.findById(id).orElse(null);
        }
    }

    @Override
    public Artist addArtist(Artist artist) throws Exception {
//        if (artistRepository.existsById(artist.getId())) {
//            throw new InvalidItemException("Cannot save artist as its id is already in use.");
        if (artist.getName().isEmpty()) {
            throw new InvalidItemException("Incomplete artist entry, please specify all values.");
        } else {
            return artistRepository.save(artist);
        }
    }
}
