package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.PredicateFactory;
import com.github.mikhailerofeev.runity.domain.values.WeakPredicate;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by Максим on 26.04.2014.
 */
@Service
public class SearchService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> search(String query) {
        Predicate<Employee> predicate = PredicateFactory.create(query);
        final List<Employee> employees = employeeRepository.findAll();
        final List<Employee> filteredEmployees = Lists.newArrayList(Collections2.filter(employees, predicate));
        return filteredEmployees;
    }
}
