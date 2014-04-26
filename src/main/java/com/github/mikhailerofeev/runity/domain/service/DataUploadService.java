package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 26.04.14
 */
@Service
public class DataUploadService {
  
  @Autowired
  private EmployeeRepository employeeRepository; 
  
  
  public void rawData(List<String> names, List<List<String>> data, String src, DateTime dataTime){
    for (int i = 0; i < names.size(); i++) {
      final List<String> row = data.get(i);
    }
  }
  
  public void rawData(List<Map<String, String>> data, String src, DateTime dataTime){
    for (Map<String, String> rawEmployee : data) {
      final String name = rawEmployee.get("name");
      rawEmployee.get("ФИО");
      final Employee byName = employeeRepository.findByName(name);
      
    }
  }
}
