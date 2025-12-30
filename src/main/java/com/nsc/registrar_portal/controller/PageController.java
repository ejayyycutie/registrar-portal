package com.nsc.registrar_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // 1. Homepage Mapping
    @GetMapping("/")
    public String showHome() {
        // Dahil nasa loob siya ng "homepage" folder sa static:
        return "homepage/home";
    }

    // 2. Login Mapping
    @GetMapping("/login")
    public String showLogin() {
        // Hanapin mo kung saang folder nakalagay ang login.htm mo.
        // Kung nasa homepage folder din siya:
        return "homepage/login";
        // Kung nasa labas siya (direct sa static): return "login";
    }

    // 3. Registrar Dashboard
    @GetMapping("/registrar/dashboard")
    public String showRegistrarDashboard() {
        // Base sa screenshot mo, nasa "registrar" folder ito:
        return "registrar/dashboard";
    }
}