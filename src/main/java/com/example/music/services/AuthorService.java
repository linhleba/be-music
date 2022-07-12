package com.example.music.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.example.music.models.Author;
import com.example.music.repositories.AuthorRepository;
import com.example.music.res.ResObj;

@Service
public class AuthorService {
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public List<Author> getAll() {
        return authorRepository.findAll();
    }

    public Author save(Author author) {
        authorRepository.save(author);
        return author;
    }

    public ResObj deleteAuthors(List<String> ids) {
        try {

            for (String id : ids) {
                // Handle check songs existed

                Optional<Author> authorRes = authorRepository.findById(id);
                if (authorRes.isPresent()) {

                    authorRepository.deleteById(id);

                }
            }
            return new ResObj(200, "deleted successfully", null);

        } catch (Exception e) {
            return new ResObj(500, "deleted failed", null);
        }
    }

    public Optional<Author> getById(String id) {
        return authorRepository.findById(id);
    }
}
