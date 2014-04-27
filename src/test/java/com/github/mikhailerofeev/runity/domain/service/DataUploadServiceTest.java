package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.TestDataUtil;
import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.github.mikhailerofeev.runity.domain.repository.DataPassportRepository;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.repository.StructureRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by Максим on 26.04.2014.
 */
//todo annotation
@SuppressWarnings("SpringJavaAutowiredMembersInspection")
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class DataUploadServiceTest {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    DataUploadService dataUploadService;

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataPassportRepository dataPassportRepository;

    @Autowired
    TestDataUtil testDataUtil;

    @Autowired
    StructureRepository structureRepository;

    @org.junit.Before
    public void beforeDown() throws IOException {
        mongoTemplate.getDb().dropDatabase();
        testDataUtil.uploadTestData();
        testDataUtil.uploadTestStructureData();
    }

    @Test
    public void testUpload() throws IOException {
        final Employee employee = employeeRepository.findByName("Колесова Инна Максимовна");
        assertEquals("Красногвардейский район", employee.getActualParamValue("Район"));
        assertNull(employee.getActualParamValue("Код строения ЕАС"));
        assertNull(employeeRepository.findByName(""));
        assertNotNull(employeeRepository.findByName("Алексеева Ольга Юрьевна"));
    }

    @Test
    public void testStructuresUpload() throws IOException {
        final Structure structure = structureRepository.findByName("152");
        assertEquals("Петроградский район", structure.getActualParamVaue("Район"));
        //assertNull(structure.getActualParamValue("Код строения ЕАС"));
        assertNull(employeeRepository.findByName(""));
        assertNotNull(structureRepository.findByName("152"));
    }

    @Test
    public void testPassport() {
        final Employee employee = employeeRepository.findByName("Колесова Инна Максимовна");
        Map<String, ArrayList<ParamValueWithVersionId>> param2valueAndVersion = employee.getParam2valueAndVersion();
        final List<ParamValueWithVersionId> values;
        values = param2valueAndVersion.get("Район");
        assertNotNull(values.get(0).getVersionId());
        DataPassport passport = dataPassportRepository.findOne(values.get(0).getVersionId());
        assertEquals(testDataUtil.dataPassport.getAuthor(), passport.getAuthor());
    }


    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }

}
