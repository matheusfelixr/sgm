package com.matheusfelixr.sgm.repository;

import com.matheusfelixr.sgm.model.domain.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {

}

