package mx.uaemex.fi.service;

import mx.uaemex.fi.model.Empleado;
import mx.uaemex.fi.repository.EmpleadoRepository;
import mx.uaemex.fi.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import static mx.uaemex.fi.util.TestDataBuilder.TEST_RFC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para CustomUserDetailsService
 * Verifica la carga de usuarios para Spring Security
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("CustomUserDetailsService - Servicio de UserDetails")
class CustomUserDetailsServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private CustomUserDetailsService userDetailsService;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = TestDataBuilder.crearEmpleadoAdministrador();
    }

    @Test
    @DisplayName("loadUserByUsername - RFC existente retorna UserDetails")
    void loadUserByUsername_rfcExistente_retornaUserDetails() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_RFC);

        // Assert
        assertNotNull(userDetails, "UserDetails no debe ser null");
        assertEquals(TEST_RFC, userDetails.getUsername(), "El username debe ser el RFC");
        assertInstanceOf(UserDetailsAdapter.class, userDetails, "Debe retornar una instancia de UserDetailsAdapter");

        verify(empleadoRepository).findByRfc(TEST_RFC);
    }

    @Test
    @DisplayName("loadUserByUsername - RFC inexistente lanza UsernameNotFoundException")
    void loadUserByUsername_rfcInexistente_lanzaUsernameNotFoundException() {
        // Arrange
        String rfcInexistente = "XXXX999999XXX";
        when(empleadoRepository.findByRfc(rfcInexistente)).thenReturn(null);

        // Act & Assert
        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> userDetailsService.loadUserByUsername(rfcInexistente));

        assertTrue(exception.getMessage().contains("Empleado no encontrado"),
                "El mensaje debe indicar que el empleado no fue encontrado");
        assertTrue(exception.getMessage().contains(rfcInexistente),
                "El mensaje debe incluir el RFC buscado");

        verify(empleadoRepository).findByRfc(rfcInexistente);
    }

    @Test
    @DisplayName("loadUserByUsername - Retorna UserDetailsAdapter correctamente")
    void loadUserByUsername_retornaUserDetailsAdapter() {
        // Arrange
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);

        // Act
        UserDetails userDetails = userDetailsService.loadUserByUsername(TEST_RFC);

        // Assert
        assertInstanceOf(UserDetailsAdapter.class, userDetails,
                "Debe retornar un UserDetailsAdapter");

        UserDetailsAdapter adapter = (UserDetailsAdapter) userDetails;
        assertEquals(TEST_RFC, adapter.getUsername());
        assertNotNull(adapter.getAuthorities());

        verify(empleadoRepository).findByRfc(TEST_RFC);
    }
}

