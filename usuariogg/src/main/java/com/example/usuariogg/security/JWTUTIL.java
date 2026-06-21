package com.example.usuariogg.security;

import java.security.Key;
import java.util.Date;

import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

// Esta clase se encarga de TODO lo relacionado al token:
// crearlo, leer el usuario que tiene dentro, y revisar si sigue siendo valido
@Component
public class JWTUTIL {

    // Clave secreta para firmar el token. Mientras mas larga, mejor.
    private final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // El token dura 24 horas
    private final long EXPIRACION = 1000 * 60 * 60 * 24;

    // Genera un token nuevo para el usuario que hizo login
    public String generarToken(String nombreUsuario) {
        return Jwts.builder()
                .setSubject(nombreUsuario)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRACION))
                .signWith(SECRET_KEY)
                .compact();
    }

    // Lee el nombre de usuario que esta guardado dentro del token
    public String obtenerUsuario(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getSubject();
    }

    // Revisa que el token no haya sido modificado y que no haya expirado
    public boolean esValido(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(SECRET_KEY).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
