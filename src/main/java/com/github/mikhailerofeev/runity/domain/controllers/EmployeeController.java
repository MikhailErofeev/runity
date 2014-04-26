package com.github.mikhailerofeev.runity.domain.controllers;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.values.ParamValueWithVersionId;
import com.google.common.collect.Maps;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 26.04.14
 */

@RestController
@RequestMapping(value = "/rest/v1/employee/")
public class EmployeeController {

    @Autowired
    EmployeeRepository employeeRepository;

    @PostConstruct
    public void postConstruct() {
        testFill();
    }

    private void testFill() {
        Employee testEmployee = employeeRepository.findByName("Борис Борисович Гребенщиков");
        if (testEmployee == null) {
            testEmployee = new Employee("Борис Борисович Гребенщиков");
            testEmployee.addParam("Должность", new ParamValueWithVersionId("magic", "Это Бог, от него сияние исходит", true));
            testEmployee.addParam("Статус", new ParamValueWithVersionId("magic", "Музыкант", false));
            testEmployee.addParam("Образование", new ParamValueWithVersionId("magic", "инженер", true));
            testEmployee.addParam("Город", new ParamValueWithVersionId("magic", "Санкт-Петебург", true));
            testEmployee.addParam("ВУЗ", new ParamValueWithVersionId("magic", "ЛГУ матмех", true));
            employeeRepository.save(testEmployee);
        }
        Employee testEmployee2 = employeeRepository.findByName("Понтий Пилат");
        if (testEmployee2 == null) {
            testEmployee2 = new Employee("Понтий Пилат");
            testEmployee2.addParam("Должность", new ParamValueWithVersionId("magic", "Прокурор", true));
            testEmployee2.addParam("Структура", new ParamValueWithVersionId("magic", "Прокуратура", true));
            testEmployee2.addParam("Город", new ParamValueWithVersionId("magic", "Иеарусалим", true));
            testEmployee2.addParam("photo",
                    new ParamValueWithVersionId("magic", "http://media.civilization5.ru/(Illustrations)/Forums/Strategium/04/Prokurator/TheEnd.jpg", true));
            employeeRepository.save(testEmployee2);
        }
        Employee testEmployee3 = employeeRepository.findByName("Иван Ведров");
        if (testEmployee3 == null) {
            testEmployee3 = new Employee("Робокоп");
            testEmployee3.addParam("Должность", new ParamValueWithVersionId("magic", "Участковый", true));
            testEmployee3.addParam("Структура", new ParamValueWithVersionId("magic", "Отдел полиции (милиции) № 43", true));
            testEmployee3.addParam("Район", new ParamValueWithVersionId("magic", "Петроградский", true));
            testEmployee3.addParam("Город", new ParamValueWithVersionId("magic", "Санкт-Петербург", true));
            testEmployee3.addParam("photo", new ParamValueWithVersionId("magic", "http://images.modhoster.de/system/files/966/large/RoboCop%20150x150.jpg", true));
            employeeRepository.save(testEmployee3);
        }
        Employee testEmployee4 = employeeRepository.findByName("Аркадий Шварц");
        if (testEmployee4 == null) {
            testEmployee4 = new Employee("Аркадий Шварц");
            testEmployee4.addParam("Должность", new ParamValueWithVersionId("magic", "Участковый", true));
            testEmployee4.addParam("Структура", new ParamValueWithVersionId("magic", "Отдел полиции (милиции) № 43", true));
            testEmployee4.addParam("Район", new ParamValueWithVersionId("magic", "Петроградский", true));
            testEmployee4.addParam("Город", new ParamValueWithVersionId("magic", "Санкт-Петербург", true));
            testEmployee4.addParam("photo", new ParamValueWithVersionId("magic", "http://i2.guns.ru/forums/icons/attachments/15_001326_000016_11238.jpg", true));
            employeeRepository.save(testEmployee4);
        }

        Employee testEmployee5 = employeeRepository.findByName("Сергей Безруков");
        if (testEmployee5 == null) {
            testEmployee5 = new Employee("Сергей Безруков");
            testEmployee5.addParam("Должность", new ParamValueWithVersionId("magic", "Участковый", true));
            testEmployee5.addParam("Структура", new ParamValueWithVersionId("magic", "Отдел полиции (милиции) № 43", true));
            testEmployee5.addParam("Район", new ParamValueWithVersionId("magic", "Петроградский", true));
            testEmployee5.addParam("Город", new ParamValueWithVersionId("magic", "Санкт-Петербург", true));
            testEmployee5.addParam("photo", new ParamValueWithVersionId("magic", "http://stat20.privet.ru/lr/0c0927f2f7571c9f76256c892e09c558", true));
            employeeRepository.save(testEmployee5);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Employee get(@PathVariable("id") String id) {
        return employeeRepository.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> get() {

        Map<String, String> ids2name = Maps.newHashMap();
        for (Employee employee : employeeRepository.findAll()) {
            ids2name.put(employee.getId(), employee.getName());
        }
        return ids2name;

    }
}