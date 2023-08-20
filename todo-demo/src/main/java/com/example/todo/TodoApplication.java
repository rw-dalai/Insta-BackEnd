package com.example.todo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TodoApplication {

  public static void main(String[] args) {

    System.out.println("Hello World");

    SpringApplication.run(TodoApplication.class, args);
  }
}
