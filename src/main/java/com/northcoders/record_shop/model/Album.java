package com.northcoders.record_shop.model;

import jakarta.persistence.*;


import java.util.Set;

@Entity
public class Album {

    //Fields and columns

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column
    String name;

    @Column(name="year_of_release")
    Integer releaseYear;

    @Column
    Integer stock;

    //Many-to-many relationships
    @ManyToMany
    @JoinTable(
            name = "album_genres",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    Set<Genres> albumGenres;

    @ManyToMany
    @JoinTable(
            name = "album_artists",
            joinColumns = @JoinColumn(name = "id"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    Set<Artist> albumArtists;


    //Constrictor

    public Album() {
    }

    //Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(Integer releaseYear) {
        this.releaseYear = releaseYear;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Set<Genres> getGenre() {
        return albumGenres;
    }

    public void setGenre(Set<Genres> genre) {
        this.albumGenres = genre;
    }

    public Set<Artist> getArtists() {
        return albumArtists;
    }

    public void setArtists(Set<Artist> artists) {
        this.albumArtists = artists;
    }
}
