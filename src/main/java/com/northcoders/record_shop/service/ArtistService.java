package com.northcoders.record_shop.service;

import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.model.Artist;

import java.util.List;

public interface ArtistService{
    Artist getArtistByName(String name);
    List<Artist> getAllArtists();
    Artist getArtistById(Long id);
    Artist addArtist(Artist artist) throws Exception;
}
