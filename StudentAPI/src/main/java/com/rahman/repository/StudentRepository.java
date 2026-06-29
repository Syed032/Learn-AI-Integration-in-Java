package com.rahman.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.rahman.entity.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {

}