package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
public class PasswordController {

    @GetMapping("/password-check")
    public String showPasswordForm() {
        return "password-form"; // loads password-form.html
    }


    @PostMapping("/check-password")
    public String checkPassword(@RequestParam("password") String password, Model model) {
        String result = getPasswordStrength(password);
        model.addAttribute("result", result);
        return "result"; // ✅ same result.html
    }

    private String getPasswordStrength(String password) {
        int score = 0;
        if (password.length() >= 8) score++;
        if (password.matches(".*[A-Z].*")) score++;
        if (password.matches(".*[a-z].*")) score++;
        if (password.matches(".*\\d.*")) score++;
        if (password.matches(".*[!@#$%^&*()].*")) score++;

        switch (score) {
            case 5:
                return "✅ Strong password!";
            case 3:
            case 4:
                return "⚠️ Medium strength password.";
            default:
                return "❌ Weak password. Try adding numbers, symbols, and upper/lowercase letters.";
        }
    }
}

