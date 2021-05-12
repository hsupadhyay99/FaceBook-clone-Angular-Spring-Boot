package com.orchard.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.orchard.model.Comment;


public interface CommentRepo extends JpaRepository<Comment, Long> {

}
