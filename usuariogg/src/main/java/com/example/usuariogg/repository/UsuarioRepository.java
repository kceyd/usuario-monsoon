package com.example.usuariogg.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.usuariogg.model.usuario;


public interface UsuarioRepository extends JpaRepository<usuario, Long> {

}
