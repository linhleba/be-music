package com.example.music.models;

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
public class Genre {
    private @Getter @Setter String id;
    private @Getter @Setter String name;

    @Override
    public int hashCode() {
        return this.getName().hashCode() + 32;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Genre genreObj = (Genre) obj;

        if (this.getId().equals(genreObj.getId()))
            return true;

        return this.getName().equalsIgnoreCase(genreObj.getName());
    }

}
