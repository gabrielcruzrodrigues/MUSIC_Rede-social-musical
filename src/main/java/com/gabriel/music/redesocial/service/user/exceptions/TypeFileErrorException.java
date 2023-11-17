package com.gabriel.music.redesocial.service.user.exceptions;

public class TypeFileErrorException extends Exception{
    public TypeFileErrorException() {
        super("O tipo de arquivo enviado não é valido");
    }
}
