package com.github.mikhailerofeev.runity.domain.controllers;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author m-erofeev
 * @since 26.04.14
 */
@RestController
@RequestMapping(value = "/rest/v1/search")
public class SearchController {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    SearchService searchService;

    @RequestMapping(value = "/{query}")
    public List<Employee> result(@PathVariable("query") String query) {
        return searchService.search(query);
    }
}
