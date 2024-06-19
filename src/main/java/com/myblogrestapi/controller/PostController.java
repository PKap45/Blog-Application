package com.myblogrestapi.controller;

import com.myblogrestapi.payloads.PostDto;
import com.myblogrestapi.payloads.PostResponse;
import com.myblogrestapi.service.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    private PostService postService;

    public PostController(PostService postService) {
        this.postService = postService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    //http://localhost:8080/api/post
    @PostMapping
    public ResponseEntity<?> savePost(@Valid  @RequestBody PostDto postDto, BindingResult bindingResult) {
        if(bindingResult.hasErrors())
        {
            return new ResponseEntity<>(bindingResult.getFieldError().getDefaultMessage(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        PostDto dto = postService.savePost(postDto);
        return new ResponseEntity<>(dto, HttpStatus.CREATED);//201
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePost(@PathVariable(name = "id") long id) {
        postService.deletePost(id);
        return new ResponseEntity<>("Post is Deleted", HttpStatus.OK);//STATUS CODE:200
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<PostDto> updatePost(@PathVariable(name = "id") long id, @RequestBody PostDto postDto) {
        PostDto dtoo = postService.updatePost(id, postDto);
        return new ResponseEntity<>(dtoo,HttpStatus.OK);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostDto> getPostById(@PathVariable("id") long id) {
        PostDto postDto = postService.getPostById(id);
        return new ResponseEntity<>(postDto,HttpStatus.OK);
    }

   // http://localhost:8080/api/post?pageNo=0&pageSize=5
    @GetMapping
        public PostResponse getPosts(
                @RequestParam(value="pageNo",defaultValue = "0",required = false) int pageNo,
                @RequestParam(value="pageSize",defaultValue = "5",required = false) int pageSize,
                @RequestParam(value="sortBy",defaultValue = "id",required = false)   String sortBy,
                @RequestParam(value="sortDir",defaultValue = "asc",required = false) String sortDir
         )
            {
                PostResponse postResponse = postService.getPosts(pageNo,pageSize,sortBy,sortDir);
                return postResponse;
            }
            
    }