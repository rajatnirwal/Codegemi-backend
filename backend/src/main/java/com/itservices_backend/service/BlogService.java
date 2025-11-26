package com.itservices_backend.service;

import com.itservices_backend.model.BlogPost;
import com.itservices_backend.repository.BlogRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service

public class BlogService {
	
    private final BlogRepository blogRepository;

    @Autowired
    public BlogService(BlogRepository blogRepository) {
		super();
		this.blogRepository = blogRepository;
	}

	public BlogPost createPost(BlogPost post) {
        post.setCreatedAt(LocalDateTime.now());
        return blogRepository.save(post);
    }

    public List<BlogPost> getAllPosts() {
        return blogRepository.findAll();
    }

    public Optional<BlogPost> getPostById(Long id) {
        return blogRepository.findById(id);
    }

    public void deletePost(Long id) {
        blogRepository.deleteById(id);
    }
}
