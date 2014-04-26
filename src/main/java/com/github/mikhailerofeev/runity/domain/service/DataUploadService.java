package com.github.mikhailerofeev.runity.domain.service;


import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.DataPassportRepository;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

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

    public static final Pattern SPACES = Pattern.compile("[ ]{2,}");


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

    public List<Map<String, String>> parseCsvToStructuredMap(List<String> params, BufferedReader bufferedReader) throws IOException {
        List<Map<String, String>> sourceEmployeesData = Lists.newArrayList();
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] rowString = s.split(";");
            Map<String, String> fullRow = Maps.newHashMap();
            for (int paramPosition = 0; paramPosition < rowString.length; paramPosition++) {
                final String paramName = params.get(paramPosition);
                //noinspection UnusedAssignment
                String paramValue = removeQuotesAndTrashSymbols(rowString[paramPosition]);
                fullRow.put(paramName, paramValue);
            }
            sourceEmployeesData.add(fullRow);
        }
        return sourceEmployeesData;
    }

    private String removeQuotesAndTrashSymbols(String str) {
        String paramValue = StringUtils.strip(str, ((char) 65279) + ""); //alternative " point
        paramValue = StringUtils.strip(paramValue, "\"");
        paramValue = StringUtils.strip(paramValue, ((char) 65279) + ""); //alternative " point
        paramValue = SPACES.matcher(paramValue).replaceAll(" ");
        paramValue = StringUtils.strip(paramValue, " ");
        return paramValue;
    }

    public void filterUnimportant(List<Map<String, String>> sourceEmployeesData, List<String> importantParams) {
        for (Map<String, String> sourceEmployee : sourceEmployeesData) {
            sourceEmployee.keySet().retainAll(importantParams);
        }
    }
}
