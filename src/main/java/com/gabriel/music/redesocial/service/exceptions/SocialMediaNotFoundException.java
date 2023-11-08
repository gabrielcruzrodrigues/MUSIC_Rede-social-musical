package com.gabriel.music.redesocial.service.exceptions;

public class SocialMediaNotFoundException extends Exception {
    public SocialMediaNotFoundException() {
        super("Nenhuma rede social atrelada a este username foi encontrada");
    }
}
