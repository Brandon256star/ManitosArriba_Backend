package com.example.tp_grupo6.services;

import com.example.tp_grupo6.dtos.UsuarioDto;

import java.util.List;

public interface UsuarioService {
    
    List<UsuarioDto> getAllUsuarios();
    
    UsuarioDto getUsuarioById(Long id);
    
    UsuarioDto createUsuario(UsuarioDto usuarioDto);
    
    UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto);
    
    void deleteUsuario(Long id);
    
    boolean isOwner(Long id, String username);
    
    UsuarioDto getUsuarioByUsername(String username);
}