package com.gabriel.music.redesocial.service.exceptions;

public class UserMidiaNotFoundException extends Exception{
    public UserMidiaNotFoundException() {
        super("midia não encontrada para este usuário");
    }
}
