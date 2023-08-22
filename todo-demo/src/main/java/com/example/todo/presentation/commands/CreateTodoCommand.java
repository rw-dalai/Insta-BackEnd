package com.example.todo.presentation.commands;

// final fields
// records are immutable (cannot be changed)
// getter, hashcode, equals, toString
// structural equality
public record CreateTodoCommand(String title, boolean completed) {}
