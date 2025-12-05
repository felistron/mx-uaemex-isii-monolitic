package mx.uaemex.fi.api.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Nomina.
 * Verifica el correcto funcionamiento del modelo de dominio Nomina.
 */
@DisplayName("Pruebas unitarias de Nomina")
class NominaTest {

    @Test
    @DisplayName("Debe crear nómina con builder")
    void debeCrearNominaConBuilder() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 1, 1);
        LocalDate fin = LocalDate.of(2025, 1, 15);
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Juan")
                .apellidos("Pérez García")
                .correo("juan.perez@example.com")
                .build();

        // Act
        Nomina nomina = Nomina.builder()
                .id(1)
                .salarioBruto(10000f)
                .excedenteSobreLimiteInferior(1500f)
                .cuotaFija(500f)
                .porcentajeSobreExcedente(10.88f)
                .periodoInicio(inicio)
                .periodoFin(fin)
                .empleado(empleado)
                .build();

        // Assert
        assertNotNull(nomina);
        assertEquals(1, nomina.getId());
        assertEquals(10000f, nomina.getSalarioBruto());
        assertEquals(1500f, nomina.getExcedenteSobreLimiteInferior());
        assertEquals(500f, nomina.getCuotaFija());
        assertEquals(10.88f, nomina.getPorcentajeSobreExcedente());
        assertEquals(inicio, nomina.getPeriodoInicio());
        assertEquals(fin, nomina.getPeriodoFin());
        assertEquals(empleado, nomina.getEmpleado());
    }

    @Test
    @DisplayName("Debe crear nómina con constructor vacío")
    void debeCrearNominaConConstructorVacio() {
        // Arrange & Act
        Nomina nomina = new Nomina();

        // Assert
        assertNotNull(nomina);
        assertNull(nomina.getId());
        assertNull(nomina.getSalarioBruto());
        assertNull(nomina.getExcedenteSobreLimiteInferior());
        assertNull(nomina.getCuotaFija());
        assertNull(nomina.getPorcentajeSobreExcedente());
        assertNull(nomina.getPeriodoInicio());
        assertNull(nomina.getPeriodoFin());
        assertNull(nomina.getEmpleado());
    }

    @Test
    @DisplayName("Debe crear nómina con todos los argumentos")
    void debeCrearNominaConTodosLosArgumentos() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 2, 1);
        LocalDate fin = LocalDate.of(2025, 2, 15);
        Empleado empleado = Empleado.builder()
                .id(2)
                .rfc("WXYZ987654ABC")
                .nombre("María")
                .apellidos("González López")
                .correo("maria.gonzalez@example.com")
                .build();

        // Act
        Nomina nomina = new Nomina(
                1,
                15000f,
                2000f,
                750f,
                16.00f,
                inicio,
                fin,
                empleado
        );

        // Assert
        assertNotNull(nomina);
        assertEquals(1, nomina.getId());
        assertEquals(15000f, nomina.getSalarioBruto());
        assertEquals(2000f, nomina.getExcedenteSobreLimiteInferior());
        assertEquals(750f, nomina.getCuotaFija());
        assertEquals(16.00f, nomina.getPorcentajeSobreExcedente());
        assertEquals(inicio, nomina.getPeriodoInicio());
        assertEquals(fin, nomina.getPeriodoFin());
        assertEquals(empleado, nomina.getEmpleado());
    }

    @Test
    @DisplayName("Debe verificar equals y hashCode")
    void debeVerificarEqualsYHashCode() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 3, 1);
        LocalDate fin = LocalDate.of(2025, 3, 15);
        Empleado empleado = Empleado.builder().id(1).build();

        Nomina nomina1 = Nomina.builder()
                .id(1)
                .salarioBruto(10000f)
                .excedenteSobreLimiteInferior(1500f)
                .cuotaFija(500f)
                .porcentajeSobreExcedente(10.88f)
                .periodoInicio(inicio)
                .periodoFin(fin)
                .empleado(empleado)
                .build();

        Nomina nomina2 = Nomina.builder()
                .id(1)
                .salarioBruto(10000f)
                .excedenteSobreLimiteInferior(1500f)
                .cuotaFija(500f)
                .porcentajeSobreExcedente(10.88f)
                .periodoInicio(inicio)
                .periodoFin(fin)
                .empleado(empleado)
                .build();

        Nomina nomina3 = Nomina.builder()
                .id(2)
                .salarioBruto(20000f)
                .excedenteSobreLimiteInferior(3000f)
                .cuotaFija(1000f)
                .porcentajeSobreExcedente(16.00f)
                .periodoInicio(inicio)
                .periodoFin(fin)
                .empleado(empleado)
                .build();

        // Assert
        assertEquals(nomina1, nomina2);
        assertNotEquals(nomina1, nomina3);
        assertEquals(nomina1.hashCode(), nomina2.hashCode());
    }

    @Test
    @DisplayName("Debe verificar toString")
    void debeVerificarToString() {
        // Arrange
        LocalDate inicio = LocalDate.of(2025, 4, 1);
        LocalDate fin = LocalDate.of(2025, 4, 15);
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .build();

        Nomina nomina = Nomina.builder()
                .id(1)
                .salarioBruto(12000f)
                .excedenteSobreLimiteInferior(1800f)
                .cuotaFija(600f)
                .porcentajeSobreExcedente(10.88f)
                .periodoInicio(inicio)
                .periodoFin(fin)
                .empleado(empleado)
                .build();

        // Act
        String resultado = nomina.toString();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("12000"));
        assertTrue(resultado.contains("1800"));
        assertTrue(resultado.contains("600"));
        assertTrue(resultado.contains("10.88"));
    }
}

