package com.github.mikhailerofeev.runity.domain;

import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.service.DataUploadService;
import com.github.mikhailerofeev.runity.domain.service.DataUtils;
import com.google.common.collect.Lists;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.List;
import java.util.Map;

/**
 * Created by Максим on 26.04.2014.
 */
@Component
public class TestDataUtil {

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
    public DataPassport dataPassport;

    public void uploadTestData() throws IOException {
        BufferedReader bufferedReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(new File(filePath))));
        sourceEmployeesData = DataUtils.parseCsvToStructuredMap(paramNames, bufferedReader);
        DataUtils.filterUnimportant(sourceEmployeesData, importantParams);
        final String author = "Max Skorohodov";
        final String url = "http://data.gov.spb.ru/datasets/6170/";
        final String text = "This a test data passport made for fun and profit";
        final DateTime date = new DateTime(2014, 4, 15, 0, 0, 0);
        dataPassport = new DataPassport(author, url, text, date);
        dataUploadService.employeesUpload(sourceEmployeesData, judgeParamName, dataPassport);
    }
}
