package com.example.usuariogg.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "usuarios") // Ajusta al nombre de tu tabla en MySQL si es diferente
@Data                     // <-- Genera automáticamente todos los Getters y Setters
@NoArgsConstructor        // <-- Genera el constructor vacío obligatorio para JPA
@AllArgsConstructor       // <-- Genera el constructor con todos los atributos
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private String email;
    private String nombreUsuario;
    private String contraseña;
    private String rol;
}