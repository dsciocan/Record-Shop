package com.northcoders.record_shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    //Fields and columns

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column(nullable = false)
    String name;

    @Column(name="year_of_release", nullable = false)
    @Min(value = 0) @Max(value = 2024)
    Integer releaseYear;

    @Column(nullable = false)
    @Min(value = 0)
    Integer stock;

//    Many-to-many relationships

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "album_genres", joinColumns = @JoinColumn(name = "album_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    Set <Genre> genreSet;

    @ManyToMany
    @JoinTable(
            name = "album_artists",
            joinColumns = @JoinColumn(name = "artist_id"),
            inverseJoinColumns = @JoinColumn(name = "album_id"))
    Set<Artist> albumArtists;


}
