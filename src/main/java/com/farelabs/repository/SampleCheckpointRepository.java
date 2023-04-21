package com.farelabs.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.farelabs.entity.SampleCheckpoints;

public interface SampleCheckpointRepository extends CrudRepository<SampleCheckpoints, Integer> {

	SampleCheckpoints findBySampleIdAndUserId(int sampleId, int id);

	SampleCheckpoints findBySampleId(int sampleId);

	List<SampleCheckpoints> findByUserIdAndFinalstatus(int execId, byte b);

}
