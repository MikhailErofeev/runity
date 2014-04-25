package com.github.mikhailerofeev.runity.domain.entities;

import com.github.mikhailerofeev.runity.domain.values.ParamValue;
import com.google.gson.Gson;
import org.springframework.data.annotation.Id;

import java.util.Collections;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 25.04.14
 */
public class Employee {

  @Id
  private String id;
  private String name;

  private Map<String, ParamValue> param2valueAndVersion;

  public Employee(String name, Map<String, ParamValue> param2valueAndVersion) {
    this.name = name;
    this.param2valueAndVersion = param2valueAndVersion;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Map<String, ParamValue> getParam2valueAndVersion() {
    return Collections.unmodifiableMap(param2valueAndVersion);
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

}
