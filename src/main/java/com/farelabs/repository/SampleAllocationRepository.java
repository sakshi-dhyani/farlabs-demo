package com.farelabs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farelabs.entity.SampleAllocation;

@Repository
public interface SampleAllocationRepository extends CrudRepository<SampleAllocation, Integer>{

	SampleAllocation findBySampleIdAndExecutiveId(int sample, int getExecutive);

	List<SampleAllocation> findByExecutiveId(int executiveId);

	SampleAllocation findBySampleId(int sampleId);

}
