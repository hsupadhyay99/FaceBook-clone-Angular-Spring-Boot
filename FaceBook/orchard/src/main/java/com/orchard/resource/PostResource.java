package com.orchard.resource;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.orchard.model.AppUser;
import com.orchard.model.Post;
import com.orchard.service.AccountService;
import com.orchard.service.CommentService;
import com.orchard.service.PostService;

@RestController
@RequestMapping("/post")
public class PostResource {

	private String postImageName;
	
	@Autowired
	private PostService postService;

	@Autowired
	private AccountService accountService;

	@Autowired
	CommentService commentService;

	@GetMapping("/list")
	public List<Post> getPostList() {
		return postService.postList();
	}

	@GetMapping("/getPostById/{postId}")
	public Post getOnePostById(@PathVariable("postId") Long id) {
		return postService.getPostById(id);
	}

	@GetMapping("/getPostByUsername/{username}")
	public ResponseEntity<?> getPostByUsernamee(@PathVariable("username") String username) {
		AppUser user = getAppUser(username);
		if (user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		try {
			List<Post> posts = postService.findPostByUsername(username);
			return new ResponseEntity<>(posts, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error Occured", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/save")
	public ResponseEntity<?> savePost(@RequestBody HashMap<String, String> request) {
		String username = request.get("username");
		AppUser user = getAppUser(username);
		if (user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		postImageName = RandomStringUtils.randomAlphabetic(10);
		try {
			Post post = postService.savePost(user, request, postImageName);
			System.out.println("Post was saved");
			return new ResponseEntity<>(post, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An Error Occured: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	@DeleteMapping("/delete/{id}")
	public ResponseEntity<?> deletePost(@PathVariable("id") Long id) {
		Post post = postService.getPostById(id);
		if (post == null) {
			return new ResponseEntity<>("Post not found", HttpStatus.NOT_FOUND);
		}
		try {
			postService.deletePost(post);
			return new ResponseEntity<>(post, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("An error occured: " + e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}

	
	@PostMapping("/photo/upload")
	public ResponseEntity<String> fileUpload(@RequestParam("image") MultipartFile multipartFile) {
		try {
			postService.savePostImage(multipartFile, postImageName);
			return new ResponseEntity<>("Picture Saved!", HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Picture was saved", HttpStatus.BAD_REQUEST);
		}
	}
	 

	@PostMapping("/like")
	public ResponseEntity<?> likePost(@RequestBody HashMap<String, String> request) {
		String postId = request.get("postId");
		Post post = postService.getPostById(Long.parseLong(postId));
		if (post == null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		String username = request.get("username");
		AppUser user = getAppUser(username);
		if (user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		try {
			post.setLikes(1);
			user.setLikedPost(post);
			accountService.simpleSaveUser(user);
			return new ResponseEntity<>(post, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Can't like Post! ", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/unLike")
	public ResponseEntity<?> unLikePost(@RequestBody HashMap<String, String> request) {
		String postId = request.get("postId");
		Post post = postService.getPostById(Long.parseLong(postId));
		if (post == null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		String username = request.get("username");
		AppUser user = getAppUser(username);
		if (user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		try {
			post.setLikes(-1);
			user.getLikedPost().remove(post);
			accountService.simpleSaveUser(user);
			return new ResponseEntity<>(post, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Can't unlike Post! ", HttpStatus.BAD_REQUEST);
		}
	}

	@PostMapping("/comment/add")
	public ResponseEntity<?> addComment(@RequestBody HashMap<String, String> request) {
		String postId = request.get("postId");
		Post post = postService.getPostById(Long.parseLong(postId));
		if (post == null) {
			return new ResponseEntity<>("Post Not Found", HttpStatus.NOT_FOUND);
		}
		String username = request.get("username");
		AppUser user = getAppUser(username);
		if (user == null) {
			return new ResponseEntity<>("User Not Found", HttpStatus.NOT_FOUND);
		}
		String content = request.get("content");
		try {
			commentService.saveComment(post, username, content);
			return new ResponseEntity<>(post, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Comment Not Added.", HttpStatus.BAD_REQUEST);
		}
	}

	private AppUser getAppUser(String username) {
		return accountService.findByUsername(username);
	}

}