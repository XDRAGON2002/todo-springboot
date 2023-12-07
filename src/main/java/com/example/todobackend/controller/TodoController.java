package com.example.todobackend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.example.todobackend.repo.TodoRepo;
import com.example.todobackend.model.Todo;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.ArrayList;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;


@RestController
@RequestMapping("/api/v1")
public class TodoController {

    @Autowired
    private TodoRepo todoRepo;

    @GetMapping("/todos")
    public ResponseEntity<List<Todo>> getAllTodos() {
        List<Todo> todoList = new ArrayList<>();
        todoRepo.findAll().forEach(todoList::add);
        if (todoList.isEmpty()) {
            return new ResponseEntity<>(todoList, HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(todoList, HttpStatus.OK);
    }

    @GetMapping("/todo/{id}")
    public ResponseEntity<Todo> getTodoById(@PathVariable Long id) {
        Optional<Todo> todoData = todoRepo.findById(id);
        if (todoData.isPresent()) {
            return new ResponseEntity<>(todoData.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    
    @PostMapping("/todo")
    public ResponseEntity<Todo> addTodo(@RequestBody Todo todo) {
        Todo todoObj = todoRepo.save(todo);
        return new ResponseEntity<>(todoObj, HttpStatus.OK);
    }

    @PutMapping("/todo/{id}")
    public ResponseEntity<Todo> updateTodoById(@PathVariable Long id, @RequestBody Todo newTodoData) {
        Optional<Todo> oldTodoData = todoRepo.findById(id);
        if (oldTodoData.isPresent()) {
            Todo updatedTodoData = oldTodoData.get();
            updatedTodoData.setTitle(newTodoData.getTitle());
            updatedTodoData.setIsDone(newTodoData.getIsDone());
            Todo todoObj = todoRepo.save(updatedTodoData);
            return new ResponseEntity<>(todoObj, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/todo/{id}")
    public ResponseEntity<HttpStatus> deleteTodoById(@PathVariable Long id) {
        todoRepo.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    
}
