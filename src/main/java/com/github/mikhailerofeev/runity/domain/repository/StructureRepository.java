package com.github.mikhailerofeev.runity.domain.repository;

import com.github.mikhailerofeev.runity.domain.entities.Structure;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author m-erofeev
 * @since 25.04.14
 */
public interface StructureRepository extends MongoRepository<Structure, String> {

  public Structure findByName(String name);

}
