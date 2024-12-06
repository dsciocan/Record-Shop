package com.northcoders.record_shop.service;

import com.northcoders.record_shop.model.Album;

import java.util.List;

public interface ShopService {
    List<Album> getAllAlbums();
    Album getAlbumById(Long id);
    Album addAlbum(Album album) throws Exception;
    Album updateAlbum(Long id, Album newAlbum) throws Exception;
}
