package com.github.mikhailerofeev.runity.domain.service;

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
import java.util.List;
import java.util.Map;

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


    @org.junit.Before
    public void beforeDown() {
        mongoTemplate.getDb().dropDatabase();
    }

    @Test
    public void testUpload() throws IOException {
        //File dataCvs = new File("data.csv");
        List<String> paramNames = Lists.newArrayList("Район", "Название судебного района", "Номер судебного участка",
                "Мировой судья", "Помощник мирового судьи", "Секретарь судебного заседания", "Секретарь суда",
                "Адрес фактический", "Рабочий телефон", "Код строения ЕАС");
        List<String> importantParams = Lists.newArrayList("Район", "Название судебного района", "Номер судебного участка",
                "Мировой судья");
        String judgeParamName = "Мировой судья";

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File("src/test/resources/data.csv"))));
        List<Map<String, String>> sourceEmployeesData = dataUploadService.parseCsvToStructuredMap(paramNames, bufferedReader);
        filterUnimportant(sourceEmployeesData, importantParams);
        assertEquals("Колесова Инна Максимовна", sourceEmployeesData.get(0).get(judgeParamName));
        dataUploadService.employeesUpload(sourceEmployeesData, judgeParamName);
        final Employee employee = employeeRepository.findByName("Колесова Инна Максимовна");
        assertEquals("Красногвардейский район", employee.getActualParamVaue("Район"));
        assertNull(employee.getActualParamVaue("Код строения ЕАС"));
        assertNull(employeeRepository.findByName(""));
        assertNotNull(employeeRepository.findByName("Алексеева Ольга Юрьевна"));
    }

    private void filterUnimportant(List<Map<String, String>> sourceEmployeesData, List<String> importantParams) {
        for (Map<String, String> sourceEmployee : sourceEmployeesData) {
            sourceEmployee.keySet().retainAll(importantParams);
        }
    }


    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }

}
