package com.nsc.registrar_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // --- HOMEPAGE (Ito tama na to) ---
    @GetMapping("/")
    public String showHome() {
        return "homepage/home";
    }

    @GetMapping("/about")
    public String showAbout() {
        return "homepage/about";
    }

    @GetMapping("/service")
    public String showService() {
        return "homepage/service";
    }

    @GetMapping("/contact")
    public String showContact() {
        return "homepage/contact";
    }

    // --- LOGINS (ITO ANG FINAL FIX) ---
    // Base sa screenshot mo: "login_student.htm" at "login_registrar.htm"

    @GetMapping("/login")
    public String showStudentLogin() {
        return "login_student"; // Ito ang pangalan ng file mo sa screenshot
    }

    @GetMapping("/registrar-login")
    public String showRegistrarLogin() {
        return "login_registrar"; // Ito ang pangalan ng file mo sa screenshot
    }

    // --- DASHBOARDS ---
    @GetMapping("/registrar/dashboard")
    public String showRegistrarDashboard() {
        return "registrar/dashboard";
    }

    @GetMapping("/student/dashboard")
    public String showStudentDashboard() {
        return "student/dashboard";
    }
}