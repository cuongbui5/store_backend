package com.example.storeback.exception;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WrongUsernameOrEmail extends RuntimeException{
    public WrongUsernameOrEmail(String message) {
        super(message);
    }
}
