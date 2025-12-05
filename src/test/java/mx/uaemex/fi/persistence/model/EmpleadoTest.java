package mx.uaemex.fi.persistence.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Empleado.
 * Verifica el correcto funcionamiento del modelo de dominio Empleado.
 */
@DisplayName("Pruebas unitarias de Empleado")
class EmpleadoTest {

    @Test
    @DisplayName("Debe crear empleado con builder")
    void debeCrearEmpleadoConBuilder() {
        // Arrange & Act
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Juan")
                .apellidos("Pérez García")
                .correo("juan.perez@example.com")
                .build();

        // Assert
        assertNotNull(empleado);
        assertEquals(1, empleado.getId());
        assertEquals("ABCD123456XYZ", empleado.getRfc());
        assertEquals("Juan", empleado.getNombre());
        assertEquals("Pérez García", empleado.getApellidos());
        assertEquals("juan.perez@example.com", empleado.getCorreo());
    }

    @Test
    @DisplayName("Debe crear empleado con constructor vacío")
    void debeCrearEmpleadoConConstructorVacio() {
        // Arrange & Act
        Empleado empleado = new Empleado();

        // Assert
        assertNotNull(empleado);
        assertNull(empleado.getId());
        assertNull(empleado.getRfc());
        assertNull(empleado.getNombre());
        assertNull(empleado.getApellidos());
        assertNull(empleado.getCorreo());
    }

    @Test
    @DisplayName("Debe crear empleado con todos los argumentos")
    void debeCrearEmpleadoConTodosLosArgumentos() {
        // Arrange
        Acceso acceso = new Acceso();
        List<Nomina> nominas = new ArrayList<>();

        // Act
        Empleado empleado = new Empleado(
                1,
                "ABCD123456XYZ",
                "María",
                "González López",
                "maria.gonzalez@example.com",
                acceso,
                nominas
        );

        // Assert
        assertNotNull(empleado);
        assertEquals(1, empleado.getId());
        assertEquals("ABCD123456XYZ", empleado.getRfc());
        assertEquals("María", empleado.getNombre());
        assertEquals("González López", empleado.getApellidos());
        assertEquals("maria.gonzalez@example.com", empleado.getCorreo());
        assertEquals(acceso, empleado.getAcceso());
        assertEquals(nominas, empleado.getNominas());
    }

    @Test
    @DisplayName("Debe establecer y obtener acceso")
    void debeEstablecerYObtenerAcceso() {
        // Arrange
        Empleado empleado = new Empleado();
        Acceso acceso = Acceso.builder()
                .id(1)
                .hashedPassword("hashedPassword123")
                .build();

        // Act
        empleado.setAcceso(acceso);

        // Assert
        assertNotNull(empleado.getAcceso());
        assertEquals(acceso, empleado.getAcceso());
        assertEquals(1, empleado.getAcceso().getId());
    }

    @Test
    @DisplayName("Debe establecer y obtener lista de nóminas")
    void debeEstablecerYObtenerListaDeNominas() {
        // Arrange
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Pedro")
                .apellidos("Martínez Ruiz")
                .correo("pedro.martinez@example.com")
                .build();

        List<Nomina> nominas = new ArrayList<>();
        Nomina nomina1 = Nomina.builder()
                .id(1)
                .salarioBruto(10000f)
                .empleado(empleado)
                .build();
        Nomina nomina2 = Nomina.builder()
                .id(2)
                .salarioBruto(11000f)
                .empleado(empleado)
                .build();
        nominas.add(nomina1);
        nominas.add(nomina2);

        // Act
        empleado.setNominas(nominas);

        // Assert
        assertNotNull(empleado.getNominas());
        assertEquals(2, empleado.getNominas().size());
        assertEquals(nomina1, empleado.getNominas().get(0));
        assertEquals(nomina2, empleado.getNominas().get(1));
    }

    @Test
    @DisplayName("Debe verificar equals y hashCode")
    void debeVerificarEqualsYHashCode() {
        // Arrange
        Empleado empleado1 = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Ana")
                .apellidos("López Flores")
                .correo("ana.lopez@example.com")
                .build();

        Empleado empleado2 = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Ana")
                .apellidos("López Flores")
                .correo("ana.lopez@example.com")
                .build();

        Empleado empleado3 = Empleado.builder()
                .id(2)
                .rfc("WXYZ987654ABC")
                .nombre("Carlos")
                .apellidos("Hernández Silva")
                .correo("carlos.hernandez@example.com")
                .build();

        // Assert
        assertEquals(empleado1, empleado2);
        assertNotEquals(empleado1, empleado3);
        assertEquals(empleado1.hashCode(), empleado2.hashCode());
    }

    @Test
    @DisplayName("Debe verificar toString")
    void debeVerificarToString() {
        // Arrange
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Luis")
                .apellidos("Ramírez Torres")
                .correo("luis.ramirez@example.com")
                .build();

        // Act
        String resultado = empleado.toString();

        // Assert
        assertNotNull(resultado);
        assertTrue(resultado.contains("ABCD123456XYZ"));
        assertTrue(resultado.contains("Luis"));
        assertTrue(resultado.contains("Ramírez Torres"));
        assertTrue(resultado.contains("luis.ramirez@example.com"));
    }
}

