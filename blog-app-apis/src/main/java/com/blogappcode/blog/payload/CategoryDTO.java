package com.blogappcode.blog.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDTO {

	
	private int categoryId;
	
	@NotBlank
	@Size(min = 4,message = "message size must be atleast 4 ")
	private String categoryTitle;
	@NotBlank
	@Size(min=4 ,max = 10000,message = "message should atleast 4 and max 10000 characters ")
	private String categoryDescription;
}
