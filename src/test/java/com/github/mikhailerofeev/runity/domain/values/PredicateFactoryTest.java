package com.github.mikhailerofeev.runity.domain.values;

import com.github.mikhailerofeev.runity.domain.DataUtil;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.server.Application;
import com.google.common.base.Predicate;
import com.google.common.collect.Collections2;
import com.google.common.collect.Lists;
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

/**
 * Created by Максим on 27.04.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class PredicateFactoryTest {

    @Autowired
    EmployeeRepository employeeRepository;

    @Autowired
    DataUtil dataUtil;

    @Autowired
    MongoTemplate mongoTemplate;

    List<Employee> filteredEmployees;
    List<Employee> employees;

    @Before
    public void before() throws IOException {
        mongoTemplate.getDb().dropDatabase();
        dataUtil.uploadTestData();
        employees = employeeRepository.findAll();
    }

    @Test
    public void neqPredicateAndWhitespacesTest() {
        Predicate<Employee> neqPredicate = PredicateFactory.create("   employee.Район    !=    Петроградский район  ");
        filteredEmployees = Lists.newArrayList(Collections2.filter(employees, neqPredicate));
        assertEquals(employees.size() - 6, filteredEmployees.size());
    }

    @Test
    public void leftSidePrefixTest() {
        Predicate<Employee> neqPredicate = PredicateFactory.create("  Район    !=    employee.Петроградский район  ");
        filteredEmployees = Lists.newArrayList(Collections2.filter(employees, neqPredicate));
        assertEquals(employees.size() - 6, filteredEmployees.size());
    }

    @Test
    public void bothSidesPrefixTest() {
        Predicate<Employee> neqPredicate = PredicateFactory.create("employee.Район    !=    employee.Петроградский район  ");
        filteredEmployees = Lists.newArrayList(Collections2.filter(employees, neqPredicate));
        assertEquals(employees.size() - 6, filteredEmployees.size());
    }

    @Test
    public void eqPredicateTest() {
        Predicate<Employee> eqPredicate = PredicateFactory.create("employeer.Район==Петроградский район");
        filteredEmployees = Lists.newArrayList(Collections2.filter(employees, eqPredicate));
        assertEquals(6, filteredEmployees.size());
    }

    @Test
    public void weakPredicateTest() {
        Predicate<Employee> predicate = PredicateFactory.create("Петроград");
        List<Employee> filteredEmployees = Lists.newArrayList(Collections2.filter(employees, predicate));
        assertEquals(6, filteredEmployees.size());
    }

    @Test
    public void nameTest() {
        String name = "Дугина Наталья Владимировна";
        Predicate<Employee> predicate = PredicateFactory.create("employee.name=="+name);
        List<Employee> filteredEmployees = Lists.newArrayList(Collections2.filter(employees, predicate));
        assertEquals(1, filteredEmployees.size());
        assertEquals(name, filteredEmployees.get(0).getName());
    }
    @Test
    public void versionIdTest() {
        Employee employee = new Employee("Герострат");
        employee.addParam("aggro", new ParamValueWithVersionId("magic","12",true));
        employeeRepository.save(employee);

        employees = employeeRepository.findAll();

        Predicate<Employee> predicate = PredicateFactory.create("employee.versionId==magic");
        List<Employee> filteredEmployees = Lists.newArrayList(Collections2.filter(employees, predicate));
        assertEquals(1, filteredEmployees.size());
        assertEquals("Герострат", filteredEmployees.get(0).getName());
    }


    @After
    public void tearDown() {
        mongoTemplate.getDb().dropDatabase();
    }
}
