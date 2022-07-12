package com.example.music.dto;

import java.util.Date;

import com.example.music.models.Author;
import com.example.music.models.Genre;
import com.example.music.models.Song;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString

public class SongDto {
    private @Getter @Setter String id;
    private @Getter @Setter String name;
    private @Getter @Setter Author author;
    private @Getter @Setter Genre genre;
    private @Getter @Setter String src;
    private @Getter @Setter String thumbnail;
    private @Getter @Setter Date updateAt;

    public void clone(Song song) {
        this.id = song.getId();
        this.name = song.getName();
        this.src = song.getSrc();
        this.thumbnail = song.getThumbnail();
        this.updateAt = song.getUpdateAt();
    }
}
