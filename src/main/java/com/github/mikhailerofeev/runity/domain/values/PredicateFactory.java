package com.github.mikhailerofeev.runity.domain.values;


import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.google.common.base.Predicate;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Максим on 27.04.2014.
 */
public class PredicateFactory {
    final private static String EQUAL_OPERATOR = "==";
    final private static String NON_EQUAL_OPERATOR = "!=";
    final private static Pattern KEY_PATTERN = Pattern.compile("[а-яА-Яa-zA-Z .]+(!=|==)");
    final private static Pattern VALUE_PATTERN = Pattern.compile("(!=|==)[а-яА-Яa-zA-Z .-]+");

    public static Predicate<Employee> create(String query) {
        for (String operator : Lists.newArrayList(EQUAL_OPERATOR, NON_EQUAL_OPERATOR)) {
            if (StringUtils.contains(query, operator)) {
                return buildEqPredicate(query, operator);
            }
        }
        return new WeakPredicate(query);
    }

    private static Predicate<Employee> buildEqPredicate(String query, String action) {
        Matcher keyMatcher = KEY_PATTERN.matcher(query);
        Matcher valueMatcher = VALUE_PATTERN.matcher(query);
        keyMatcher.find();
        valueMatcher.find();
        String rawKey = keyMatcher.group(0);
        String rawValue = valueMatcher.group(0);
        String key = rawKey.substring(0,rawKey.length()-2);
        key = key.trim();

        rawValue = rawValue.substring(2, rawValue.length());
        String value = rawValue.trim();

        key = getValueFromObject(key);
        value = getValueFromObject(value);

        return new EqPredicate(key, value, action);
    }

    private static String getValueFromObject(String objectWithValue) {
        if(objectWithValue.contains(".")){
            String[] tokens = objectWithValue.split("\\.");
            String value = tokens[1];
            return value;
        }
        return objectWithValue;
    }

}
