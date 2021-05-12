package com.orchard.service;

import java.util.HashMap;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.orchard.model.AppUser;
import com.orchard.model.Post;

public interface PostService {

	public Post savePost(AppUser user, HashMap<String, String> request, String postImageName);

	public List<Post> postList();

	public Post getPostById(Long id);

	public List<Post> findPostByUsername(String username);

	public Post deletePost(Post post);
	
	public String savePostImage(MultipartFile multipartFile, String fileName);
}
