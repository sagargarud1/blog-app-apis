package com.blogappcode.blog.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserDTO {
	private int id;
	
	@NotEmpty
	@Size(min = 4,message = "size is must be atlist 4 characters")
	private String name;
	
	@Email(message = "email address is not valid")
	private String email;
	
	@NotBlank
	@Size(min = 4,max = 20,message = "password must be min 4 or max 10 charcters")
	private String password;
	
	@NotBlank
	private String about;
}
