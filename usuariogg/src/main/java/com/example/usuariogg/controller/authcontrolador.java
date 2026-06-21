package com.example.usuariogg.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.usuariogg.security.JWTUTIL;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class authcontrolador {

    @Autowired
    private JWTUTIL jwtUtil;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> loginRequest) {
        try {
           
            String nombreUsuario = loginRequest.get("nombreUsuario");
            
            if (nombreUsuario == null || nombreUsuario.isEmpty()) {
                nombreUsuario = "usuarioPrueba";
            }
            
           
            String tokenReal = jwtUtil.generarToken(nombreUsuario);
            
            return ResponseEntity.ok(Map.of(
                "status", "Exitoso",
                "token", tokenReal
            ));
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("error", "Error: " + e.getMessage()));
        }
    }
}