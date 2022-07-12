package com.example.music.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import com.example.music.models.Genre;
import com.example.music.repositories.GenreRepository;
import com.example.music.res.ResObj;

@Service
public class GenreService {
    @Autowired
    private GenreRepository genreRepository;

    @Autowired
    private MongoTemplate mongoTemplate;

    public Optional<Genre> getById(String id) {
        return genreRepository.findById(id);
    }

    public List<Genre> findByName(String name) {
        Query query = new Query();
        query.addCriteria(Criteria.where("name").regex(name, "i"));

        return mongoTemplate.find(query, Genre.class, "genre");
    }

    public List<Genre> getAll() {
        Query query = new Query();
        query.with(Sort.by(Sort.Direction.ASC, "name"));

        mongoTemplate.find(query, Genre.class, "genre");

        return genreRepository.findAll();
    }

    public Boolean checkGenre(Genre genre) {
        List<Genre> genres = findByName(genre.getName());
        for (Genre g : genres) {
            if (g.equals(genre))
                return false;
        }
        return true;
    }

    public Genre save(Genre genre) {
        genreRepository.save(genre);
        return genre;
    }

    public ResObj delete(String id) {
        if (getById(id).isPresent()) {

            genreRepository.deleteById(id);
            return new ResObj(200, "deleted successfully", null);
        } else {
            return new ResObj(500, "deleted failed", null);
        }
    }

    public Genre update(Genre genre) {
        if (findByName(genre.getName()).size() > 0) {
            return null;
        } else {
            genreRepository.save(genre);
            return genre;
        }
    }

}
