package com.orchard.service;

import com.orchard.model.Post;

public interface CommentService {

	public void saveComment(Post post, String username, String content);

}
