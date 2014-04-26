package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
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
      for (List<String> row : data) {
          List<Map<String, String>> region = new ArrayList<Map<String, String>>();
          for (int i = 0; i < row.size(); ++i) {
            Map<String, String> linkedField = new HashMap<String, String>();
            linkedField.put(names.get(i) ,row.get(i));
            region.add(linkedField);
          }
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
