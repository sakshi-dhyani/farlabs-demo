package com.farelabs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farelabs.entity.SampleStatus;

@Repository
public interface SampleStatusRepository extends CrudRepository<SampleStatus, Integer>{


	SampleStatus findBySampleId(int sample);

}
