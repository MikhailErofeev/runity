package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
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
      for (final List<String> row : data) {
          List<String> namesWithPost = new ArrayList<String>();
          final String region = row.get(0);

          namesWithPost.add(row.get(3));
          namesWithPost.add(row.get(4));
          namesWithPost.add(row.get(5));
          namesWithPost.add(row.get(6));

          for (int i = 0; i < namesWithPost.size(); ++i) {
              final String name = namesWithPost.get(i);
              Employee e = employeeRepository.findByName(name);
              if(e == null){
                  e = new Employee(name);
              }
              //sorry for i + 3, this is an offset to role names
              e.addParam("post", new ParamValueWithVersionId("magic",names.get(i+3),true));
              e.addParam("region", new ParamValueWithVersionId("magic", region,true));

              employeeRepository.save(e);
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
