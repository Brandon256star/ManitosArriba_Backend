package com.example.tp_grupo6.security.config;

import com.example.tp_grupo6.entities.Rol;
import com.example.tp_grupo6.entities.Usuario;
import com.example.tp_grupo6.repositories.RolRepository;
import com.example.tp_grupo6.repositories.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class RoleDataSeeder implements CommandLineRunner {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        // Crear roles por defecto si no existen
        if (!rolRepository.existsByTipoRol("ADMIN")) {
            Rol adminRole = new Rol();
            adminRole.setTipoRol("ADMIN");
            adminRole.setDescripcion("Administrador del sistema con acceso completo");
            rolRepository.save(adminRole);
            System.out.println("✅ Rol ADMIN creado");
        }

        if (!rolRepository.existsByTipoRol("USER")) {
            Rol userRole = new Rol();
            userRole.setTipoRol("USER");
            userRole.setDescripcion("Usuario estándar con acceso básico");
            rolRepository.save(userRole);
            System.out.println("✅ Rol USER creado");
        }
        
        if (!rolRepository.existsByTipoRol("INSTRUCTOR")) {
            Rol instructorRole = new Rol();
            instructorRole.setTipoRol("INSTRUCTOR");
            instructorRole.setDescripcion("Instructor de clases y contenido educativo");
            rolRepository.save(instructorRole);
            System.out.println("✅ Rol INSTRUCTOR creado");
        }

        // Crear usuario administrador por defecto si no existe
        if (!usuarioRepository.existsByUsername("admin")) {
            Rol adminRole = rolRepository.findByTipoRol("ADMIN")
                    .orElseThrow(() -> new RuntimeException("Rol ADMIN no encontrado"));
            
            Usuario admin = new Usuario();
            admin.setUsername("admin");
            admin.setEmail("admin@tpgrupo6.com");
            admin.setPassword(passwordEncoder.encode("admin123"));
            admin.setNombre("Administrador");
            admin.setApellido("Sistema");
            admin.setRol(adminRole);
            admin.setActivo(true);
            admin.setFechaCreacion(LocalDateTime.now());
            
            usuarioRepository.save(admin);
            System.out.println("✅ Usuario administrador creado:");
            System.out.println("   📧 Email: admin@tpgrupo6.com");
            System.out.println("   👤 Username: admin");
            System.out.println("   🔑 Password: admin123");
        }

        // Crear usuario de prueba si no existe
        if (!usuarioRepository.existsByUsername("testuser")) {
            Rol userRole = rolRepository.findByTipoRol("USER")
                    .orElseThrow(() -> new RuntimeException("Rol USER no encontrado"));
            
            Usuario testUser = new Usuario();
            testUser.setUsername("testuser");
            testUser.setEmail("test@tpgrupo6.com");
            testUser.setPassword(passwordEncoder.encode("test123"));
            testUser.setNombre("Usuario");
            testUser.setApellido("Prueba");
            testUser.setRol(userRole);
            testUser.setActivo(true);
            testUser.setFechaCreacion(LocalDateTime.now());
            
            usuarioRepository.save(testUser);
            System.out.println("✅ Usuario de prueba creado:");
            System.out.println("   📧 Email: test@tpgrupo6.com");
            System.out.println("   👤 Username: testuser");
            System.out.println("   🔑 Password: test123");
        }

        System.out.println("🎯 Datos iniciales del sistema cargados correctamente");
        System.out.println("🚀 Sistema listo para usar");
    }
}