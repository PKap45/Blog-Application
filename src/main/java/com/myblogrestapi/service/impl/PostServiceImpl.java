package com.myblogrestapi.service.impl;

import com.myblogrestapi.entity.Post;
import com.myblogrestapi.exception.ResourceNotFound;
import com.myblogrestapi.payloads.PostDto;
import com.myblogrestapi.payloads.PostResponse;
import com.myblogrestapi.repository.PostRepository;
import com.myblogrestapi.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class PostServiceImpl implements PostService {


    private PostRepository postRepository;
    private ModelMapper modelMapper;

    public PostServiceImpl(PostRepository postRepository, ModelMapper modelMapper) {
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PostDto savePost(PostDto postDto) {

        Post post = mapToEntity(postDto);
        Post savedPost = postRepository.save(post);
        PostDto dto = mapToDto(savedPost);
        return dto;
    }

    @Override
    public void deletePost(long id) {
        postRepository.deleteById(id);
    }

    @Override
    public PostDto updatePost(long id, PostDto postDto) {
       Post post =  postRepository.findById(id).orElseThrow(
               ()->new ResourceNotFound("Post Not found with id:"+id)
       );
       post.setId(postDto.getId());
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
        Post updatedPost = postRepository.save(post);

        PostDto dto = mapToDto(updatedPost);
        return dto;

    }

    @Override
    public PostDto getPostById(long id) {
        Post post = postRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Post Not found with id:" + id)
        );
        PostDto dto = mapToDto(post);
        return dto;
    }

    @Override
    public PostResponse getPosts(int pageNo, int pageSize, String sortBy, String sortDir) {
         Sort.Direction direction = Sort.Direction.ASC;
         if(sortDir != null && sortDir.equalsIgnoreCase("desc") )
         {
             direction= Sort.Direction.DESC;
         }
        Pageable pageable =PageRequest.of(pageNo,pageSize, Sort.by(direction, sortBy));
        Page<Post> pagePosts = postRepository.findAll(pageable);

        List<Post> posts = pagePosts.getContent();
        List<PostDto> dtos = posts.stream().map(post -> mapToDto(post)).collect(Collectors.toList());

        PostResponse postResponse = new PostResponse();
        postResponse.setPostDto(dtos);
        postResponse.setPageNo(pagePosts.getNumber());
        postResponse.setPageSize(pagePosts.getSize());
        postResponse.setTotalElements((int) pagePosts.getTotalElements());
        postResponse.setTotalPages(pagePosts.getTotalPages());
        postResponse.setLast(pagePosts.isLast());
        return postResponse;
    }

    PostDto mapToDto(Post post){
        PostDto dto= modelMapper.map(post, PostDto.class);
       //PostDto dto = new PostDto();

      /* dto.setId(post.getId());
       dto.setTitle(post.getTitle());
       dto.setDescription(post.getDescription());
       dto.setContent(post.getContent());
       */
       return dto;

   }
   Post mapToEntity(PostDto postDto)
   {
       Post post = modelMapper.map(postDto, Post.class);
       /*
       Post post = new Post();
       post.setId(postDto.getId());
       post.setTitle(postDto.getTitle());
       post.setDescription(postDto.getDescription());
       post.setContent(postDto.getContent());
       */
       return post;
   }
}
