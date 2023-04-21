package com.farelabs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farelabs.entity.SampleDetails;

@Repository
public interface SampleDetailsRepository extends CrudRepository<SampleDetails, Integer>{

	SampleDetails findByCompanyMobile(String companyMobile);
	
	List<SampleDetails>findAll();
	
	SampleDetails findById(int id);

	SampleDetails findBySerialNumber(int sNumbers);


}
