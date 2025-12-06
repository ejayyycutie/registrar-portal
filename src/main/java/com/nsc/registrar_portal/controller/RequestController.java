package com.nsc.registrar_portal.controller;

import com.nsc.registrar_portal.entity.Request;
import com.nsc.registrar_portal.entity.Student;
import com.nsc.registrar_portal.repository.RequestRepository;
import com.nsc.registrar_portal.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter; // IMPORT ADDED
import java.util.*;

@RestController
@RequestMapping("/api")
public class RequestController {

    private final RequestRepository requestRepo;
    private final StudentRepository studentRepo;

    public RequestController(RequestRepository requestRepo, StudentRepository studentRepo) {
        this.requestRepo = requestRepo;
        this.studentRepo = studentRepo;
    }

    // --- 1. STUDENT: SUBMIT REQUEST ---
    @PostMapping("/request/submit")
    public Map<String, Object> submitRequest(
            @RequestParam String doc_type,
            @RequestParam String purpose,
            @RequestParam Integer copies,
            @RequestParam(required = false) String student_id
    ) {
        Map<String, Object> response = new HashMap<>();
        String targetStudentId = (student_id != null && !student_id.isEmpty()) ? student_id : "233657";

        Optional<Student> student = studentRepo.findById(targetStudentId);

        if (student.isPresent()) {
            Request req = new Request();
            req.setStudent(student.get());
            req.setDocumentType(doc_type);
            req.setPurpose(purpose);
            req.setCopies(copies);
            req.setStatus("Pending");
            req.setDateRequested(LocalDateTime.now());

            String code = "REQ-" + (1000 + new Random().nextInt(9000));
            req.setTransactionCode(code);

            requestRepo.save(req);

            response.put("status", "success");
            response.put("message", "Request Submitted Successfully!");
            response.put("code", code);
        } else {
            response.put("status", "error");
            response.put("message", "Student ID Not Found.");
        }
        return response;
    }

    // --- 2. STUDENT: GET MY REQUESTS (With AM/PM Formatting) ---
    @GetMapping("/student/requests")
    public List<Map<String, Object>> getStudentRequests(@RequestParam String student_id) {
        List<Request> requests = requestRepo.findByStudent_StudentId(student_id);
        List<Map<String, Object>> responseList = new ArrayList<>();

        // Define AM/PM Formatter
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");

        for (Request req : requests) {
            Map<String, Object> map = new HashMap<>();
            map.put("transaction_code", req.getTransactionCode());
            map.put("document_type", req.getDocumentType());
            map.put("status", req.getStatus());
            map.put("date_requested", req.getDateRequested().toString());

            if (req.getAppointmentDate() != null) {
                map.put("sched_date", req.getAppointmentDate().toString());

                // FORMAT TIME HERE (e.g., 14:00 -> 02:00 PM)
                if (req.getAppointmentTime() != null) {
                    map.put("sched_time", req.getAppointmentTime().format(timeFormatter));
                } else {
                    map.put("sched_time", "");
                }
            }

            responseList.add(map);
        }

        return responseList;
    }
}