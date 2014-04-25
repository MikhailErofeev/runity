package com.github.mikhailerofeev.runnity.server;

import com.github.mikhailerofeev.runnity.domain.entities.Employee;
import com.github.mikhailerofeev.runnity.domain.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;

import java.util.Collections;
import java.util.Map;

/**
 * @author m-erofeev
 * @since 25.04.14
 */
//@ComponentScan(basePackages = "com.github.mikhailerofeev.runnity")
//@EnableAutoConfiguration
//@Configuration
//@EntityScan("com.github.mikhailerofeev.runnity")
//@EnableMongoRepositories("com.github.mikhailerofeev.runnity")

public class CLIApp implements CommandLineRunner {

  @Autowired
  private EmployeeRepository repository;

  public static void main(String[] args) {
    SpringApplication.run(CLIApp.class, args);
  }

  @SuppressWarnings("unchecked")
  @Override
  public void run(String... args) throws Exception {

    repository.deleteAll();

    // save a couple of customers
    repository.save(new Employee("Alice", (Map) Collections.emptyMap()));
    repository.save(new Employee("Bob", (Map) Collections.emptyMap()));

    // fetch all customers
    System.out.println("Customers found with findAll():");
    System.out.println("-------------------------------");
    for (Employee employee : repository.findAll()) {
      System.out.println(employee);
    }
    System.out.println();

    // fetch an individual customer
    System.out.println("Customer found with findByFirstName('Alice'):");
    System.out.println("--------------------------------");
    System.out.println(repository.findByName("Alice"));

//    System.out.println("Customers found with findByLastName('Smith'):");
//    System.out.println("--------------------------------");
//    for (Employee employee : repository.fin("Smith")) {
//      System.out.println(employee);
//    }

  }

}