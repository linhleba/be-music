package com.example.music.models;

import org.springframework.data.annotation.Id;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {

    @Id
    private @Getter @Setter String id;
    private @Getter @Setter String username;
    private @Getter @Setter String password;
}
