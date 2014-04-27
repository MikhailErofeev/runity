package com.github.mikhailerofeev.runity.domain.values;


import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.google.common.base.Predicate;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Максим on 27.04.2014.
 */
public class WeakPredicate implements Predicate<Employee> {

    private String srcQuery;

    public WeakPredicate(String srcQuery) {
        this.srcQuery = srcQuery;
    }

    @Override
    public boolean apply(Employee employee) {
        boolean isNameContainsQuery = StringUtils.contains(employee.getName().toLowerCase(), srcQuery.toLowerCase());
        boolean hasAnyMatches = isNameContainsQuery || checkEmployeeForQueue(srcQuery, employee);
        return hasAnyMatches;
    }


    private boolean checkEmployeeForQueue(String query, Employee employee) {
        Map<String, ArrayList<ParamValueWithVersionId>> employeeFields = employee.getParam2valueAndVersion();
        for (Map.Entry<String, ArrayList<ParamValueWithVersionId>> paramName2Versions : employeeFields.entrySet()) {
            if (paramName2Versions.getValue() != null) {
                for (ParamValueWithVersionId versionedParam : paramName2Versions.getValue()) {
                    if (StringUtils.contains(versionedParam.getValue().toLowerCase(), query.toLowerCase())) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
