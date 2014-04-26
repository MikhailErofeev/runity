package com.github.mikhailerofeev.runity.domain;

import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.service.DataUploadService;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Максим on 26.04.2014.
 */
@Component
public class DataUtil {

    @Autowired
    DataUploadService dataUploadService;

    final public List<String> paramNames = Lists.newArrayList("Район", "Название судебного района", "Номер судебного участка",
            "Мировой судья", "Помощник мирового судьи", "Секретарь судебного заседания", "Секретарь суда",
            "Адрес фактический", "Рабочий телефон", "Код строения ЕАС");

    final public List<String> importantParams = Lists.newArrayList("Район", "Название судебного района", "Номер судебного участка",
            "Мировой судья");

    final public String judgeParamName = "Мировой судья";
    final public String filePath = "src/test/resources/data.csv";
    public List<Map<String, String>> sourceEmployeesData;

    public void uploadTestData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(filePath))));
        sourceEmployeesData = dataUploadService.parseCsvToStructuredMap(paramNames, bufferedReader);
        dataUploadService.filterUnimportant(sourceEmployeesData, importantParams);
        dataUploadService.employeesUpload(sourceEmployeesData, judgeParamName);
    }
}
