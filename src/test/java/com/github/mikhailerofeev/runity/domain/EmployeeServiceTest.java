package com.github.mikhailerofeev.runity.domain;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.github.mikhailerofeev.runity.domain.service.EmployeeService;
import com.github.mikhailerofeev.runity.server.Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * @author m-erofeev
 * @since 26.04.14
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@ActiveProfiles("test")
@DirtiesContext
public class EmployeeServiceTest {

  @Autowired
  EmployeeService employeeService;

  @Test
  public void testSave() throws Exception {
    Employee e = new Employee("Putin");
    e.setStructure(new Structure("Russia"));
    final Employee putinSaved = employeeService.save(e);
    assertNotNull(putinSaved.getStructure().getId());
    assertEquals("Russia", putinSaved.getStructure().getName());
  }
}
