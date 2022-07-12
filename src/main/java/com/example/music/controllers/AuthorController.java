package com.example.music.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.music.models.Author;
import com.example.music.res.ResObj;
import com.example.music.services.AuthorService;

@RestController
@RequestMapping(path = "/api/author")
public class AuthorController {
    @Autowired
    private AuthorService authorService;

    @PostMapping("")
    public ResponseEntity<Author> AddAuthor(@RequestBody Author a) {
        return new ResponseEntity<Author>(authorService.save(a),
                HttpStatus.CREATED);
    }

    @GetMapping()
    public List<Author> getAllAuthors() {
        // logger.info("Getting author list:...");
        return authorService.getAll();
    }

    @DeleteMapping("/delete/{ids}")
    public ResObj deleteEmployee(@PathVariable List<String> ids) {
        return authorService.deleteAuthors(ids);
    }

}
