package com.itservices_backend.repository;



import com.itservices_backend.model.BlogPost;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<BlogPost, Long> {
}

