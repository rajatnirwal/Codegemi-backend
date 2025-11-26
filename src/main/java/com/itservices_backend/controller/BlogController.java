package com.itservices_backend.controller;

import com.itservices_backend.model.BlogPost;
import com.itservices_backend.service.BlogService;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/blogs")
public class BlogController {

    private final BlogService blogService;
    
    public BlogController(BlogService blogService) {
        this.blogService = blogService;
    }
    

    @PostMapping("/create")
    public ResponseEntity<BlogPost> create(@RequestBody BlogPost blogPost) {
        return ResponseEntity.ok(blogService.createPost(blogPost));
    }

    @GetMapping("/all")
    public ResponseEntity<List<BlogPost>> getAll() {
        return ResponseEntity.ok(blogService.getAllPosts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<BlogPost> getById(@PathVariable Long id) {
        return blogService.getPostById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        blogService.deletePost(id);
        return ResponseEntity.ok("Blog deleted");
    }
}
