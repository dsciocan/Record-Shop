package com.northcoders.record_shop.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.CascadeType;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


import java.io.Serial;
import java.util.HashSet;
import java.util.Set;

@Entity
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Album {

    //Fields and columns

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    @Serial
    @Column(updatable = false, nullable = false)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name="year_of_release", nullable = false)
    @Min(value = 0) @Max(value = 2024)
    private Integer releaseYear;

    @Column(nullable = false)
    @Min(value = 0)
    private Integer stock;

//    Many-to-many relationships

    @ElementCollection(targetClass = Genre.class)
    @CollectionTable(name = "album_genres", joinColumns = @JoinColumn(name = "album_id"))
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Enumerated(EnumType.STRING)
    @Column(name = "genres")
    private Set <Genre> genreSet = new HashSet<>();

    @ManyToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    @Cascade({CascadeType.ALL})
    @JoinColumn(name = "artist")
    private Artist albumArtist;


    public void addGenre(Genre genre) {
        genreSet.add(genre);
    }
}
