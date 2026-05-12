package com.example.usuariogg.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuariogg.model.usuario;
import com.example.usuariogg.service.serviciousuario;

@RestController
@RequestMapping("/api/v0/usuarios")
public class controladorusuario {


    @Autowired
    private serviciousuario usuarioService;

    @GetMapping
    public List<usuario> obtenerUsuarios() {
        return usuarioService.obtenerUsuarios();
    }

    @PostMapping
    public void crearUsuario(@RequestBody usuario usuario) {
        usuarioService.crearUsuario(usuario);
    }

    @GetMapping("/{id}")
    public usuario obtenerUsuario(@PathVariable Long id) {
        return usuarioService.obtenerUsuario(id);
    }

    @PutMapping("/{id}")
    public void actualizarUsuario(@PathVariable Long id, @RequestBody usuario usuario) {
        usuario.setId(id);
        usuarioService.actualizarUsuario(usuario);
    }

    @DeleteMapping("/{id}")
    public void eliminarUsuario(@PathVariable Long id) {
        usuarioService.eliminarUsuario(id);
    }
}


