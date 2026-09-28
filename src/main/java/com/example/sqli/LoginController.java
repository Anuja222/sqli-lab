package com.example.sqli;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

@RestController
public class LoginController {

    private final LoginService loginService;

    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/login")
    public LoginService.LoginResult unsafeLogin(@RequestParam String username,
                                                 @RequestParam String password) {
        return loginService.unsafeLogin(username, password);
    }

    @PostMapping("/login-secure")
    public LoginService.LoginResult secureLogin(@RequestParam String username,
                                                 @RequestParam String password) {
        return loginService.secureLogin(username, password);
    }
}

@Controller
class PageController {

    @GetMapping("/")
    String index(Model model) {
        return "index";
    }
}
