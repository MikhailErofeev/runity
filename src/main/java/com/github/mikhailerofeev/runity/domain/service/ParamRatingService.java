package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.repository.StructureRepository;
import org.apache.commons.lang3.tuple.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by Максим on 27.04.2014.
 */
@Service
public class ParamRatingService {
    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    StructureRepository structureRepository;


    public Employee upRatingParam(String id, String param, boolean like) throws Exception {
        Employee employee = employeeRepository.findOne(id);
        if(employee == null) throw new NullPointerException(id);

        Map<String, Pair<Integer, Integer>> paramsRating = employee.getParamRating();

        if(!paramsRating.containsKey(param)) {
            employee.addParamRating(param);
        }

        if(like){
            employee.increaseParamRating(param);
        } else{
            employee.decreaseParamRating(param);
        }
        return employeeRepository.save(employee);
    }

    public Structure upRatingParamStructure(String id, String param, boolean like) throws Exception {
        Structure structure = structureRepository.findOne(id);
        if(structure == null) throw new NullPointerException(id);

        Map<String, Pair<Integer, Integer>> paramsRating = structure.getParamRating();

        if(!paramsRating.containsKey(param)) {
            structure.addParamRating(param);
            return structureRepository.save(structure);
        }

        if(like){
            structure.increaseParamRating(param);
        } else{
            structure.decreaseParamRating(param);
        }
        return structureRepository.save(structure);
    }

}
