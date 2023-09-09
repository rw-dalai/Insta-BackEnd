package com.example.todo.presentation.commands;

// final fields
// records are immutable (cannot be changed)
// getter, hashcode, equals, toString
// structural equality
public record TodoCommand(String title, boolean completed) {}
