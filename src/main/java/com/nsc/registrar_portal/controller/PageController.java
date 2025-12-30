package com.nsc.registrar_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // --- HOMEPAGE ---
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

    // --- LOGINS (ITO ANG NAG-EERROR SA'YO) ---

    @GetMapping("/login") // Student Login
    public String showStudentLogin() {
        // Hula ko nasa loob ito ng "student" folder
        return "student/login";
    }

    @GetMapping("/registrar-login") // Registrar Login
    public String showRegistrarLogin() {
        // Hula ko nasa loob ito ng "registrar" folder, pero wala sa screenshot mo kanina
        // Kung "dashboard.htm" lang ang nandun, baka nasa ibang folder ang login mo?
        // Pansamantala, try natin ito:
        return "registrar/login";
    }

    // --- DASHBOARDS ---
    @GetMapping("/registrar/dashboard")
    public String showRegistrarDashboard() {
        return "registrar/dashboard";
    }
}