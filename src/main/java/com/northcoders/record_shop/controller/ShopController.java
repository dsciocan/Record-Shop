package com.northcoders.record_shop.controller;

import com.fasterxml.jackson.databind.util.JSONPObject;
import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.repository.ShopRepository;
import com.northcoders.record_shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/album")
public class ShopController {

    @Autowired
    ShopService shopService;

    @GetMapping
    public ResponseEntity<List<Album>> getAllAlbums() {
        return new ResponseEntity<>(shopService.getAllAlbums(), HttpStatus.OK);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<Album> getAlbumById(@PathVariable Long albumId) throws Exception {
        Album result = shopService.getAlbumById(albumId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> postAlbum(@RequestBody Album album) throws Exception {
//        Album fullAlbum = Album.builder().name(album.getName()).releaseYear(album.getReleaseYear()).stock(album.getStock()).genreSet(album.getGenreSet()).albumArtist(album.getAlbumArtist()).build();
//        fullAlbum.getAlbumArtists().forEach(artist -> shopService.saveArtist(artist.getName()));
        return new ResponseEntity<>(shopService.addAlbum(album), HttpStatus.CREATED);
    }

    @PutMapping("/{albumId}")
    public ResponseEntity<Album> putAlbum(@PathVariable Long albumId, @RequestBody Album newAlbum) throws Exception {
        return new ResponseEntity<>(shopService.updateAlbum(albumId, newAlbum), HttpStatus.OK);
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<String> deleteAlbum(@PathVariable Long albumId) throws Exception {
        return new ResponseEntity<>(shopService.deleteAlbum(albumId), HttpStatus.OK);
    }
}





