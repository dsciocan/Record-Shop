package com.northcoders.record_shop.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;

import java.util.Set;

@Entity
public class Genres {
    @Id
    Long id;

    @Column
    Genre genres;

    @ManyToMany(mappedBy = "albumGenres")
    Set<Album> associatedAlbums;

    public Genres() {
    }

    public Long getId() {
        return id;
    }

    public Genre getGenres() {
        return genres;
    }

    public Set<Album> getAssociatedAlbums() {
        return associatedAlbums;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setGenres(Genre genres) {
        this.genres = genres;
    }

    public void setAssociatedAlbums(Set<Album> associatedAlbums) {
        this.associatedAlbums = associatedAlbums;
    }
}
