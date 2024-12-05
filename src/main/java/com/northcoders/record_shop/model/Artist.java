package com.northcoders.record_shop.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Entity
@NoArgsConstructor
@Data
public class Artist {

    @Id
    @GeneratedValue
    @Column(updatable = false, nullable = false)
    Long id;

    @Column (unique = true, nullable = false)
    String name;

    @ManyToMany(mappedBy = "albumArtists")
    Set<Album> albums;

    public Artist(String name) {
        this.name = name;
    }

}
