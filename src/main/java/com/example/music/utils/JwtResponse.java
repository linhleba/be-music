package com.example.music.utils;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
@Slf4j
public class JwtResponse {
    private @Getter @Setter String token;
    private static final String tokenType = "Bearer";
}
