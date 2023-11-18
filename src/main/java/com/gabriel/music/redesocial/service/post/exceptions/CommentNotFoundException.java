package com.gabriel.music.redesocial.service.post.exceptions;

public class CommentNotFoundException extends Exception {
    public CommentNotFoundException() {
        super("Comentario não encontrado!");
    }
}
