package com.github.mikhailerofeev.runity.domain.repository;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
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
  private EmployeeRepository repository;

  @Autowired
  private MongoTemplate mongoTemplate;

  @SuppressWarnings("unchecked")
  @Test
  public void testMongo() {
    repository.save(new Employee("Alice"));
    repository.save(new Employee("Bob"));

    final List<Employee> all = repository.findAll();
    assertEquals(2, all.size());

    final Employee alice = repository.findByName("Alice");
    assertNotNull(alice);
  }

  @Test
  public void testComplexObject() {
    repository.save(new Employee("Misha"));
    final Employee man = repository.findByName("Misha");
    man.addParam("status", new ParamValueWithVersionId("magic", "student", true));
    final Employee saved = repository.save(man);
    assertEquals("student", saved.getActualParamVaue("status"));
    final Employee retrieved = repository.findOne(man.getId());
    assertEquals("student", retrieved.getActualParamVaue("status"));

    retrieved.addParam("status", new ParamValueWithVersionId("magic", "president", true));
    repository.save(retrieved);
    final Employee president = repository.findOne(man.getId());
    assertEquals("president", president.getActualParamVaue("status"));
  }

  @After
  public void tearDown() {
    mongoTemplate.getDb().dropDatabase();
  }
}
