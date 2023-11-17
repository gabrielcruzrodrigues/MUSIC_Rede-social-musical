package com.gabriel.music.redesocial.service.Exceptions;

public class TypeFileErrorException extends Exception{
    public TypeFileErrorException() {
        super("O tipo de arquivo enviado não é valido");
    }
}
