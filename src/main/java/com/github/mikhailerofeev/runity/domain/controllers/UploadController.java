package com.github.mikhailerofeev.runity.domain.controllers;

import com.github.mikhailerofeev.runity.domain.entities.DataPassport;
import com.github.mikhailerofeev.runity.domain.repository.DataPassportRepository;
import com.github.mikhailerofeev.runity.domain.service.DataUploadService;
import com.github.mikhailerofeev.runity.domain.service.DataUtils;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 27.04.14
 */
@Controller
@MultipartConfig
@RequestMapping(value = "/rest/v1/upload/")
public class UploadController {

    @Autowired
    DataUploadService dataUploadService;

    @Autowired
    DataPassportRepository dataPassportRepository;

    @RequestMapping(value = "", headers = "content-type=multipart/*", method = RequestMethod.POST)
    public String upload(WebRequest webRequest, @RequestParam("csv") MultipartFile multipartFile) throws IOException, ServletException {
        final String author = webRequest.getParameter("author").trim();
        final List<String> allColumns = splitByLines(webRequest.getParameter("allColumns"));
        final List<String> importantColumns = splitByLines(webRequest.getParameter("importantColumns"));
        final Map<String, String> additionalConstants = splitToMap(splitByLines(webRequest.getParameter("additionalColumns")));
        final String description = webRequest.getParameter("description").trim();
        final String url = webRequest.getParameter("url").trim();
        final boolean isEmployee = "employeee".equals(webRequest.getParameter("type"));
        final BufferedReader csvReader = new BufferedReader(new InputStreamReader(multipartFile.getInputStream()));
        final List<Map<String, String>> niceCsv = DataUtils.parseCsvToStructuredMap(allColumns, csvReader);
        final DataPassport passport = dataPassportRepository.save(new DataPassport(author, url, description, DateTime.now()));
        DataUtils.filterUnimportant(niceCsv, importantColumns);
        addConstants(additionalConstants, niceCsv);
        if(isEmployee) {
            dataUploadService.employeesUpload(niceCsv, "name", passport);
        }
        else{
            dataUploadService.structuresUpload(niceCsv, "name", passport);
        }

        return "/rest/v1/employee/";
    }

    private void addConstants(Map<String, String> additionalConstants, List<Map<String, String>> niceCsv) {
        for (Map<String, String> employee : niceCsv) {
            employee.putAll(additionalConstants);
        }
    }

    private Map<String, String> splitToMap(List<String> additionalColumns) {
        Map<String, String> ret = Maps.newHashMap();
        for (String additionalColumn : additionalColumns) {
            final String[] k2v = additionalColumn.split(":");
            ret.put(k2v[0].trim(), k2v[1].trim());
        }
        return ret;
    }

    private List<String> splitByLines(String src) {
        return Splitter.on("\n").trimResults().omitEmptyStrings().splitToList(src);
    }
}
