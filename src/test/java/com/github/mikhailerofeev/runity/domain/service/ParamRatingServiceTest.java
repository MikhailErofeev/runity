package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.TestDataUtil;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.server.Application;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Максим on 27.04.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class ParamRatingServiceTest {


    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    TestDataUtil testDataUtil;

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    ParamRatingService paramRatingService;

    List<Employee> filteredEmployees;
    List<Employee> employees;

    @Before
    public void before() throws IOException {
        mongoTemplate.getDb().dropDatabase();
        testDataUtil.uploadTestData();
        employees = employeeRepository.findAll();
    }

    @Test
    public void addRatingToParam() throws Exception {
        Employee employee = employeeRepository.findByName("Дугина Наталья Владимировна");
        final String paramName = "Район";
        employee = paramRatingService.upRatingParam(employee.getId(), paramName, true);
        assertTrue(employee.getParamRating().containsKey(paramName));
        assertEquals((Integer) 1, employee.getParamRating().get(paramName).getLeft());
        assertEquals((Integer) 0, employee.getParamRating().get(paramName).getRight());

        employee = paramRatingService.upRatingParam(employee.getId(), paramName, false);
        assertEquals((Integer) 1, employee.getParamRating().get(paramName).getLeft());
        assertEquals((Integer) 1, employee.getParamRating().get(paramName).getRight());
    }


    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }
}
