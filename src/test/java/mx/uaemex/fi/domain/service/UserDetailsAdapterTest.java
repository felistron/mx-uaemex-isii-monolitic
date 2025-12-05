package mx.uaemex.fi.domain.service;

import mx.uaemex.fi.persistence.model.Empleado;
import mx.uaemex.fi.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para UserDetailsAdapter
 * Verifica la adaptación de Empleado a UserDetails de Spring Security
 */
@DisplayName("UserDetailsAdapter - Adaptador de UserDetails")
class UserDetailsAdapterTest {

    private UserDetailsAdapter adapterNormal;
    private UserDetailsAdapter adapterAdmin;

    @BeforeEach
    void setUp() {
        Empleado empleadoNormal = TestDataBuilder.crearEmpleado();
        Empleado empleadoAdmin = TestDataBuilder.crearEmpleadoAdministrador();
        adapterNormal = new UserDetailsAdapter(empleadoNormal);
        adapterAdmin = new UserDetailsAdapter(empleadoAdmin);
    }

    // ==================== PRUEBAS DE AUTHORITIES ====================

    @Test
    @DisplayName("getAuthorities - Empleado sin acceso retorna solo ROLE_USER")
    void getAuthorities_empleadoSinAcceso_retornaRoleUser() {
        // Act
        Collection<? extends GrantedAuthority> authorities = adapterNormal.getAuthorities();

        // Assert
        assertNotNull(authorities, "Las autoridades no deben ser null");
        assertEquals(1, authorities.size(), "Empleado normal debe tener solo 1 rol");
        assertTrue(authorities.stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "Debe tener ROLE_USER");
        assertFalse(authorities.stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")),
                "No debe tener ROLE_ADMIN");
    }

    @Test
    @DisplayName("getAuthorities - Empleado con acceso retorna ROLE_USER y ROLE_ADMIN")
    void getAuthorities_empleadoConAcceso_retornaUserYAdmin() {
        // Act
        Collection<? extends GrantedAuthority> authorities = adapterAdmin.getAuthorities();

        // Assert
        assertNotNull(authorities, "Las autoridades no deben ser null");
        assertEquals(2, authorities.size(), "Administrador debe tener 2 roles");
        assertTrue(authorities.stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_USER")),
                "Debe tener ROLE_USER");
        assertTrue(authorities.stream()
                        .anyMatch(auth -> auth.getAuthority().equals("ROLE_ADMIN")),
                "Debe tener ROLE_ADMIN");
    }

    // ==================== PRUEBAS DE PASSWORD ====================

    @Test
    @DisplayName("getPassword - Empleado con acceso retorna password hasheada")
    void getPassword_empleadoConAcceso_retornaHashedPassword() {
        // Act
        String password = adapterAdmin.getPassword();

        // Assert
        assertNotNull(password, "La contraseña no debe ser null");
        assertEquals(TestDataBuilder.TEST_HASHED_PASSWORD, password,
                "Debe retornar la contraseña hasheada del acceso");
    }

    @Test
    @DisplayName("getPassword - Empleado sin acceso retorna null")
    void getPassword_empleadoSinAcceso_retornaNull() {
        // Act
        String password = adapterNormal.getPassword();

        // Assert
        assertNull(password, "Empleado sin acceso debe retornar null como contraseña");
    }

    // ==================== PRUEBAS DE USERNAME ====================

    @Test
    @DisplayName("getUsername - Retorna el RFC del empleado")
    void getUsername_retornaRfc() {
        // Act
        String username = adapterNormal.getUsername();
        String usernameAdmin = adapterAdmin.getUsername();

        // Assert
        assertEquals(TestDataBuilder.TEST_RFC, username, "Username debe ser el RFC");
        assertEquals(TestDataBuilder.TEST_RFC, usernameAdmin, "Username del admin debe ser el RFC");
    }

    // ==================== PRUEBAS DE ESTADO DE CUENTA ====================

    @Test
    @DisplayName("isAccountNonExpired - Siempre retorna true")
    void isAccountNonExpired_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterNormal.isAccountNonExpired(),
                "La cuenta del empleado normal nunca expira");
        assertTrue(adapterAdmin.isAccountNonExpired(),
                "La cuenta del admin nunca expira");
    }

    @Test
    @DisplayName("isAccountNonLocked - Siempre retorna true")
    void isAccountNonLocked_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterNormal.isAccountNonLocked(),
                "La cuenta del empleado normal nunca está bloqueada");
        assertTrue(adapterAdmin.isAccountNonLocked(),
                "La cuenta del admin nunca está bloqueada");
    }

    @Test
    @DisplayName("isCredentialsNonExpired - Siempre retorna true")
    void isCredentialsNonExpired_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterNormal.isCredentialsNonExpired(),
                "Las credenciales del empleado normal nunca expiran");
        assertTrue(adapterAdmin.isCredentialsNonExpired(),
                "Las credenciales del admin nunca expiran");
    }

    @Test
    @DisplayName("isEnabled - Siempre retorna true")
    void isEnabled_siempreRetornaTrue() {
        // Act & Assert
        assertTrue(adapterNormal.isEnabled(),
                "El empleado normal siempre está habilitado");
        assertTrue(adapterAdmin.isEnabled(),
                "El admin siempre está habilitado");
    }
}

