package com.myblogrestapi.service;

import com.myblogrestapi.payloads.CommentDto;

import java.util.List;

public interface CommentService {

    CommentDto createComment(long postId, CommentDto commentDto);

    List<CommentDto> getCommentsByPostId(long postId);

    CommentDto getComment(long postId, long commentId);

    List<CommentDto> getAllComments();

    void deleteCommentById(long postId, long commentId);

    CommentDto updateComment(long postId, long commentId,CommentDto commentDto);
}
