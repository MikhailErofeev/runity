package com.github.mikhailerofeev.runity.domain.controllers;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 26.04.14
 */

@RestController
@RequestMapping(value = "/rest/v1/")
public class DataController {

  @Autowired
  EmployeeRepository employeeRepository;

  @PostConstruct
  public void postConstruct() {
    testFill();
  }

  private void testFill() {
    Employee testEmployee = employeeRepository.findByName("Борис Борисович Гребенщиков");
    if (testEmployee == null) {
      testEmployee = new Employee("Борис Борисович Гребенщиков");
      testEmployee.addParam("post", new ParamValueWithVersionId("magic", "Это Бог, от него сияние исходит", true));
      testEmployee.addParam("status", new ParamValueWithVersionId("magic", "Музыкант", false));
      testEmployee.addParam("education", new ParamValueWithVersionId("magic", "инженер", true));
      testEmployee.addParam("high school", new ParamValueWithVersionId("magic", "ЛГУ матмех", true));
      testEmployee = employeeRepository.save(testEmployee);
    }
    System.out.println(testEmployee.getId());
  }

  @RequestMapping(value = "/employee/{id}", method = RequestMethod.GET)
  @ResponseBody
  public Employee get(@PathVariable("id") String id) {
    return employeeRepository.findOne(id);
  }

  @RequestMapping(value = "/employee", method = RequestMethod.GET)
  @ResponseBody
  public Map<String, String> get() {

    Map<String, String> ids2name = Maps.newHashMap();
    for (Employee employee : employeeRepository.findAll()) {
      ids2name.put(employee.getId(), employee.getName());
    }
    return ids2name;

  }
}
