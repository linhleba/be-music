package com.example.music.models;

import java.util.Date;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document
public class Song {
    @Id
    private @Getter @Setter String id;
    private @Getter @Setter String name;
    private @Getter @Setter String author;
    private @Getter @Setter String genre;
    private @Getter @Setter String src;
    private @Getter @Setter String srcId;
    private @Getter @Setter String thumbnail;
    private @Getter @Setter String thumbnailId;
    private @Getter @Setter Date updateAt;

    @Override
    public int hashCode() {
        return this.getId().hashCode() + this.getName().hashCode() + this.getAuthor().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Song songObj = (Song) obj;

        if (this.getId().equals(songObj.getId()))
            return true;

        return this.getAuthor().equalsIgnoreCase(songObj.getAuthor())
                && this.getName().equalsIgnoreCase(songObj.getName());
    }

    public void updateData(Song s) {
        if (s.getName() != null)
            this.name = s.getName();
        if (s.getAuthor() != null)
            this.author = s.getAuthor();
        if (s.getSrc() != null)
            this.src = s.getSrc();
        if (s.getGenre() != null)
            this.genre = s.getGenre();
    }

    public Song(String name, String author, String genre, Date updateAt) {
        this.name = name;
        this.author = author;
        this.genre = genre;
        this.updateAt = updateAt;
    }

}
