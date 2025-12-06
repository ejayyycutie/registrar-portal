package com.nsc.registrar_portal.controller;

import com.nsc.registrar_portal.entity.Registrar;
import com.nsc.registrar_portal.entity.Student;
import com.nsc.registrar_portal.repository.RegistrarRepository;
import com.nsc.registrar_portal.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class AuthController {

    private final StudentRepository studentRepo;
    private final RegistrarRepository registrarRepo;

    public AuthController(StudentRepository studentRepo, RegistrarRepository registrarRepo) {
        this.studentRepo = studentRepo;
        this.registrarRepo = registrarRepo;
    }

    @PostMapping("/login")
    public Map<String, Object> studentLogin(@RequestParam String student_id, @RequestParam String birthdate) {
        Map<String, Object> response = new HashMap<>();
        Optional<Student> student = studentRepo.findById(student_id); // Using String ID

        if (student.isPresent()) {
            if (student.get().getBirthdate().toString().equals(birthdate)) {
                response.put("status", "success");
                response.put("message", "Login Successful");
                response.put("student", student.get());
            } else {
                response.put("status", "error");
                response.put("message", "Invalid Birthdate (Password).");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Student ID not found.");
        }
        return response;
    }

    @PostMapping("/auth/registrar-login")
    public Map<String, Object> registrarLogin(@RequestParam String reg_id, @RequestParam String birthdate) {
        Map<String, Object> response = new HashMap<>();
        Optional<Registrar> reg = registrarRepo.findByRegistrarId(reg_id); // Using String ID

        if (reg.isPresent()) {
            if (reg.get().getPassword().equals(birthdate)) {
                response.put("status", "success");
                response.put("message", "Welcome Registrar!");
            } else {
                response.put("status", "error");
                response.put("message", "Invalid Birthdate (Password).");
            }
        } else {
            response.put("status", "error");
            response.put("message", "Registrar ID not found.");
        }
        return response;
    }
}