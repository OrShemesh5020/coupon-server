package com.example.i_o_spring_project.modelDTO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {

	private Integer id;
	private String firstName;
	private String lastName;
	private String email;
	private String password;

}
