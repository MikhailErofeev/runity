package com.github.mikhailerofeev.runity.server;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

/**
 * @author m-erofeev
 * @since 25.04.14
 */
//@ComponentScan(basePackages = "com.github.mikhailerofeev.runity")
//@EnableAutoConfiguration
//@Configuration
//@EntityScan("com.github.mikhailerofeev.runity")
//@EnableMongoRepositories("com.github.mikhailerofeev.runity")

public class CLIApp implements CommandLineRunner {

  public static void main(String[] args) {
    SpringApplication.run(CLIApp.class, args);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run(String... args) throws Exception {



//    System.out.println("Customers found with findByLastName('Smith'):");
//    System.out.println("--------------------------------");
//    for (Employee employee : repository.fin("Smith")) {
//      System.out.println(employee);
//    }

  }

}