package com.gabriel.music.redesocial.service.user.exceptions;

public class UserNotFoundException extends Exception{
    public UserNotFoundException() {
        super("usuario não encontrado.");
    }
}
