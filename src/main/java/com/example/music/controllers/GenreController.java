package com.example.music.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.models.Genre;
import com.example.music.models.SongPage;
import com.example.music.res.ResObj;
import com.example.music.services.GenreService;
import com.example.music.services.SongService;

@RestController
@RequestMapping(path = "/api/genre")
public class GenreController {
    @Autowired
    private GenreService genreService;
    @Autowired
    private SongService songService;

    @GetMapping(value = "")
    public ResObj getAllGenres() {
        // logger.info("Getting genre list:...");

        return new ResObj(200, "Success", genreService.getAll());
    }

    @PostMapping(value = "")
    ResObj add(@RequestBody Genre g) {
        if (!genreService.checkGenre(g)) {
            return new ResObj(500, "Genre name existed", null);
        }

        Genre genre = genreService.save(g);

        if (genre != null) {
            return new ResObj(200, "Success", genre);
        } else {
            return new ResObj(500, "Genre with name '" + g.getName() + "' already exist!", null);
        }

    }

    @DeleteMapping(value = "/delete")
    ResponseEntity<ResObj> delete(@RequestParam(name = "id") List<String> list) {
        if (list.size() > 0) {
            for (String s : list) {
                Pageable pageable = PageRequest.of(0, 1);
                SongPage page = songService.getPage("", "", s, 0, 1, pageable);

                if (page.getSongs().size() > 0) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                            new ResObj(500,
                                    "This Genre is match with other songs, delete those songs first", null));
                }

                ResObj status;
                status = genreService.delete(s);
                if (status.equals("NOT_FOUND")) {
                    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                            new ResObj(404, "Cannot find song with id: " + s, null));
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj(200, "Success", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResObj(400, "Missing required ID", null));
        }
    }
}
