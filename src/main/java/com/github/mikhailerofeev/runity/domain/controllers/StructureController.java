package com.github.mikhailerofeev.runity.domain.controllers;

import com.github.mikhailerofeev.runity.domain.entities.Employee;
import com.github.mikhailerofeev.runity.domain.entities.Structure;
import com.github.mikhailerofeev.runity.domain.repository.EmployeeRepository;
import com.github.mikhailerofeev.runity.domain.repository.StructureRepository;
import com.github.mikhailerofeev.runity.domain.service.ParamRatingService;
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
@RequestMapping(value = "/rest/v1/structure/")
public class StructureController {

    @Autowired
    StructureRepository structureRepository;

    @Autowired
    ParamRatingService paramRatingService;

    @PostConstruct
    public void postConstruct() {
        testFill();
    }

    private void testFill() {
        Structure testStructure = structureRepository.findByName("Отдел полиции (милиции) № 43");
        if (testStructure == null) {
            testStructure = new Structure("Отдел полиции (милиции) № 43");
            testStructure.addParam("Район", new ParamValueWithVersionId("magic","Петроградский район", true));
            testStructure.addParam("Адрес", new ParamValueWithVersionId("magic", "СПб, Монетная Б. ул., д. 27 ", true));
            testStructure.addParam("Телефон", new ParamValueWithVersionId("magic","7-812-232-43-02 ",true));
            testStructure.addParam("Начальник", new ParamValueWithVersionId("magic","Аветисянц Валерий Хуренович", true));
            structureRepository.save(testStructure);
        }
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ResponseBody
    public Structure get(@PathVariable("id") String id) {
        return structureRepository.findOne(id);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @ResponseBody
    public Map<String, String> get() {
        Map<String, String> ids2name = Maps.newHashMap();
        for (Structure structure : structureRepository.findAll()) {
            ids2name.put(structure.getId(), structure.getName());
        }
        return ids2name;
    }

    @RequestMapping(value = "/{id}/{param}/{like}", method = RequestMethod.GET)
    @ResponseBody
    public Structure paramRatingSet(@PathVariable("id") String id, @PathVariable("param") String param,
                                   @PathVariable("like") boolean like) throws Exception {

        return paramRatingService.upRatingParamStructure(id, param, like);
    }
}
