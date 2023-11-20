package com.gabriel.music.redesocial.service.Exceptions;

public class UserWithoutRequiredInformationException extends Exception {
    public UserWithoutRequiredInformationException() {
        super("O usuário não tem informações necessárias cadastradas para executar esta ação!");
    }
}
