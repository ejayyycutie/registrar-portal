package com.nsc.registrar_portal.controller;

import com.nsc.registrar_portal.entity.Request;
import com.nsc.registrar_portal.entity.Student;
import com.nsc.registrar_portal.repository.RequestRepository;
import com.nsc.registrar_portal.repository.StudentRepository;
import org.springframework.data.domain.Sort; // IMPORT ADDED
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.util.Base64;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/registrar")
public class StudentController {

    private final StudentRepository studentRepo;
    private final RequestRepository requestRepo;

    public StudentController(StudentRepository studentRepo, RequestRepository requestRepo) {
        this.studentRepo = studentRepo;
        this.requestRepo = requestRepo;
    }

    // --- 1. LIST ALL STUDENTS (ALPHABETICAL BY LASTNAME) ---
    @GetMapping("/students")
    public List<Student> getAllStudents() {
        // Sort by 'lastname' in Ascending order (A-Z)
        return studentRepo.findAll(Sort.by(Sort.Direction.ASC, "lastname"));
    }

    // --- 2. ADD NEW STUDENT ---
    @PostMapping("/student/add")
    public Map<String, Object> addStudent(
            @RequestParam String sid,
            @RequestParam String fname,
            @RequestParam String lname,
            @RequestParam String bday,
            @RequestParam String prog,
            @RequestParam String year,
            @RequestParam String status,
            @RequestParam(required = false) String email,
            @RequestParam(required = false) String phone,
            @RequestParam(required = false) MultipartFile imageFile
    ) {
        Map<String, Object> response = new HashMap<>();

        if (studentRepo.existsById(sid)) {
            response.put("status", "error");
            response.put("message", "Student ID already exists!");
            return response;
        }

        try {
            Student s = new Student();
            s.setStudentId(sid);
            s.setFirstname(fname);
            s.setLastname(lname);
            s.setBirthdate(LocalDate.parse(bday));
            s.setProgram(prog);
            s.setYearLevel(year);
            s.setStatus(status);
            s.setEmail(email);
            s.setPhone(phone);

            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    byte[] bytes = imageFile.getBytes();
                    String contentType = imageFile.getContentType();
                    String base64Image = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);
                    s.setProfilePic(base64Image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            studentRepo.save(s);
            response.put("status", "success");
            response.put("message", "Student successfully added!");
        } catch (Exception e) {
            response.put("status", "error");
            response.put("message", "Error: " + e.getMessage());
        }
        return response;
    }

    // --- 3. GET SINGLE STUDENT PROFILE ---
    @GetMapping("/student/{id}")
    public Map<String, Object> getStudentProfile(@PathVariable String id) {
        Map<String, Object> response = new HashMap<>();
        Optional<Student> studentOpt = studentRepo.findById(id);

        if (studentOpt.isPresent()) {
            response.put("status", "success");
            response.put("student", studentOpt.get());
            List<Request> history = requestRepo.findByStudent_StudentId(id);
            response.put("history", history);
        } else {
            response.put("status", "error");
            response.put("message", "Student not found");
        }
        return response;
    }

    // --- 4. UPDATE CONTACT ---
    @PostMapping("/student/update")
    public Map<String, Object> updateStudentContact(
            @RequestParam String student_id,
            @RequestParam String email,
            @RequestParam String phone
    ) {
        Map<String, Object> response = new HashMap<>();
        Optional<Student> studentOpt = studentRepo.findById(student_id);
        if (studentOpt.isPresent()) {
            Student student = studentOpt.get();
            student.setEmail(email);
            student.setPhone(phone);
            studentRepo.save(student);
            response.put("status", "success");
            response.put("message", "Contact updated.");
        } else {
            response.put("status", "error");
        }
        return response;
    }

    // --- 5. EDIT FULL DETAILS ---
    @PostMapping("/student/edit")
    public Map<String, Object> updateStudentFullDetails(
            @RequestParam String original_id,
            @RequestParam String fname,
            @RequestParam String lname,
            @RequestParam String bday,
            @RequestParam String prog,
            @RequestParam String year,
            @RequestParam String status,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam(required = false) MultipartFile imageFile
    ) {
        Map<String, Object> response = new HashMap<>();
        Optional<Student> studentOpt = studentRepo.findById(original_id);

        if (studentOpt.isPresent()) {
            Student s = studentOpt.get();
            s.setFirstname(fname);
            s.setLastname(lname);
            s.setBirthdate(LocalDate.parse(bday));
            s.setProgram(prog);
            s.setYearLevel(year);
            s.setStatus(status);
            s.setEmail(email);
            s.setPhone(phone);

            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    byte[] bytes = imageFile.getBytes();
                    String contentType = imageFile.getContentType();
                    String base64Image = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);
                    s.setProfilePic(base64Image);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            studentRepo.save(s);
            response.put("status", "success");
            response.put("message", "Student record updated!");
        } else {
            response.put("status", "error");
            response.put("message", "Student not found.");
        }
        return response;
    }
}