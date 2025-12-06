package com.nsc.registrar_portal.repository;

import com.nsc.registrar_portal.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository; // Import this

@Repository // <--- ADD THIS
public interface StudentRepository extends JpaRepository<Student, String> {
}