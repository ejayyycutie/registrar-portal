package com.nsc.registrar_portal.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PageController {

    // --- HOMEPAGE SECTIONS ---

    @GetMapping("/")
    public String showHome() {
        return "homepage/home"; // Ito yung gumagana na ngayon
    }

    @GetMapping("/about")
    public String showAbout() {
        return "homepage/about"; // Hahanapin ang static/homepage/about.htm
    }

    @GetMapping("/service")
    public String showService() {
        return "homepage/service"; // Hahanapin ang static/homepage/service.htm
    }

    @GetMapping("/contact")
    public String showContact() {
        return "homepage/contact"; // Hahanapin ang static/homepage/contact.htm
    }

    // --- LOGINS ---
    // NOTE: Hahanapin nito ang file depende sa kung saan mo sila nilagay.
    // Base sa structure mo, hula ko nasa loob sila ng "student" o "registrar" folder,
    // O kaya nasa labas (static folder).

    @GetMapping("/login")
    public String showStudentLogin() {
        // ⚠️ IMPORTANT: Palitan mo ito kung mali ang hula ko!
        // Kung ang file ay nasa static/student/login.htm -> return "student/login";
        // Kung ang file ay nasa static/login_student.htm -> return "login_student";
        return "student/login";
    }

    @GetMapping("/registrar-login")
    public String showRegistrarLogin() {
        // ⚠️ IMPORTANT: Check mo folder mo.
        // Kung ang file ay nasa static/registrar/login.htm -> return "registrar/login";
        return "registrar/login";
    }
}