package com.example.music.models;

import org.springframework.data.annotation.Id;
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
public class Author {
    @Id
    public @Getter String id;
    public @Getter @Setter String name;

    @Override
    public int hashCode() {
        return this.getName().hashCode() + 30;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || obj.getClass() != this.getClass())
            return false;

        Author authorObj = (Author) obj;
        return this.getName().equalsIgnoreCase(authorObj.getName());
    }

}
