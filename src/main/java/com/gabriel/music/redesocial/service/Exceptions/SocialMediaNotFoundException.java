package com.gabriel.music.redesocial.service.Exceptions;

public class SocialMediaNotFoundException extends Exception {
    public SocialMediaNotFoundException() {
        super("Nenhuma rede social atrelada a este username foi encontrada");
    }
}
