package com.github.mikhailerofeev.runity.domain.repository;

import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Created by Максим on 27.04.2014.
 */
public interface DataPassportRepository extends MongoRepository<DataPassport, String> {
    public Structure findByName(String name);
}
