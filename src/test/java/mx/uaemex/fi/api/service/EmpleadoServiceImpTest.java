package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.exception.NotFoundException;
import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import mx.uaemex.fi.api.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static mx.uaemex.fi.api.util.TestDataBuilder.TEST_RFC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para EmpleadoServiceImp
 * Verifica la búsqueda y obtención de empleados
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("EmpleadoServiceImp - Pruebas de servicio de empleado")
class EmpleadoServiceImpTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @InjectMocks
    private EmpleadoServiceImp empleadoService;

    private Empleado empleado;

    @BeforeEach
    void setUp() {
        empleado = TestDataBuilder.crearEmpleado();
    }

    // ==================== PRUEBAS DE BÚSQUEDA POR RFC ====================

    @Test
    @DisplayName("buscarPorRFC - RFC existente retorna empleado")
    void buscarPorRFC_rfcExistente_retornaEmpleado() {
        // Arrange
        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);

        // Act
        Empleado resultado = empleadoService.buscarPorRFC(TEST_RFC);

        // Assert
        assertNotNull(resultado, "El empleado no debe ser null");
        assertEquals(TEST_RFC, resultado.getRfc(), "El RFC debe coincidir");
        assertEquals(empleado.getNombre(), resultado.getNombre());
        assertEquals(empleado.getApellidos(), resultado.getApellidos());
        assertEquals(empleado.getCorreo(), resultado.getCorreo());

        verify(empleadoRepository).existsByRfc(TEST_RFC);
        verify(empleadoRepository).findByRfc(TEST_RFC);
    }

    @Test
    @DisplayName("buscarPorRFC - RFC inexistente lanza NotFoundException")
    void buscarPorRFC_rfcInexistente_lanzaNotFoundException() {
        // Arrange
        String rfcInexistente = "XXXX999999XXX";
        when(empleadoRepository.existsByRfc(rfcInexistente)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> empleadoService.buscarPorRFC(rfcInexistente));

        assertEquals("Empleado no encontrado", exception.getMessage());
        verify(empleadoRepository).existsByRfc(rfcInexistente);
        verify(empleadoRepository, never()).findByRfc(anyString());
    }

    // ==================== PRUEBAS DE OBTENER TODOS ====================

    @Test
    @DisplayName("obtenerTodos - Empleados existen retorna lista")
    void obtenerTodos_empleadosExisten_retornaLista() {
        // Arrange
        List<Empleado> empleados = TestDataBuilder.crearListaEmpleados();
        when(empleadoRepository.findAll()).thenReturn(empleados);

        // Act
        List<Empleado> resultado = empleadoService.obtenerTodos();

        // Assert
        assertNotNull(resultado, "La lista no debe ser null");
        assertFalse(resultado.isEmpty(), "La lista no debe estar vacía");
        assertEquals(2, resultado.size(), "Debe retornar 2 empleados");

        verify(empleadoRepository).findAll();
    }

    @Test
    @DisplayName("obtenerTodos - Sin empleados retorna lista vacía")
    void obtenerTodos_sinEmpleados_retornaListaVacia() {
        // Arrange
        when(empleadoRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Empleado> resultado = empleadoService.obtenerTodos();

        // Assert
        assertNotNull(resultado, "La lista no debe ser null");
        assertTrue(resultado.isEmpty(), "La lista debe estar vacía");

        verify(empleadoRepository).findAll();
    }
}

