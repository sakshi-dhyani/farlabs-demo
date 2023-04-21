package com.farelabs.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.farelabs.entity.SampleCollection;

@Repository
public interface SampleCollectionRepository extends CrudRepository<SampleCollection, Integer>{

}
