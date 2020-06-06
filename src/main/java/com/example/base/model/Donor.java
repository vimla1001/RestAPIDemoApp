package com.example.base.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter 
@Setter 
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"id", "lastName", "firstName"})
public class Donor {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	@ApiModelProperty(
			value = "First name of donor",
			name = "First Name"
			)
	private String firstName;
	@Size(min=2, message="LastName should have atleast 2 characters")
	private String lastName;
	
}
