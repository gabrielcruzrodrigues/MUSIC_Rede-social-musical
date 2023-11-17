package com.gabriel.music.redesocial.service.user.exceptions;

public class FileNotFoundException extends Exception{
    public FileNotFoundException() {
        super("arquivo não encontrado.");
    }
}
