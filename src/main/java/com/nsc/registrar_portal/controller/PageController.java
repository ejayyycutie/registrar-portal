package com.nsc.registrar_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // ITO ANG PAGBABAGO:
    // Kapag binuksan ang "nscregistrar.com" o root url, "home.html" ang lalabas.
    @GetMapping("/")
    public String showLandingPage() {
        return "home"; // Dapat match sa filename na home.html sa templates folder
    }

    // Mapping para sa Login (Kung click nila yung Student Portal button)
    @GetMapping("/login")
    public String showLoginPage() {
        return "login"; // Dapat may login.html ka
    }

    // Mapping para sa Registrar Login
    @GetMapping("/registrar-login")
    public String showRegistrarLoginPage() {
        return "registrar_login"; // Palitan kung anong filename ng registrar login mo
    }

    // ... iba pang mappings (dashboard, etc.)
}