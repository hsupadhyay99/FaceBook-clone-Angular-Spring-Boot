package com.orchard.model;

import java.io.Serializable;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Stream;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

import org.hibernate.annotations.CreationTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Post implements Serializable {

	private static final long serialVersionUID = 34546474787L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(nullable = false, updatable = false)
	private Long id;

	private String name;

	@Column(columnDefinition = "text")
	private String caption;

	private String username;
	private String location;

	private Long userImageId;

	private int likes;

	@CreationTimestamp
	private Date postedDate;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	//@JoinColumn(name = "post_id")
	private List<Comment> commentList;

	public Post() {
	}

	public Post(Long id, String name, String caption, String username, Long userImageId, int likes,
			Date postedDate, List<Comment> commentList) {
		super();
		this.id = id;
		this.name = name;
		this.caption = caption;
		this.username = username;
		this.userImageId = userImageId;
		this.likes = likes;
		this.postedDate = postedDate;
		this.commentList = commentList;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public Long getUserImageId() {
		return userImageId;
	}

	public void setUserImageId(Long userImageId) {
		this.userImageId = userImageId;
	}

	public int getLikes() {
		return likes;
	}

	public void setLikes(int likes) {
		this.likes += likes;
	}

	public Date getPostedDate() {
		return postedDate;
	}

	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public Stream<Comment> getCommentList() {
		if (commentList != null) {
			return commentList.stream().sorted(Comparator.comparing(Comment::getPostedDate));
		}
		return null;
	}

	@JsonIgnore
	public void setComments(Comment comment) {
		if (comment != null) {
			this.commentList.add(comment);
		}
	}

	@Override
	public String toString() {
		return "Post [id=" + id + ", name=" + name + ", caption=" + caption + ", username=" + username + ", location="
				+ location + ", userImageId=" + userImageId + ", likes=" + likes + ", postedDate=" + postedDate
				+ ", commentList=" + commentList + "]";
	}

}
