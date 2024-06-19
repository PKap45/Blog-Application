package com.myblogrestapi.service.impl;

import com.myblogrestapi.entity.Comment;
import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.ResourceNotFound;
import com.myblogrestapi.payloads.CommentDto;
import com.myblogrestapi.repository.CommentRepository;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.CommentService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private ModelMapper modelMapper;

    private PostRepository postRepository;

    public CommentServiceImpl(CommentRepository commentRepository, ModelMapper modelMapper, PostRepository postRepository) {
        this.commentRepository = commentRepository;
        this.modelMapper = modelMapper;
        this.postRepository = postRepository;
    }

    @Override
    public CommentDto createComment(long postId, CommentDto commentDto) {
        Comment comment = mapToEntity(commentDto);
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not found with id:" + postId)
        );
        comment.setPost(post);
        Comment savedComment = commentRepository.save(comment);
        CommentDto dto = mapToDto(savedComment);
        return dto;

    }

    @Override
    public List<CommentDto> getCommentsByPostId(long postId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not found with id:" + postId)
        );
        List<Comment> getComments = commentRepository.findByPostId(postId);
        return getComments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public CommentDto getComment(long postId, long commentId) {
        Post post = postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not found with id:" + postId)
        );
        Comment comment = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment Not found with id:" + commentId)
        );
        CommentDto dto = mapToDto(comment);
        return dto;
    }

    @Override
    public List<CommentDto> getAllComments() {
        List<Comment> comments = commentRepository.findAll();
        return comments.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    @Override
    public void deleteCommentById(long postId, long commentId) {
       postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not found with id:" + postId)
        );
        commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment Not found with id:" + commentId)
        );
        commentRepository.deleteById(commentId);
    }

    @Override
    public CommentDto updateComment(long postId, long commentId, CommentDto commentDto) {

             postRepository.findById(postId).orElseThrow(
                () -> new ResourceNotFound("Post Not found with id:" + postId)
        );
        Comment comment1 = commentRepository.findById(commentId).orElseThrow(
                () -> new ResourceNotFound("Comment Not found with id:" + commentId)
        );
        comment1.setId(commentDto.getId());
        comment1.setName(commentDto.getName());
        comment1.setEmail(commentDto.getEmail());
        comment1.setBody(commentDto.getBody());

        Comment savedComment = commentRepository.save(comment1);
        CommentDto dto = mapToDto(savedComment);
        return dto;
    }

    CommentDto mapToDto(Comment comment)
    {
        CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
        return commentDto;
    }

    Comment mapToEntity(CommentDto commentDto)
    {
        Comment comment = modelMapper.map(commentDto, Comment.class);
        return comment;

    }
}



