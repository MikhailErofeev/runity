package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.DataUtil;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.server.Application;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;

/**
 * Created by Максим on 26.04.2014.
 */
//todo annotation
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class DataUploadServiceTest extends TestCase {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DataUploadService dataUploadService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataUtil dataUtil;

    @org.junit.Before
    public void beforeDown() throws IOException {
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void testUpload() throws IOException {
        dataUtil.uploadTestData();
        final Employee employee = employeeRepository.findByName("Колесова Инна Максимовна");
        assertEquals("Красногвардейский район", employee.getActualParamVaue("Район"));
        assertNull(employee.getActualParamVaue("Код строения ЕАС"));
        assertNull(employeeRepository.findByName(""));
        assertNotNull(employeeRepository.findByName("Алексеева Ольга Юрьевна"));
    }

    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }

}
