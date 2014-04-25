package com.github.mikhailerofeev.runity.server;

import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

/**
 * @author Mikhail Erofeev https://github.com/MikhailErofeev
 * @since 09.04.14
 */
@ComponentScan(basePackages = "com.github.mikhailerofeev.runity")
@EnableAutoConfiguration
@Configuration
@EnableMongoRepositories("com.github.mikhailerofeev.runity")
public class Application{

  public static void main(String[] args) {
    new SpringApplicationBuilder(Application.class)
        .showBanner(false)
        .run(args);
  }      

}
