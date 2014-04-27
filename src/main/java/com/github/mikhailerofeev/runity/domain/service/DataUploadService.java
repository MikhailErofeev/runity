package com.github.mikhailerofeev.runity.domain.service;


import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.DataPassportRepository;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import org.apache.commons.lang3.StringUtils;
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

    @Autowired
    private DataPassportRepository dataPassportRepository;

    public void employeesUpload(List<Map<String, String>> filteredDataSet,
                                String employeeNameFiled, DataPassport dataPassport) {
        final DataPassport savedDataPassport = dataPassportRepository.save(dataPassport);
        final String savedVersionInfoId = savedDataPassport.getId();
        for (Map<String, String> employerData : filteredDataSet) {
            String name = employerData.remove(employeeNameFiled);
            if (StringUtils.isBlank(name)) {
                continue;
            }
            final Employee employee = getOrCreateEmployer(name);
            for (Map.Entry<String, String> param2value : employerData.entrySet()) {
                final ParamValueWithVersionId paramValue = new ParamValueWithVersionId(savedVersionInfoId,
                        param2value.getValue(), true);
                employee.addParam(param2value.getKey(), paramValue);
            }
            employeeRepository.save(employee);
        }
    }


    private Employee getOrCreateEmployer(String name) {
        Employee e = employeeRepository.findByName(name);
        if (e == null) {
            e = new Employee(name);
        }
        return e;
    }
}
