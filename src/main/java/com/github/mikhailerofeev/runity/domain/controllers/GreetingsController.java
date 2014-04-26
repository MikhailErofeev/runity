package com.github.mikhailerofeev.runity.domain.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;

/**
 * @author Mikhail Erofeev https://github.com/MikhailErofeev
 * @since 09.04.14
 */

@RestController
@Deprecated //for testing purposes
public class GreetingsController {

    @RequestMapping(value = "/rest/v1/greeting", method = RequestMethod.GET)
    public
    @ResponseBody
    Greeting greeting(Authentication authentication, WebRequest webRequest) {
        String userName = authentication == null ? "Anonymous" : authentication.getName();


        testMongo();

        return new Greeting(String.format("Hello, %s!", userName));
    }

    private void testMongo() {

    }

}
