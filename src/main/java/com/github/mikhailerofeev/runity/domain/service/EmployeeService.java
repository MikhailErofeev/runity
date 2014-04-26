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
public class EmployeeService {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    StructureRepository structureRepository;

    public Employee save(Employee e) {
        if (e.getStructure() != null && e.getStructure().getId() == null) {
            final Structure founded = structureRepository.findByName(e.getStructure().getName());
            if (founded == null) {
                e.setStructure(structureRepository.save(e.getStructure()));
            } else {
                e.setStructure(founded);
            }
        }
        return employeeRepository.save(e);
    }

}
