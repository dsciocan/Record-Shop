package com.northcoders.record_shop.controller;

import com.northcoders.record_shop.model.Album;
import com.northcoders.record_shop.repository.ShopRepository;
import com.northcoders.record_shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseEntity<Album> getAlbumById(@PathVariable Long albumId) {
        Album result = shopService.getAlbumById(albumId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Album> postAlbum(@RequestBody Album album) throws Exception {
        return new ResponseEntity<>(shopService.addAlbum(album), HttpStatus.CREATED);
    }
}





