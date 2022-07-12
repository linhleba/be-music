package com.example.music.controllers;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.music.dto.SongDto;
import com.example.music.models.Song;
import com.example.music.models.SongPage;
import com.example.music.res.ResObj;
import com.example.music.services.SongService;
import com.example.music.utils.SongSourceUpload;
import com.example.music.utils.SongThumbnailUpload;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "/api/song")
public class SongController {
    @Autowired
    private SongService service;
    private static final Logger logger = LoggerFactory.getLogger(SongController.class);

    @GetMapping("")
    ResponseEntity<ResObj> getAll(@RequestParam(required = false, value = "shuffle") Boolean shuffle) {
        logger.info("Getting song list:... with shuffle = " + shuffle);

        if (shuffle == null) {
            shuffle = false;
        }

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResObj(200, "Success", service.findAllDto(shuffle)));
    }

    @GetMapping(path = "/count")
    ResponseEntity<ResObj> count(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "author") String author,
            @RequestParam(required = false, value = "genre") String genre) {
        logger.info("Counting songs:...");

        if (name == null)
            name = "";
        if (author == null)
            author = "";
        if (genre == null)
            genre = "";

        return ResponseEntity.status(HttpStatus.OK).body(
                new ResObj(200, "Success", service.count(name, author, genre)));
    }

    @GetMapping(path = "/get")
    ResponseEntity<ResObj> getById(@RequestParam("id") String id) {
        SongDto song = service.findById(id);

        if (song != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj(200, "Success", song));
        } else {
            // throw new SongNotFoundException();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                    new ResObj(404, "Song not found", null));
        }
    }

    @GetMapping(path = "/page")
    ResponseEntity<ResObj> getSongPage(
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "author") String author,
            @RequestParam(required = false, value = "genre") String genre,
            @RequestParam("page") Integer index,
            @RequestParam("limit") Integer limit) {

        if (index == null)
            index = 0;
        if (limit == null)
            limit = 4;
        if (name == null)
            name = "";
        if (author == null)
            author = "";
        if (genre == null)
            genre = "";

        Pageable pageable = PageRequest.of(index, limit);

        SongPage page = service.getPage(name, author, genre, index, limit, pageable);

        if (page != null) {
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj(200, "Success", page));
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                new ResObj(404, "Cannot find song with page: " + index, null));
    }

    @PutMapping(value = "/update", consumes = "application/json", produces = "application/json")
    ResponseEntity<ResObj> updateSong(@RequestBody Song s) {
        if (s.getId() != null) {

            Song afterEdit = service.editSong(s);

            if (afterEdit != null) {

                if (!service.checkSong(afterEdit)) {
                    return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                            new ResObj(406, "Author already has this song name", null));
                }

                service.save(afterEdit);

                return ResponseEntity.status(HttpStatus.OK).body(
                        new ResObj(200, "Success", afterEdit));
            } else {
                // throw new SongNotFoundException();
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(
                        new ResObj(404, "Song not found", null));
            }

        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResObj(406, "Missing required ID", null));
        }
    }

    @DeleteMapping(value = "/delete")
    ResponseEntity<ResObj> deleteSong(@RequestParam(name = "id") List<String> list) {
        if (list.size() > 0) {
            Song song = new Song();
            for (String s : list) {
                song.setId(s);
                String status;

                try {
                    status = service.delete(song);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                if (status == null) {
                    // throw new SongNotFoundException();
                }
            }
            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj(200, "Success", null));
        } else {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResObj(406, "Missing required ID", null));
        }
    }

    @PostMapping(value = "/upload")
    public ResponseEntity<ResObj> uploadFile(@RequestParam MultipartFile src,
            @RequestParam(required = false) MultipartFile thumbnail, @RequestParam String name,
            @RequestParam String author, @RequestParam String genre) {
        logger.info(String.format("File name '%s' with name '%s' author '%s' genre '%s' uploaded successfully.",
                src.getOriginalFilename(), name, author, genre));

        SongSourceUpload songSourceUpload = new SongSourceUpload();
        songSourceUpload.setFile(src);
        songSourceUpload.setTitle(name);

        SongThumbnailUpload songThumbnailUpload = new SongThumbnailUpload();
        songThumbnailUpload.setFile(thumbnail);
        songThumbnailUpload.setTitle(name);

        Song song = new Song(name, author, genre, new Date());

        if (!service.checkSong(song)) {
            return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(
                    new ResObj(406, "Author already has this song name", null));
        }

        try {
            Song afterUpload = service.uploadSongSource(song, songSourceUpload);
            if (songThumbnailUpload.getFile() != null) {
                afterUpload = service.uploadSongThumbnail(song, songThumbnailUpload);
            }

            logger.info("Saving...:" + service.save(afterUpload));

            return ResponseEntity.status(HttpStatus.OK).body(
                    new ResObj(200, "Success", afterUpload));

        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResObj(500, "Error while upload file", null));
        }
    }

    // @GetMapping(value = "/getRelated")
    // ResponseEntity<ResObj> getRelated(@RequestParam("id") String id) {

    // List<SongDto> relatedList = service.getRelatedSong(id);
    // if (relatedList == null)
    // // throw new SongNotFoundException();

    // return ResponseEntity.status(HttpStatus.OK).body(
    // new ResObj(200, "success", service.getRelatedSong(id)));
    // }
}
