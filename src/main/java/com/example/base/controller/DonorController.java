package com.example.base.controller;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.example.base.exception.DonorNotFoundException;
import com.example.base.model.Donor;
import com.example.base.service.DonorService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/donor")
@Api(produces = "application/json", value = "Operations pertaining to manage blood donors in the application")
public class DonorController {

	@Autowired
	DonorService donorService;
	
	@PostMapping
	@ApiOperation(value = "Create a new Donor", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully created a new Donor"),
			@ApiResponse(code = 401, message = "Not Authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 200, message = "Application failed to process request")
	})
	private ResponseEntity<Donor> create(@Valid @RequestBody Donor donor) {
		Donor savedDonor = donorService.save(donor);
		
		//return new ResponseEntity<Donor>(savedDonor, HttpStatus.OK);
		
		/**
		 * Return correct HTTP status code and location 
		 * 
		 */
		URI location = ServletUriComponentsBuilder
				.fromCurrentRequest()
				.path("/{id}")
				.buildAndExpand(savedDonor.getId()).toUri();
		
		return ResponseEntity.created(location).build();
		
	}
	
	@PutMapping
	@ApiOperation(value = "Update Donor information", response = ResponseEntity.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully updated a Donor"),
			@ApiResponse(code = 401, message = "Not Authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 200, message = "Application failed to process request")
	})
	private ResponseEntity<Donor> update(@RequestBody Donor donor) {
		Donor updatedDonor = donorService.save(donor);
		return new ResponseEntity<Donor>(updatedDonor, HttpStatus.OK);
	}
	
	@GetMapping
	@ApiOperation(value = "View all Donors", response = Iterable.class)
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully retrieved all Donor"),
			@ApiResponse(code = 401, message = "Not Authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 200, message = "Application failed to process request")
	})
	private Iterable<Donor> view() {
		return donorService.findAll();
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "Delete specific Donor with supplied donor id ")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted a new Donor"),
			@ApiResponse(code = 401, message = "Not Authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 200, message = "Application failed to process request")
	})
	private void delete(@PathVariable Integer id) {
		donorService.findById(id).ifPresentOrElse(
				donor -> donorService.deleteById(donor.getId()),
				() -> {throw new DonorNotFoundException("id "+id+" not found");});
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value="Get Donor by id", notes = "This will get Donor by id")
	@ApiResponses(value = {
			@ApiResponse(code = 200, message = "Successfully deleted a new Donor"),
			@ApiResponse(code = 401, message = "Not Authorized to view the resource"),
			@ApiResponse(code = 403, message = "Accessing the resource is forbidden"),
			@ApiResponse(code = 404, message = "Resource not found"),
			@ApiResponse(code = 200, message = "Application failed to process request")
	})
	private ResponseEntity<Donor> getDonorById(@ApiParam(name="ID", type = "INT", required = true)@PathVariable Integer id) {
		Optional<Donor> donor = donorService.findById(id);
		//return new ResponseEntity<Donor>(donor.orElse(new Donor()), HttpStatus.OK);
		
		if(donor.isPresent())
			return new ResponseEntity<Donor>(donor.get(), HttpStatus.OK);
		else
			throw new DonorNotFoundException("id not found : "+id);
	}
}
