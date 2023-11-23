package com.gabriel.music.redesocial.infra.security.exception;

public class GenerateTokenErrorException extends Exception {
    public GenerateTokenErrorException(String error) {
        super("Erro ao gerar token, motivo: " + error);
    }
}
