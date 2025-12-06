package com.nsc.registrar_portal.controller;

import com.nsc.registrar_portal.entity.Registrar;
import com.nsc.registrar_portal.entity.Request;
import com.nsc.registrar_portal.repository.RegistrarRepository;
import com.nsc.registrar_portal.repository.RequestRepository;
import com.nsc.registrar_portal.repository.StudentRepository;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/registrar")
public class RegistrarController {

    private final RequestRepository requestRepo;
    private final StudentRepository studentRepo;
    private final RegistrarRepository registrarRepo;

    public RegistrarController(RequestRepository requestRepo, StudentRepository studentRepo, RegistrarRepository registrarRepo) {
        this.requestRepo = requestRepo;
        this.studentRepo = studentRepo;
        this.registrarRepo = registrarRepo;
    }

    // 1. GET DASHBOARD STATS
    @GetMapping("/stats")
    public Map<String, Long> getStats() {
        Map<String, Long> stats = new HashMap<>();
        long pendingCount = requestRepo.findByStatus("Pending").stream().count();
        long approvedTodayCount = requestRepo.findByStatus("Approved").stream()
                .filter(req -> req.getAppointmentDate() != null && req.getAppointmentDate().isEqual(LocalDate.now()))
                .count();
        stats.put("pending", pendingCount);
        stats.put("approved_today", approvedTodayCount);
        stats.put("total_students", studentRepo.count());
        return stats;
    }

    // 2. GET PENDING REQUESTS
    @GetMapping("/requests/pending")
    public List<Map<String, Object>> getPendingRequests() {
        return requestRepo.findByStatus("Pending").stream().map(req -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transaction_code", req.getTransactionCode());
            map.put("document_type", req.getDocumentType());
            map.put("date_requested", req.getDateRequested() != null ? req.getDateRequested().toString() : "");
            if (req.getStudent() != null) {
                map.put("student_name", req.getStudent().getFirstname() + " " + req.getStudent().getLastname());
                map.put("program", req.getStudent().getProgram());
            } else {
                map.put("student_name", "Unknown"); map.put("program", "N/A");
            }
            return map;
        }).collect(Collectors.toList());
    }

    // 3. GET APPROVED APPOINTMENTS
    @GetMapping("/requests/approved")
    public List<Map<String, Object>> getApprovedRequests() {
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
        return requestRepo.findByStatus("Approved").stream().map(req -> {
            Map<String, Object> map = new HashMap<>();
            map.put("transaction_code", req.getTransactionCode());
            map.put("sched_date", req.getAppointmentDate().toString());
            map.put("sched_time", req.getAppointmentTime() != null ? req.getAppointmentTime().format(timeFormatter) : "");
            if (req.getStudent() != null) {
                map.put("student_name", req.getStudent().getFirstname() + " " + req.getStudent().getLastname());
            } else {
                map.put("student_name", "Unknown");
            }
            map.put("document_type", req.getDocumentType());
            return map;
        }).collect(Collectors.toList());
    }

    // 4. PROCESS REQUEST
    @PostMapping("/request/process")
    public Map<String, Object> processRequest(
            @RequestParam String action, @RequestParam String trans_code,
            @RequestParam(required = false) String sched_date, @RequestParam(required = false) String sched_time
    ) {
        Map<String, Object> response = new HashMap<>();
        Request req = requestRepo.findByTransactionCode(trans_code);
        if (req != null) {
            if ("approve".equalsIgnoreCase(action)) {
                req.setStatus("Approved");
                if (sched_date != null && !sched_date.isEmpty()) req.setAppointmentDate(LocalDate.parse(sched_date));
                if (sched_time != null && !sched_time.isEmpty()) req.setAppointmentTime(LocalTime.parse(sched_time));
            } else if ("reject".equalsIgnoreCase(action)) {
                req.setStatus("Declined");
            } else if ("complete".equalsIgnoreCase(action)) {
                req.setStatus("Completed");
            }
            requestRepo.save(req);
            response.put("status", "success");
        } else {
            response.put("status", "error"); response.put("message", "Transaction not found.");
        }
        return response;
    }

    // --- 5. UPDATE REGISTRAR SETTINGS (UPDATED FOR ALL IMAGE TYPES) ---
    @PostMapping("/update-settings")
    public Map<String, Object> updateRegistrarSettings(
            @RequestParam String office_email,
            @RequestParam String office_phone,
            @RequestParam(required = false) MultipartFile imageFile
    ) {
        Map<String, Object> response = new HashMap<>();
        // Find Admin
        Optional<Registrar> registrarOpt = registrarRepo.findByRegistrarId("102384");

        if (registrarOpt.isPresent()) {
            Registrar registrar = registrarOpt.get();
            registrar.setEmail(office_email);
            registrar.setPhone(office_phone);

            // IMAGE UPLOAD LOGIC (SUPPORT ALL TYPES)
            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    byte[] bytes = imageFile.getBytes();
                    String contentType = imageFile.getContentType(); // Get real type (jpeg, png, etc.)
                    String base64Image = "data:" + contentType + ";base64," + Base64.getEncoder().encodeToString(bytes);
                    registrar.setProfilePic(base64Image);
                } catch (Exception e) {
                    e.printStackTrace();
                    response.put("status", "error");
                    response.put("message", "Image Error: " + e.getMessage());
                    return response;
                }
            }

            registrarRepo.save(registrar);
            response.put("status", "success");
            response.put("message", "Profile updated successfully!");
        } else {
            response.put("status", "error");
            response.put("message", "Registrar not found.");
        }
        return response;
    }

    // 6. GET PROFILE
    @GetMapping("/profile")
    public Map<String, Object> getRegistrarProfile() {
        Map<String, Object> response = new HashMap<>();
        Optional<Registrar> registrarOpt = registrarRepo.findByRegistrarId("102384");
        if (registrarOpt.isPresent()) {
            response.put("status", "success");
            response.put("data", registrarOpt.get());
        } else {
            response.put("status", "error");
        }
        return response;
    }
}