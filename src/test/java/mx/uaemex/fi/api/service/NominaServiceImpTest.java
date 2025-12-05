package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.dto.NominaRequest;
import mx.uaemex.fi.api.exception.NotFoundException;
import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.model.Nomina;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import mx.uaemex.fi.api.repository.NominaRepository;
import mx.uaemex.fi.api.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Optional;

import static mx.uaemex.fi.api.util.TestDataBuilder.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para NominaServiceImp
 * Verifica la generación, eliminación y obtención de nóminas
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("NominaServiceImp - Pruebas de servicio de nómina")
class NominaServiceImpTest {

    @Mock
    private NominaRepository nominaRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private CalculoNominaService calculoNominaService;

    @InjectMocks
    private NominaServiceImp nominaService;

    private Empleado empleado;
    private NominaRequest nominaRequest;
    private Nomina nomina;

    @BeforeEach
    void setUp() {
        empleado = TestDataBuilder.crearEmpleado();
        nominaRequest = new NominaRequest(
                TEST_RFC,
                TEST_SALARIO_RANGO_2,
                LocalDate.of(2025, 1, 1),
                LocalDate.of(2025, 1, 15)
        );
        nomina = TestDataBuilder.crearNomina(empleado, TEST_SALARIO_RANGO_2);
    }

    // ==================== PRUEBAS DE GENERACIÓN DE NÓMINA ====================

    @Test
    @DisplayName("generarNomina - Empleado existente crea nómina")
    void generarNomina_empleadoExistente_creaNomina() {
        // Arrange
        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);
        when(calculoNominaService.calcularExcedente(TEST_SALARIO_RANGO_2)).thenReturn(2253.95f);
        when(calculoNominaService.calcularCuotaFija(TEST_SALARIO_RANGO_2)).thenReturn(14.32f);
        when(calculoNominaService.calcularPorcentaje(TEST_SALARIO_RANGO_2)).thenReturn(0.0640f);
        when(empleadoRepository.save(any(Empleado.class))).thenReturn(empleado);

        // Act
        nominaService.generarNomina(nominaRequest);

        // Assert
        verify(empleadoRepository).existsByRfc(TEST_RFC);
        verify(empleadoRepository).findByRfc(TEST_RFC);
        verify(calculoNominaService).calcularExcedente(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularCuotaFija(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularPorcentaje(TEST_SALARIO_RANGO_2);
        verify(empleadoRepository).save(empleado);
    }

    @Test
    @DisplayName("generarNomina - Empleado inexistente lanza NotFoundException")
    void generarNomina_empleadoInexistente_lanzaNotFoundException() {
        // Arrange
        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> nominaService.generarNomina(nominaRequest));

        assertEquals("Empleado no encontrado", exception.getMessage());
        verify(empleadoRepository).existsByRfc(TEST_RFC);
        verify(empleadoRepository, never()).findByRfc(anyString());
        verify(empleadoRepository, never()).save(any());
    }

    @Test
    @DisplayName("generarNomina - Usa CalculoNominaService correctamente")
    void generarNomina_calculosCorrectos_usaCalculoNominaService() {
        // Arrange
        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);
        when(calculoNominaService.calcularExcedente(TEST_SALARIO_RANGO_2)).thenReturn(2253.95f);
        when(calculoNominaService.calcularCuotaFija(TEST_SALARIO_RANGO_2)).thenReturn(14.32f);
        when(calculoNominaService.calcularPorcentaje(TEST_SALARIO_RANGO_2)).thenReturn(0.0640f);

        // Act
        nominaService.generarNomina(nominaRequest);

        // Assert
        verify(calculoNominaService).calcularExcedente(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularCuotaFija(TEST_SALARIO_RANGO_2);
        verify(calculoNominaService).calcularPorcentaje(TEST_SALARIO_RANGO_2);
    }

    @Test
    @DisplayName("generarNomina - Agrega nómina a la lista del empleado")
    void generarNomina_agregaNominaAEmpleado_actualizaRelacion() {
        // Arrange
        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);
        when(calculoNominaService.calcularExcedente(TEST_SALARIO_RANGO_2)).thenReturn(2253.95f);
        when(calculoNominaService.calcularCuotaFija(TEST_SALARIO_RANGO_2)).thenReturn(14.32f);
        when(calculoNominaService.calcularPorcentaje(TEST_SALARIO_RANGO_2)).thenReturn(0.0640f);

