package mx.uaemex.fi.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Pruebas unitarias para la entidad Acceso.
 * Verifica el correcto funcionamiento del modelo de dominio Acceso.
 */
@DisplayName("Pruebas unitarias de Acceso")
class AccesoTest {

    @Test
    @DisplayName("Debe crear acceso con builder")
    void debeCrearAccesoConBuilder() {
        // Arrange
        Empleado empleado = Empleado.builder()
                .id(1)
                .rfc("ABCD123456XYZ")
                .nombre("Juan")
                .apellidos("Pérez García")
                .correo("juan.perez@example.com")
                .build();

        // Act
        Acceso acceso = Acceso.builder()
                .id(1)
                .empleado(empleado)
                .hashedPassword("$2a$10$hashedPasswordExample")
                .build();

        // Assert
        assertNotNull(acceso);
        assertEquals(1, acceso.getId());
        assertEquals(empleado, acceso.getEmpleado());
        assertEquals("$2a$10$hashedPasswordExample", acceso.getHashedPassword());
    }

    @Test
    @DisplayName("Debe crear acceso con constructor vacío")
    void debeCrearAccesoConConstructorVacio() {
        // Arrange & Act
        Acceso acceso = new Acceso();

        // Assert
        assertNotNull(acceso);
        assertNull(acceso.getId());
        assertNull(acceso.getEmpleado());
        assertNull(acceso.getHashedPassword());
    }

    @Test
    @DisplayName("Debe verificar equals y hashCode")
    void debeVerificarEqualsYHashCode() {
        // Arrange
        Empleado empleado1 = Empleado.builder().id(1).build();
        Empleado empleado2 = Empleado.builder().id(2).build();

        Acceso acceso1 = Acceso.builder()
                .id(1)
                .empleado(empleado1)
                .hashedPassword("password123")
                .build();

        Acceso acceso2 = Acceso.builder()
                .id(1)
                .empleado(empleado1)
                .hashedPassword("password123")
                .build();

        Acceso acceso3 = Acceso.builder()
                .id(2)
                .empleado(empleado2)
                .hashedPassword("password456")
                .build();

        // Assert
        assertEquals(acceso1, acceso2);
        assertNotEquals(acceso1, acceso3);
        assertEquals(acceso1.hashCode(), acceso2.hashCode());
    }
}

