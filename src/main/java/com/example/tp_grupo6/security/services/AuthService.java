package com.example.tp_grupo6.security.services;

import com.example.tp_grupo6.entities.Rol;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.RolRepository;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import com.example.tp_grupo6.security.dtos.AuthResponse;
import com.example.tp_grupo6.security.dtos.LoginRequest;
import com.example.tp_grupo6.security.dtos.RegisterRequest;
import com.example.tp_grupo6.security.util.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class AuthService {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse authenticateUser(LoginRequest loginRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsername(),
                    loginRequest.getPassword()
                )
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);
            String jwt = jwtUtils.generateJwtToken(authentication.getName());

            Usuario usuario = usuarioRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

            return new AuthResponse(
                jwt,
                usuario.getIdUsuario(),
                usuario.getUsername(),
                usuario.getEmail(),
                usuario.getRol().getTipoRol()
            );
        } catch (Exception e) {
            return new AuthResponse("Error de autenticación: " + e.getMessage());
        }
    }

    public AuthResponse registerUser(RegisterRequest registerRequest) {
        try {
            // Validar si el usuario ya existe
            if (usuarioRepository.existsByUsername(registerRequest.getUsername())) {
                return new AuthResponse("Error: El nombre de usuario ya existe");
            }

            if (usuarioRepository.existsByEmail(registerRequest.getEmail())) {
                return new AuthResponse("Error: El email ya está en uso");
            }

            // Crear nuevo usuario
            Usuario usuario = new Usuario();
            usuario.setUsername(registerRequest.getUsername());
            usuario.setEmail(registerRequest.getEmail());
            usuario.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            usuario.setNombre(registerRequest.getNombre());
            usuario.setApellido(registerRequest.getApellido());
            usuario.setActivo(true);
            usuario.setFechaCreacion(LocalDateTime.now());

            // Asignar rol
            Rol rol = rolRepository.findByTipoRol(registerRequest.getRolTipo())
                .orElse(rolRepository.findByTipoRol("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado")));
            
            usuario.setRol(rol);

            usuarioRepository.save(usuario);

            return new AuthResponse("Usuario registrado correctamente");
        } catch (Exception e) {
            return new AuthResponse("Error en el registro: " + e.getMessage());
        }
    }
}