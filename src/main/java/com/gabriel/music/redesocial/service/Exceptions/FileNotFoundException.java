package com.gabriel.music.redesocial.service.Exceptions;

public class FileNotFoundException extends Exception{
    public FileNotFoundException() {
        super("arquivo não encontrado.");
    }
}
