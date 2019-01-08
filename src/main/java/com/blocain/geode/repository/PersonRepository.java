package com.blocain.geode.repository;

import com.blocain.geode.entity.Person;
import org.springframework.data.gemfire.repository.GemfireRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PersonRepository extends GemfireRepository<Person, Long> {
}
