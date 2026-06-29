package com.rahman.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;

import com.rahman.entity.Student;
import com.rahman.service.StudentService;

import com.rahman.dto.MessageRequest;
import com.rahman.dto.MessageResponse;

@RestController
@RequestMapping("/student")
public class StudentController {

    @Autowired
    private StudentService service;

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return service.saveStudent(student);
    }

    @GetMapping
    public List<Student> getStudents() {
        return service.getAllStudents();
    }

    @PutMapping
    public Student updateStudent(@RequestBody Student student) {
        return service.updateStudent(student);
    }

    @DeleteMapping("/{id}")
    public String deleteStudent(@PathVariable int id) {
        service.deleteStudent(id);
        return "Student Deleted Successfully";
    }
    
    @GetMapping("/github")
    public String githubUser() {

        WebClient client = WebClient.create();

        return client.get()
                .uri("https://api.github.com/users/sandesh721")
                .retrieve()
                .bodyToMono(String.class)
                .block();
    }
    
    @PostMapping("/echo")
    public MessageResponse echo(
            @RequestBody MessageRequest request) {

        return new MessageResponse(request.getMessage());
    }
    
    
}