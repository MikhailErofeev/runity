package com.github.mikhailerofeev.runity.domain.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * @author m-erofeev
 * @since 27.04.14
 */
public class DataUtils {
    private static final Pattern SPACES = Pattern.compile("[ ]{2,}");

    public static List<Map<String, String>> parseCsvToStructuredMap(List<String> params, BufferedReader bufferedReader) throws IOException {
        List<Map<String, String>> sourceEmployeesData = Lists.newArrayList();
        int i = 0;
        String s;
        while ((s = bufferedReader.readLine()) != null) {
            String[] rowString = s.split(";");
            if (params.size() != rowString.length) {
                throw new IllegalStateException("Incorrect params count. " +
                        "Expected " + params.size() + ", but was " + rowString.length + ". Row = " + i);
            }
            Map<String, String> fullRow = Maps.newHashMap();
            for (int paramPosition = 0; paramPosition < rowString.length; paramPosition++) {
                final String paramName = params.get(paramPosition);
                //noinspection UnusedAssignment
                String paramValue = removeQuotesAndTrashSymbols(rowString[paramPosition]);
                fullRow.put(paramName, paramValue);
            }
            sourceEmployeesData.add(fullRow);
            i++;
        }
        return sourceEmployeesData;
    }

    private static String removeQuotesAndTrashSymbols(String str) {
        String paramValue = StringUtils.strip(str, ((char) 65279) + ""); //alternative " point
        paramValue = StringUtils.strip(paramValue, "\"");
        paramValue = StringUtils.strip(paramValue, ((char) 65279) + ""); //alternative " point
        paramValue = SPACES.matcher(paramValue).replaceAll(" ");
        paramValue = StringUtils.strip(paramValue, " ");
        return paramValue;
    }

    public static void filterUnimportant(List<Map<String, String>> sourceEmployeesData, List<String> importantParams) {
        for (Map<String, String> sourceEmployee : sourceEmployeesData) {
            sourceEmployee.keySet().retainAll(importantParams);
        }
    }
}
