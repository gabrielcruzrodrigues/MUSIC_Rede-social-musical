package com.gabriel.music.redesocial.service.post.exceptions;

public class PostNotFoundException extends Exception{
    public PostNotFoundException() {
        super("post não encontrado");
    }
}
