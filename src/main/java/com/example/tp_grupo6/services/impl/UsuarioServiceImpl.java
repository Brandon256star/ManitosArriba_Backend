package com.example.tp_grupo6.services.impl;

import com.example.tp_grupo6.dtos.UsuarioDto;
import com.example.tp_grupo6.entities.Rol;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.RolRepository;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import com.example.tp_grupo6.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UsuarioServiceImpl implements UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    
    @Autowired
    private RolRepository rolRepository;
    
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UsuarioDto> getAllUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        return usuarios.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public UsuarioDto getUsuarioById(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        return convertToDto(usuario);
    }

    @Override
    public UsuarioDto createUsuario(UsuarioDto usuarioDto) {
        // Verificar que el username no existe
        if (usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            throw new RuntimeException("El username ya existe: " + usuarioDto.getUsername());
        }
        
        // Verificar que el email no existe
        if (usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new RuntimeException("El email ya existe: " + usuarioDto.getEmail());
        }
        
        // Buscar el rol por tipo
        Rol rol = rolRepository.findByTipoRol(usuarioDto.getRolTipo())
                .orElse(null); // Si no especifica rol, se asigna después
        
        // Crear el usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        // Por defecto usar el username como password inicial (debe cambiarse después)
        usuario.setPassword(passwordEncoder.encode(usuarioDto.getUsername()));
        usuario.setRol(rol);
        usuario.setActivo(usuarioDto.getActivo() != null ? usuarioDto.getActivo() : true);
        usuario.setFechaCreacion(LocalDateTime.now());
        
        Usuario savedUsuario = usuarioRepository.save(usuario);
        return convertToDto(savedUsuario);
    }

    @Override
    public UsuarioDto updateUsuario(Long id, UsuarioDto usuarioDto) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        
        // Verificar username duplicado (excepto el actual)
        if (!usuario.getUsername().equals(usuarioDto.getUsername()) && 
            usuarioRepository.existsByUsername(usuarioDto.getUsername())) {
            throw new RuntimeException("El username ya existe: " + usuarioDto.getUsername());
        }
        
        // Verificar email duplicado (excepto el actual)
        if (!usuario.getEmail().equals(usuarioDto.getEmail()) && 
            usuarioRepository.existsByEmail(usuarioDto.getEmail())) {
            throw new RuntimeException("El email ya existe: " + usuarioDto.getEmail());
        }
        
        // Actualizar campos
        usuario.setUsername(usuarioDto.getUsername());
        usuario.setEmail(usuarioDto.getEmail());
        usuario.setNombre(usuarioDto.getNombre());
        usuario.setApellido(usuarioDto.getApellido());
        usuario.setActivo(usuarioDto.getActivo() != null ? usuarioDto.getActivo() : usuario.isActivo());
        
        // Solo actualizar rol si se proporciona
        if (usuarioDto.getRolTipo() != null && !usuarioDto.getRolTipo().isEmpty()) {
            Rol rol = rolRepository.findByTipoRol(usuarioDto.getRolTipo())
                    .orElseThrow(() -> new RuntimeException("Rol no encontrado con tipo: " + usuarioDto.getRolTipo()));
            usuario.setRol(rol);
        }
        
        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return convertToDto(updatedUsuario);
    }

    @Override
    public void deleteUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + id));
        usuarioRepository.delete(usuario);
    }

    @Override
    public boolean isOwner(Long id, String username) {
        return usuarioRepository.findById(id)
                .map(usuario -> usuario.getUsername().equals(username))
                .orElse(false);
    }

    @Override
    public UsuarioDto getUsuarioByUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con username: " + username));
        return convertToDto(usuario);
    }
    
    private UsuarioDto convertToDto(Usuario usuario) {
        UsuarioDto dto = new UsuarioDto();
        dto.setId(usuario.getIdUsuario());
        dto.setUsername(usuario.getUsername());
        dto.setEmail(usuario.getEmail());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setActivo(usuario.isActivo());
        dto.setFechaCreacion(usuario.getFechaCreacion());
        dto.setRolTipo(usuario.getRol() != null ? usuario.getRol().getTipoRol() : null);
        // No incluir password en el DTO por seguridad
        return dto;
    }
}