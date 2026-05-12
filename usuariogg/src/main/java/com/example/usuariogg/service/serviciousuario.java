package com.example.usuariogg.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.usuariogg.model.usuario;
import com.example.usuariogg.repository.UsuarioRepository;

import jakarta.transaction.Transactional;


@Service
@Transactional

public class serviciousuario {
     @Autowired
    private UsuarioRepository usuarioRepository;

    public void crearUsuario(usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public List<usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }

    public usuario obtenerUsuario(Long id) {
        return usuarioRepository.findById(id).orElse(null);
    }

    public void actualizarUsuario(usuario usuario) {
        usuarioRepository.save(usuario);
    }

    public void eliminarUsuario(Long id) {
        usuarioRepository.deleteById(id);
    }
}


