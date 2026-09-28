package com.example.sqli;

import java.util.List;
import java.util.Map;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class LoginService {

    private final JdbcTemplate jdbcTemplate;

    public LoginService(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public LoginResult unsafeLogin(String username, String password) {
        String sql = "SELECT id, username FROM users WHERE username = '"
                + username + "' AND password = '" + password + "'";

        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql);
        return new LoginResult(!users.isEmpty(), sql,
                users.isEmpty() ? "Login failed" : "Login succeeded");
    }

    public LoginResult secureLogin(String username, String password) {
        String sql = "SELECT id, username FROM users WHERE username = ? AND password = ?";

        List<Map<String, Object>> users = jdbcTemplate.queryForList(sql, username, password);
        return new LoginResult(!users.isEmpty(), sql,
                users.isEmpty() ? "Login failed" : "Login succeeded");
    }

    public record LoginResult(boolean success, String sql, String message) {
    }
}
