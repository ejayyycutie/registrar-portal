package com.nsc.registrar_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller  // <--- Importante: @Controller lang ito, HINDI @RestController
public class PageController {

    // 1. Kapag binuksan ang website (Root URL), ipakita ang Login Page
    @GetMapping("/")
    public String showLandingPage() {
        return "login"; // Dapat may file kang 'login.html' (small letters) sa templates folder
    }

    // 2. Mapping para sa /login link
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Dapat match sa filename: login.html
    }

    // 3. Mapping para sa Registrar Dashboard
    @GetMapping("/registrar/dashboard")
    public String showRegistrarDashboard() {
        return "registrar_dashboard"; // Palitan kung iba ang filename mo, e.g., 'admin.html'
    }

    // 4. Mapping para sa Student Dashboard
    @GetMapping("/student/dashboard")
    public String showStudentDashboard() {
        return "student_dashboard"; // Palitan kung iba ang filename mo, e.g., 'home.html'
    }
}