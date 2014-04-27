package com.github.mikhailerofeev.runity.domain.repository;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
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

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;


/**
 * @author m-erofeev
 * @since 25.04.14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class EmployeeRepositoryTest {


    @Autowired
    private EmployeeRepository employeeRepository;

    @Autowired
    private StructureRepository structureRepository;

    @Autowired
    private MongoTemplate mongoTemplate;


    @Before
    public void beforeDown() {
        mongoTemplate.getDb().dropDatabase();
    }

    @SuppressWarnings("unchecked")
    @Test
    public void testMongo() {
        employeeRepository.save(new Employee("Alice"));
        employeeRepository.save(new Employee("Bob"));

        final List<Employee> all = employeeRepository.findAll();
        assertEquals(2, all.size());

        final Employee alice = employeeRepository.findByName("Alice");
        assertNotNull(alice);
    }

    @Test
    public void testComplexObject() {
        employeeRepository.save(new Employee("Misha"));
        final Employee man = employeeRepository.findByName("Misha");
        man.addParam("status", new ParamValueWithVersionId("magic", "student", true));
        final Employee saved = employeeRepository.save(man);
        assertEquals("student", saved.getActualParamValue("status"));
        final Employee retrieved = employeeRepository.findOne(man.getId());
        assertEquals("student", retrieved.getActualParamValue("status"));

        retrieved.addParam("status", new ParamValueWithVersionId("magic", "president", true));
        employeeRepository.save(retrieved);
        final Employee president = employeeRepository.findOne(man.getId());
        assertEquals("president", president.getActualParamValue("status"));
    }


    @Test
    public void testComplex2() {
        Employee e = new Employee("Akakiy Akakievich");
        e.addParam("status", new ParamValueWithVersionId("magic", "obesyana chi-chi-chi", true));
        e.addParam("status", new ParamValueWithVersionId("magic", "prosecutor", false));
        e.addParam("car", new ParamValueWithVersionId("magic", "hohohorse", false));
        final Employee save = employeeRepository.save(e);
        final Employee retrieve = employeeRepository.findOne(save.getId());
        assertEquals(save, retrieve);
    }


    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }
}
