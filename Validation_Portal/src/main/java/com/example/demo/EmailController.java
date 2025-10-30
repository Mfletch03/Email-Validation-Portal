package com.example.demo;

import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.naming.directory.*;
import javax.naming.NamingException;
import java.util.Hashtable;

@Controller
public class EmailController {

    @GetMapping("/email-check")
    public String showForm() {
        return "email-form"; // Loads email-form.html
    }
   
    
    @PostMapping("/submit")
    public String checkEmail(@RequestParam("email") String email, Model model) {
        String result;

        if (!isValidSyntax(email)) {
            result = "❌ " + email + " is not a valid email format.";
        } else if (!hasValidDomain(email)) {
            result = "⚠️ " + email + " has a valid format, but the domain does not exist.";
        } else {
            result = "✅ " + email + " is valid and the domain exists!";
        }

        model.addAttribute("result", result);
        return "result"; // Loads result.html
    }

    // Validate email format using Apache Commons Validator
    private boolean isValidSyntax(String email) {
        EmailValidator validator = EmailValidator.getInstance();
        return validator.isValid(email);
    }

    // Check if email domain has MX records (can receive mail)
    private boolean hasValidDomain(String email) {
        try {
            String domain = email.substring(email.indexOf("@") + 1);
            Hashtable<String, String> env = new Hashtable<>();
            env.put("java.naming.factory.initial", "com.sun.jndi.dns.DnsContextFactory");
            DirContext ctx = new InitialDirContext(env);
            Attributes attrs = ctx.getAttributes(domain, new String[]{"MX"});
            Attribute attr = attrs.get("MX");
            return attr != null && attr.size() > 0;
        } catch (NamingException e) {
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
