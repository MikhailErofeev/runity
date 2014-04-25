package com.github.mikhailerofeev.runity.domain.repository;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
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

import java.util.Collections;
import java.util.List;
import java.util.Map;

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
    repository.save(new Employee("Alice", (Map) Collections.emptyMap()));
    repository.save(new Employee("Bob", (Map) Collections.emptyMap()));

    final List<Employee> all = repository.findAll();
    assertEquals(2, all.size());

    final Employee alice = repository.findByName("Alice");
    assertNotNull(alice);
  }
  
  @After
  public void tearDown(){
    mongoTemplate.getDb().dropDatabase();
  }
}
