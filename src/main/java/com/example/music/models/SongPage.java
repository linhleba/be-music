package com.example.music.models;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.music.dto.SongDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SongPage {
    private @Getter @Setter List<SongDto> songs;
    private @Getter @Setter Pageable pageable;
}
