package com.ragaroads.controllers;

import org.springframework.web.bind.annotation.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private static final Map<String, Map<String, Object>> USERS = new HashMap<>();

    static {
        Map<String, Object> admin = new HashMap<>();
        admin.put("userId", 1L);
        admin.put("username", "admin");
        admin.put("password", "admin123");
        admin.put("role", "Administrator");
        USERS.put("admin", admin);

        Map<String, Object> manager = new HashMap<>();
        manager.put("userId", 2L);
        manager.put("username", "manager");
        manager.put("password", "manager123");
        manager.put("role", "Band Manager");
        USERS.put("manager", manager);

        Map<String, Object> artist1 = new HashMap<>();
        artist1.put("userId", 3L);
        artist1.put("username", "artist1");
        artist1.put("password", "artist123");
        artist1.put("role", "Artist");
        USERS.put("artist1", artist1);

        Map<String, Object> artist2 = new HashMap<>();
        artist2.put("userId", 4L);
        artist2.put("username", "artist2");
        artist2.put("password", "artist123");
        artist2.put("role", "Artist");
        USERS.put("artist2", artist2);

        Map<String, Object> artist3 = new HashMap<>();
        artist3.put("userId", 5L);
        artist3.put("username", "artist3");
        artist3.put("password", "artist123");
        artist3.put("role", "Artist");
        USERS.put("artist3", artist3);
    }

    @PostMapping("/login")
    public Map<String, Object> login(@RequestBody Map<String, String> request) {
        String username = request.get("username");
        String password = request.get("password");
        Map<String, Object> response = new HashMap<>();

        if (!USERS.containsKey(username)) {
            response.put("authenticated", false);
            response.put("message", "Invalid Username");
            return response;
        }

        Map<String, Object> user = USERS.get(username);
        if (!password.equals(user.get("password"))) {
            response.put("authenticated", false);
            response.put("message", "Invalid Password");
            return response;
        }

        String token = Base64.getEncoder()
                .encodeToString((user.get("username") + ":" + user.get("role")).getBytes());

        response.put("authenticated", true);
        response.put("userId", user.get("userId"));
        response.put("username", user.get("username"));
        response.put("role", user.get("role"));
        response.put("token", token);
        return response;
    }

    @PostMapping("/validate")
    public Map<String, Object> validateToken(
            @RequestHeader("Authorization") String token) {
        Map<String, Object> response = new HashMap<>();
        try {
            String decoded = new String(Base64.getDecoder().decode(token));
            String[] values = decoded.split(":");
            response.put("valid", true);
            response.put("username", values[0]);
            response.put("role", values[1]);
        } catch (Exception ex) {
            response.put("valid", false);
            response.put("message", "Invalid Token");
        }
        return response;
    }
}
