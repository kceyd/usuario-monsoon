package com.example.usuariogg.assembler;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.example.usuariogg.DTO.UsuarioGGDTO;
import com.example.usuariogg.controller.controladorusuario;
import com.example.usuariogg.model.Usuario;

@Component
public class UsuarioGGAssembler extends RepresentationModelAssemblerSupport<Usuario, UsuarioGGDTO> {

    public UsuarioGGAssembler() {
        super(controladorusuario.class, UsuarioGGDTO.class);
    }

    @SuppressWarnings("null")
    @Override
    public UsuarioGGDTO toModel(Usuario entity) {
        UsuarioGGDTO dto = new UsuarioGGDTO(
            entity.getId(),
            entity.getNombre(),
            entity.getEmail(),
            entity.getNombreUsuario(),
            entity.getRol()
        );

       
        dto.add(linkTo(methodOn(controladorusuario.class).obtenerUsuario(entity.getId())).withSelfRel());

       
        dto.add(linkTo(methodOn(controladorusuario.class).obtenerUsuarios()).withRel("usuarios"));

        return dto;
    }
}