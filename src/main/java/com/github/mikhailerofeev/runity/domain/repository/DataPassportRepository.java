package com.github.mikhailerofeev.runity.domain.repository;

import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Максим on 27.04.2014.
 */
@Repository
public interface DataPassportRepository extends MongoRepository<DataPassport, String> {
    public Structure findByName(String name);
}
