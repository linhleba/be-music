package com.example.music.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.example.music.models.Genre;

@Repository
public interface GenreRepository extends MongoRepository<Genre, String> {

}
