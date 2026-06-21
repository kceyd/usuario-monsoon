package com.example.usuariogg.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.usuariogg.DTO.UsuarioGGDTO;
import com.example.usuariogg.assembler.UsuarioGGAssembler;
import com.example.usuariogg.model.Usuario;
import com.example.usuariogg.service.serviciousuario;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/v0/usuarios")
public class controladorusuario {

    @Autowired
    private serviciousuario usuarioService;

    @Autowired
    private UsuarioGGAssembler assembler;

    
    @GetMapping
    public ResponseEntity<CollectionModel<UsuarioGGDTO>> obtenerUsuarios() {
        List<UsuarioGGDTO> usuarios = usuarioService.obtenerUsuarios().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        CollectionModel<UsuarioGGDTO> coleccion = CollectionModel.of(usuarios,
                linkTo(methodOn(controladorusuario.class).obtenerUsuarios()).withSelfRel());

        return ResponseEntity.ok(coleccion);
    }

    @PostMapping
    public ResponseEntity<UsuarioGGDTO> crearUsuario(@Valid @RequestBody Usuario usuario) {
        Usuario creado = usuarioService.crearUsuario(usuario);
        UsuarioGGDTO dto = assembler.toModel(creado);
        return ResponseEntity.status(HttpStatus.CREATED).body(dto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGGDTO> obtenerUsuario(@PathVariable Long id) {
        Usuario encontrado = usuarioService.obtenerUsuario(id);
        if (encontrado == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(assembler.toModel(encontrado));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioGGDTO> actualizarUsuario(@PathVariable Long id, @Valid @RequestBody Usuario usuario) {
        if (usuarioService.obtenerUsuario(id) == null) {
            return ResponseEntity.notFound().build();
        }
        usuario.setId(id);
        Usuario actualizado = usuarioService.actualizarUsuario(usuario);
        return ResponseEntity.ok(assembler.toModel(actualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Long id) {
        if (usuarioService.obtenerUsuario(id) == null) {
            return ResponseEntity.notFound().build();
        }
        usuarioService.eliminarUsuario(id);
        return ResponseEntity.noContent().build();
    }

}