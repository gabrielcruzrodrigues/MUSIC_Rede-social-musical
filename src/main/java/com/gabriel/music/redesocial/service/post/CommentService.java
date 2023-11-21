package com.gabriel.music.redesocial.service.post;

import com.gabriel.music.redesocial.domain.post.Comment;
import com.gabriel.music.redesocial.domain.post.Post;
import com.gabriel.music.redesocial.domain.user.User;
import com.gabriel.music.redesocial.repository.post.CommentRepository;
import com.gabriel.music.redesocial.service.Exceptions.ErrorDeleteException;
import com.gabriel.music.redesocial.service.post.exceptions.CommentNotFoundException;
import com.gabriel.music.redesocial.service.post.exceptions.PostNotFoundException;
import com.gabriel.music.redesocial.service.user.UserService;
import com.gabriel.music.redesocial.service.user.exceptions.UserNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private PostService postService;

    public List<Comment> findAll() {
        return commentRepository.findAll();
    }

    @Transactional
    public Comment save(String comment, String username, String codec) throws UserNotFoundException, PostNotFoundException {
        User user = userService.findByUsernameForServer(username);
        Post post = postService.findByCodec(codec);
        Comment newComment = new Comment(comment, user, post);
        return commentRepository.save(newComment);
    }

    private Comment findById(Long id) throws CommentNotFoundException {
        Optional<Comment> comment = commentRepository.findById(id);
        return comment.orElseThrow(CommentNotFoundException::new);
    }

    public void delete(Long id) throws CommentNotFoundException, ErrorDeleteException {
        this.findById(id);
        try {
            commentRepository.deleteById(id);
        } catch (Exception e) {
            throw new ErrorDeleteException("não foi possível deletar. Error: " + e.getMessage());
        }
    }

    @Transactional
    public void addLikeInComment(Long id) throws CommentNotFoundException {
        Comment comment = this.findById(id);
        comment.setLikes(comment.getLikes() + 1);
        commentRepository.save(comment);
    }

    @Transactional
    public void removeLikeInComment(Long id) throws CommentNotFoundException {
        Comment comment = this.findById(id);
        if (comment.getLikes() > 0) {
            comment.setLikes(comment.getLikes() - 1);
        }
        commentRepository.save(comment);
    }
}
