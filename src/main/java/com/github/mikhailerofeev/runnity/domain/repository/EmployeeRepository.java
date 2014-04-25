package com.github.mikhailerofeev.runnity.domain.repository;

import com.github.mikhailerofeev.runnity.domain.entities.Employee;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author m-erofeev
 * @since 25.04.14
 */
public interface EmployeeRepository extends MongoRepository<Employee, String> {

  public Employee findByName(String name);

}
