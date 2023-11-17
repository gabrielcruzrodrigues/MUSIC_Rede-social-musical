package com.gabriel.music.redesocial.service.user.exceptions;

public class FileNullContentException extends Exception{
    public FileNullContentException() {
        super("O arquivo não tem nenhum conteúdo.");
    }
}
