package com.marosmamrak.consumeapi.repository;

import com.marosmamrak.consumeapi.entity.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByUserId(Long userId);

    @Query("select max(p.id) from Post p")
    Long getMaxId();
}
