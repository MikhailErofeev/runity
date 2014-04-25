package com.github.mikhailerofeev.runity.domain.entities;

import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import com.google.common.collect.Lists;
import com.google.gson.Gson;
import org.springframework.data.annotation.Id;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 25.04.14
 */
public class Employee {

  @Id
  private String id;
  private String name;

  //detalisation for mongo purpose
  private Map<String, ArrayList<ParamValueWithVersionId>> param2valueAndVersion;

  //for seriazation purposes
  Employee() {

  }

  public Employee(String name) {
    this.name = name;
    param2valueAndVersion = Collections.EMPTY_MAP;
  }

  public String getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Map<String, ArrayList<ParamValueWithVersionId>> getParam2valueAndVersion() {
    return Collections.unmodifiableMap(param2valueAndVersion);
  }

  @Override
  public String toString() {
    return new Gson().toJson(this);
  }

  public void addParam(String names, ParamValueWithVersionId newValue) {
    ArrayList<ParamValueWithVersionId> values = param2valueAndVersion.get(names);
    if (values == null) {
      values = Lists.newArrayList();
      param2valueAndVersion.put(names, values);
    }
    if (newValue.isActual()) {
      for (ParamValueWithVersionId value : values) {
        value.setActual(false);
      }
    }
    values.add(newValue);
  }

  public String getActualParamVaue(String paramName) {
    final List<ParamValueWithVersionId> values = param2valueAndVersion.get(paramName);
    if (values == null) {
      return null;
    }
    for (ParamValueWithVersionId paramValueWithVersionId : values) {
      if (paramValueWithVersionId.isActual()) {
        return paramValueWithVersionId.getValue();
      }
    }
    return null;

  }
}
