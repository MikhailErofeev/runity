package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.server.Application;
import com.google.common.collect.Lists;
import junit.framework.TestCase;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

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
    DataUploadService dataUploadService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Test
    public void testUpload() throws IOException {
        //File dataCvs = new File("data.csv");
        List<String> names = Lists.newArrayList("Район", "Название судебного района", "Номер судебного участка",
                "Мировой судья", "Помощник мирового судьи", "Секретарь судебного заседания", "Секретарь суда",
                "Адрес фактический", "Рабочий телефон", "Код строения ЕАС");

        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(
                new FileInputStream(
                new File("src/test/resources/data.csv"))));

        List<List<String>> data = new ArrayList<List<String>>();

        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] rowString = s.split(";");
            List<String> rowStringArray = Lists.newArrayList(rowString);
            data.add(rowStringArray);
        }

        dataUploadService.rawData(names, data, "site", DateTime.now());
        Employee testEmployee = employeeRepository.findByName(data.get(2).get(3));
        assertNotNull(testEmployee);
        String post = testEmployee.getActualParamVaue("post");
        assertNull(post);
        String actualPost = names.get(3);
        assertEquals(post, actualPost);
    }

}
