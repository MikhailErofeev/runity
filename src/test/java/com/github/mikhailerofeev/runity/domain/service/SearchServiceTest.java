package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.DataUtil;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.server.Application;
import org.junit.After;
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

import static org.junit.Assert.*;

/**
 * Created by Максим on 26.04.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class SearchServiceTest {
    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DataUploadService dataUploadService;

    @Autowired
    SearchService searchService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataUtil dataUtil;

    @org.junit.Before
    public void before() throws IOException {
        mongoTemplate.getDb().dropDatabase();
        dataUtil.uploadTestData();
    }

    @Test
    public void testSearch() throws IOException {
        List<Employee> employees = searchService.search("Ирина");
        assertNotNull(employees);
        assertEquals(13, employees.size());
        Employee employee = employees.get(0);
        assertTrue(employee.getName().contains("Ирина"));
        assertTrue(searchService.search("Мафусаил").isEmpty());
    }

    @Test
    public void testSearchByRegion() {
        List<Employee> all = searchService.search("Петроград");
        assertEquals(6, all.size());
    }

    @Test
    public void testEmptyQuery() {
        List<Employee> all = searchService.search("");
        assertEquals(198, all.size());
        assertEquals(all.size(), employeeRepository.findAll().size());
    }

    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }
}
