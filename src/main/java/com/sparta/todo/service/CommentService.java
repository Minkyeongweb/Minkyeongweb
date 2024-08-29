package com.sparta.todo.service;

import com.sparta.todo.entity.Comment;
import com.sparta.todo.entity.Todo;
import com.sparta.todo.repository.CommentRepository;
import com.sparta.todo.repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentRepository commentRepository;
    private final TodoRepository todoRepository;

    public Comment createComment(Long todoId, String content, String username) {
        Todo todo = todoRepository.findById(todoId).orElseThrow(() -> new RuntimeException("todo not found"));
        Comment comment = new Comment(content, todo, username);
        todo.addComment(comment);
        return commentRepository.save(comment);
    }
    public Comment getComment(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        }
    public List<Comment> getCommentsByTodoId(Long todoId) {
        return commentRepository.findAllByTodoId(todoId);
    }
    public Comment updateComment(Long commentId, String newContent) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Comment not found"));
        comment.updateContent(newContent);
        return commentRepository.save(comment);
    }
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}

