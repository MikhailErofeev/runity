package com.github.mikhailerofeev.runity.domain.values;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Map;


/**
 * Created by Максим on 27.04.2014.
 */
public class EqPredicate implements Predicate<Employee> {
    private String key;
    private String value;
    private String operator;

    final private static String EQUAL_ACTION = "==";
    final private static String NON_EQUAL_ACTION = "!=";

    public EqPredicate(String key, String value, String operator) {
        this.key = key;
        this.value = value;
        this.operator = operator;
    }

    @Override
    public boolean apply(Employee employee) {
        Map<String, ArrayList<ParamValueWithVersionId>> employeeFields = employee.getParam2valueAndVersion();

        if (StringUtils.equals(operator, EQUAL_ACTION)) {
            if (StringUtils.equals(this.key, "name")) {
                return StringUtils.equals(employee.getName(), value);
            }

            if (StringUtils.equals(this.key, "versionId")) {
                return checkForVersionIDEquals(employeeFields);
            }
            if(employeeFields.get(key) == null){
                return false;
            }
            for (ParamValueWithVersionId paramValueWithVersionId : employeeFields.get(key)) {
                if (StringUtils.equals(paramValueWithVersionId.getValue(), this.value))
                    return true;
            }
            return false;
        } else if (StringUtils.equals(operator, NON_EQUAL_ACTION)) {
            if (StringUtils.equals(this.key, "name")) {
                return !StringUtils.equals(employee.getName(), value);
            }

            if (StringUtils.equals(this.key, "versionId")) {
                return !checkForVersionIDEquals(employeeFields);
            }
            if(employeeFields.get(key) == null){
                return false;
            }

            for (ParamValueWithVersionId paramValueWithVersionId : employeeFields.get(key)) {
                if (StringUtils.equals(paramValueWithVersionId.getValue(), this.value))
                    return false;
            }
            return true;
        }
        return false;
    }

    public boolean checkForVersionIDEquals(Map<String, ArrayList<ParamValueWithVersionId>> employeeFields) {
        for (Map.Entry<String, ArrayList<ParamValueWithVersionId>> fieldMap : employeeFields.entrySet()) {
            for (ParamValueWithVersionId paramValueWithVersionId : fieldMap.getValue()) {
                if (StringUtils.equals(paramValueWithVersionId.getVersionId(), value))
                    return true;
            }
        }
        return false;
    }
}
