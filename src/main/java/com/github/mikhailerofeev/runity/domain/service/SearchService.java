package com.github.mikhailerofeev.runity.domain.service;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Максим on 26.04.2014.
 */
@Service
public class SearchService {

    @Autowired
    EmployeeRepository employeeRepository;

    public List<Employee> search(String query) {
        List<Employee> foundEmployees = new ArrayList<Employee>();
        final List<Employee> employees = employeeRepository.findAll();
        for (final Employee employee : employees) {
            boolean isNameContainsQuery = StringUtils.contains(employee.getName(), query);
            Structure structure = employee.getStructure();
            boolean isStructureContainsQuery = structure != null && StringUtils.contains(structure.getName(), query);
            boolean hasAnyMatches = isNameContainsQuery || isStructureContainsQuery || checkEmployeeForQueue(query, employee);
            if (hasAnyMatches) {
                foundEmployees.add(employee);
            }
        }
        return foundEmployees;
    }

    private boolean checkEmployeeForQueue(String query, Employee employee) {
        Map<String, ArrayList<ParamValueWithVersionId>> employeeFields = employee.getParam2valueAndVersion();
        for (Map.Entry<String, ArrayList<ParamValueWithVersionId>> paramName2Versions : employeeFields.entrySet()) {
            if (paramName2Versions.getValue() != null) {
                for (ParamValueWithVersionId versionedParam : paramName2Versions.getValue()) {
                    if (StringUtils.contains(versionedParam.getValue(), query)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }
}
