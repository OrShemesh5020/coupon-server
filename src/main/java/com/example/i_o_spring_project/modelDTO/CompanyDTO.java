package com.example.i_o_spring_project.modelDTO;

import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Component
public class CompanyDTO {

	private Integer id;
	private String name;
	private String email;
	private String password;

}
