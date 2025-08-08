package com.blogappcode.blog.entities;

import java.util.Date;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	private Integer postId;
	
	@Column(name = "posttitle" ,length = 100,nullable = false)
	private String title;
	
	@Column(length = 10000)
	private String content;
	
	
	private String imageName;
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name = "category_id")
	private Category category;
	@ManyToOne
	private User user;
	
//	@OneToMany(mappedBy = "post" , cascade = CascadeType.ALL)
//	private Set<Comment> comments = new HashSet<>();
//	
	
}
