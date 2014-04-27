package com.github.mikhailerofeev.runity.domain.entities;

import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.Gson;
import org.apache.commons.lang3.tuple.Pair;
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
    private Map<String, Pair<Integer, Integer>> paramRating;

    //for seriazation purposes
    Employee() {

    }

    public Employee(String name) {
        this.name = name;
        param2valueAndVersion = Maps.newHashMap();
        paramRating = Maps.newHashMap();
    }

    public Employee(String name, Map<String, ArrayList<ParamValueWithVersionId>> param2valueAndVersion) {
        this.name = name;
        this.param2valueAndVersion = param2valueAndVersion;
        paramRating = Maps.newHashMap();
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

    public String getActualParamValue(String paramName) {
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Employee)) return false;

        Employee employee = (Employee) o;

        if (id != null ? !id.equals(employee.id) : employee.id != null) return false;
        if (name != null ? !name.equals(employee.name) : employee.name != null) return false;
        if (param2valueAndVersion != null ? !param2valueAndVersion.equals(employee.param2valueAndVersion) : employee.param2valueAndVersion != null)
            return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (param2valueAndVersion != null ? param2valueAndVersion.hashCode() : 0);
        return result;
    }

    public Map<String, Pair<Integer, Integer>> getParamRating() {
        return paramRating;
    }

    public void initParamRating(String param) {
        this.paramRating.put(param, Pair.of(0, 0));
    }

    public void increaseParamRating(String param) {
        alterRating(param, 1);
    }

    public void decreaseParamRating(String param) {
        alterRating(param, -1);
    }

    private void alterRating(String param, int difference) {
        Pair<Integer, Integer> rating = this.paramRating.get(param);
        Pair<Integer, Integer> newRating = (difference > 0) ?
                Pair.of(rating.getKey() + difference, rating.getValue()) : Pair.of(rating.getKey(), rating.getValue() + difference);
        this.paramRating.remove(param);
        this.paramRating.put(param, newRating);
    }
}
