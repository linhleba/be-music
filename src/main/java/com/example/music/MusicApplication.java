package com.example.music;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

@EnableMongoRepositories
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class MusicApplication {

	public static void main(String[] args) {
		SpringApplication.run(MusicApplication.class, args);

	}

	@Bean
	public MongoTemplate mongoTemplate() {
		MongoClient mongodb = MongoClients
				.create("mongodb+srv://linhlb:1235@cluster0.e8zbvyf.mongodb.net/?retryWrites=true&w=majority");
		return new MongoTemplate(mongodb, "springify");

	}
	// MongoDatabase db = mongodb.getDatabase("musicmanager");

	// MongoCollection col = db.getCollection("musicmanagercollection");

	// Document sampleDoc = new Document("_id", "1").append("name", "John Smith");

	// col.insertOne(sampleDoc);

}