        int nominasAnteriores = empleado.getNominas().size();

        // Act
        nominaService.generarNomina(nominaRequest);

        // Assert
        assertEquals(nominasAnteriores + 1, empleado.getNominas().size(),
                "Debe agregarse una nómina a la lista del empleado");
    }

    @Test
    @DisplayName("generarNomina - Guarda empleado en repositorio")
    void generarNomina_guardaEmpleado_llamaSaveRepository() {
        // Arrange
        when(empleadoRepository.existsByRfc(TEST_RFC)).thenReturn(true);
        when(empleadoRepository.findByRfc(TEST_RFC)).thenReturn(empleado);
        when(calculoNominaService.calcularExcedente(TEST_SALARIO_RANGO_2)).thenReturn(2253.95f);
        when(calculoNominaService.calcularCuotaFija(TEST_SALARIO_RANGO_2)).thenReturn(14.32f);
        when(calculoNominaService.calcularPorcentaje(TEST_SALARIO_RANGO_2)).thenReturn(0.0640f);

        // Act
        nominaService.generarNomina(nominaRequest);

        // Assert
        ArgumentCaptor<Empleado> empleadoCaptor = ArgumentCaptor.forClass(Empleado.class);
        verify(empleadoRepository).save(empleadoCaptor.capture());

        Empleado empleadoGuardado = empleadoCaptor.getValue();
        assertEquals(TEST_RFC, empleadoGuardado.getRfc());
        assertFalse(empleadoGuardado.getNominas().isEmpty());
    }

    // ==================== PRUEBAS DE ELIMINACIÓN DE NÓMINA ====================

    @Test
    @DisplayName("eliminarNomina - ID existente elimina correctamente")
    void eliminarNomina_idExistente_eliminaCorrectamente() {
        // Arrange
        Integer nominaId = 1;
        when(nominaRepository.existsById(nominaId)).thenReturn(true);

        // Act
        nominaService.eliminarNomina(nominaId);

        // Assert
        verify(nominaRepository).existsById(nominaId);
        verify(nominaRepository).deleteById(nominaId);
    }

    @Test
    @DisplayName("eliminarNomina - ID inexistente lanza NotFoundException")
    void eliminarNomina_idInexistente_lanzaNotFoundException() {
        // Arrange
        Integer nominaId = 999;
        when(nominaRepository.existsById(nominaId)).thenReturn(false);

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> nominaService.eliminarNomina(nominaId));

        assertEquals("Nómina no encontrada", exception.getMessage());
        verify(nominaRepository).existsById(nominaId);
        verify(nominaRepository, never()).deleteById(anyInt());
    }

    // ==================== PRUEBAS DE OBTENCIÓN DE NÓMINA ====================

    @Test
    @DisplayName("obtenerNomina - ID existente retorna nómina")
    void obtenerNomina_idExistente_retornaNomina() {
        // Arrange
        Integer nominaId = 1;
        when(nominaRepository.findById(nominaId)).thenReturn(Optional.of(nomina));

        // Act
        Nomina resultado = nominaService.obtenerNomina(nominaId);

        // Assert
        assertNotNull(resultado);
        assertEquals(nomina.getId(), resultado.getId());
        assertEquals(nomina.getSalarioBruto(), resultado.getSalarioBruto());
        verify(nominaRepository).findById(nominaId);
    }

    @Test
    @DisplayName("obtenerNomina - ID inexistente lanza NotFoundException")
    void obtenerNomina_idInexistente_lanzaNotFoundException() {
        // Arrange
        Integer nominaId = 999;
        when(nominaRepository.findById(nominaId)).thenReturn(Optional.empty());

        // Act & Assert
        NotFoundException exception = assertThrows(NotFoundException.class, () -> nominaService.obtenerNomina(nominaId));

        assertEquals("Nómina no encontrada", exception.getMessage());
        verify(nominaRepository).findById(nominaId);
    }
}

