package com.github.mikhailerofeev.runity.domain.controllers;

import com.beust.jcommander.internal.Maps;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 26.04.14
 */

@RestController
public class EmployeeController {

  @Autowired
  EmployeeRepository employeeRepository;

  @PostConstruct
  public void testFill() {
    Employee testEmployee = employeeRepository.findByName("Akakiy Akakievich");
    if (testEmployee == null) {
      testEmployee = new Employee("Akakiy Akakievich");
      testEmployee.addParam("status", new ParamValueWithVersionId("magic", "obesyana chi-chi-chi", true));
      testEmployee.addParam("status", new ParamValueWithVersionId("magic", "prosecutor", false));
      testEmployee.addParam("car", new ParamValueWithVersionId("magic", "hohohorse", false));
      testEmployee = employeeRepository.save(testEmployee);
    }
    System.out.println(testEmployee.getId());

  }

  @RequestMapping(value = "/rest/v1/employee/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Employee get(@PathVariable("id") String id) {
    return employeeRepository.findOne(id);
  }

  @RequestMapping(value = "/rest/v1/employee", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, String> get() {

    Map<String, String> ids2name = Maps.newHashMap();
    for (Employee employee : employeeRepository.findAll()) {
      ids2name.put(employee.getId(), employee.getName());
    }
    return ids2name;

  }
}
