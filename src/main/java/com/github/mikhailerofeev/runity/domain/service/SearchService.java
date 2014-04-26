package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Максим on 26.04.2014.
 */
@Service
public class SearchService {

    @Autowired
    EmployeeRepository employeeRepository;

    public Employee[] search(String key){



        return null;
    }

}
