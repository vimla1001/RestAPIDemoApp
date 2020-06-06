package com.example.base.service;

import org.springframework.data.repository.CrudRepository;

import com.example.base.model.Donor;

public interface DonorService extends CrudRepository<Donor, Integer> {

}
