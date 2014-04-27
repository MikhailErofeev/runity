package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
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

}
