package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.repository.StructureRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author m-erofeev
 * @since 26.04.14
 */
@Service
public class StructureService {

    @Autowired
    StructureRepository structureRepository;

    public Structure save(Structure s) {
        return structureRepository.save(s);
    }

}
