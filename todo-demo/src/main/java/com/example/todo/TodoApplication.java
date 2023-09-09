package com.example.todo;

import com.example.todo.domain.Todo;
import com.example.todo.persistence.TodoRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

// @SpringBootApplication
@SpringBootApplication(exclude = {SecurityAutoConfiguration.class})
public class TodoApplication {

  // https://www.slf4j.org/manual.html
  private final Logger LOGGER = LoggerFactory.getLogger(TodoApplication.class);

  public static void main(String[] args) {

    SpringApplication.run(TodoApplication.class, args);
  }

  // Gets executed when the spring container is fully initialised
  @Bean
  public CommandLineRunner commandLineRunner(
      ApplicationContext ctx, TodoRepository todoRepository) {
    return args -> {
      LOGGER.info("Save one todo into the DB");
      todoRepository.deleteAll();
      todoRepository.save(new Todo("todo title", false));
      // System.out.println("Hello Spring 1");
      //      LOGGER.info("Command Line Runner shows the bean names...");

      //      String[] beanNames = ctx.getBeanDefinitionNames();
      //      Arrays.sort(beanNames);

      //      for (String beanName : beanNames) LOGGER.info(beanName);
    };
  }

  //  @Bean
  //  public CommandLineRunner commandLineRunner2(ApplicationContext ctx) {
  //    return args -> {
  //      System.out.println("Hello Spring 2");
  //    };
  //  }
}
