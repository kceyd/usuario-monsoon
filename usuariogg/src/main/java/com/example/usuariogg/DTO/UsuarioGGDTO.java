package com.example.usuariogg.DTO;

import org.springframework.hateoas.RepresentationModel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioGGDTO extends RepresentationModel<UsuarioGGDTO>{
private Long id;
    private String nombre;
    private String email;
    private String nombreUsuario;
    private String rol;
}
