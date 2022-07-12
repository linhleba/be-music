package com.example.music.res;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ResObj {
    private @Getter @Setter long status;
    private @Getter @Setter String message;
    private @Getter @Setter Object object;

}
