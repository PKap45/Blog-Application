package com.myblogrestapi.controller;

import com.myblogrestapi.payloads.CommentDto;
import com.myblogrestapi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")

public class CommentController {
    @Autowired
private CommentService commentService;


    @PostMapping("/posts/{postId}/comments")
    public ResponseEntity<CommentDto> savedComment(@PathVariable("postId") long postId,@RequestBody CommentDto commentDto)
    {
        CommentDto dto = commentService.createComment(postId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.CREATED);
    }

    @GetMapping("/post/{postId}/comments")
    public List<CommentDto> getCommentsByPostId(@PathVariable("postId") long postId)
    {
        return commentService.getCommentsByPostId(postId);

    }

    @GetMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> getComment(@PathVariable("postId") long postId,
                                                 @PathVariable("commentId")long commentId)
    {
        CommentDto commentDto = commentService.getComment(postId, commentId);
        return new ResponseEntity<>(commentDto,HttpStatus.OK);

    }

    @GetMapping("/comments")
    public List<CommentDto> getAllComments()
    {
        List<CommentDto> dtos = commentService.getAllComments();
        return dtos;
    }

    @PutMapping("/post/{postId}/comment/{commentId}")
    public ResponseEntity<CommentDto> updateComment( @PathVariable("postId") long postId,
        @PathVariable("commentId") long commentId,@RequestBody CommentDto commentDto)
    {
        CommentDto dto = commentService.updateComment(postId, commentId, commentDto);
        return new ResponseEntity<>(dto,HttpStatus.OK);
    }

}
