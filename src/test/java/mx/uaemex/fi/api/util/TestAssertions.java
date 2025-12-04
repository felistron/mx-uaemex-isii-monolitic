package mx.uaemex.fi.api.util;

import mx.uaemex.fi.api.model.Empleado;
import mx.uaemex.fi.api.model.Nomina;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Clase de utilidad para asserciones personalizadas en pruebas.
 * Proporciona métodos de validación reutilizables para objetos de dominio.
 */
public class TestAssertions {

    /**
     * Verifica que un empleado tiene todos los campos correctos
     */
    public static void assertEmpleadoEquals(Empleado expected, Empleado actual) {
        assertNotNull(actual, "El empleado no debe ser null");
        assertEquals(expected.getId(), actual.getId(), "El ID del empleado debe coincidir");
        assertEquals(expected.getRfc(), actual.getRfc(), "El RFC debe coincidir");
        assertEquals(expected.getNombre(), actual.getNombre(), "El nombre debe coincidir");
        assertEquals(expected.getApellidos(), actual.getApellidos(), "Los apellidos deben coincidir");
        assertEquals(expected.getCorreo(), actual.getCorreo(), "El correo debe coincidir");
    }

    /**
     * Verifica que una nómina tiene todos los campos correctos
     */
    public static void assertNominaEquals(Nomina expected, Nomina actual) {
        assertNotNull(actual, "La nómina no debe ser null");
        assertEquals(expected.getId(), actual.getId(), "El ID de la nómina debe coincidir");
        assertEquals(expected.getSalarioBruto(), actual.getSalarioBruto(), 0.01f,
                     "El salario bruto debe coincidir");
        assertEquals(expected.getExcedenteSobreLimiteInferior(), actual.getExcedenteSobreLimiteInferior(), 0.01f,
                     "El excedente debe coincidir");
        assertEquals(expected.getCuotaFija(), actual.getCuotaFija(), 0.01f,
                     "La cuota fija debe coincidir");
        assertEquals(expected.getPorcentajeSobreExcedente(), actual.getPorcentajeSobreExcedente(), 0.0001f,
                     "El porcentaje debe coincidir");
        assertEquals(expected.getPeriodoInicio(), actual.getPeriodoInicio(),
                     "La fecha de inicio debe coincidir");
        assertEquals(expected.getPeriodoFin(), actual.getPeriodoFin(),
                     "La fecha de fin debe coincidir");
    }

    /**
     * Verifica que un RFC tiene el formato correcto
     */
    public static void assertValidRFC(String rfc) {
        assertNotNull(rfc, "El RFC no debe ser null");
        assertTrue(rfc.matches("^[A-Z&Ñ]{4}[0-9]{6}[A-Z0-9]{3}$"),
                   "El RFC debe tener formato válido: " + rfc);
    }

    /**
     * Verifica que un correo tiene el formato correcto
     */
    public static void assertValidEmail(String email) {
        assertNotNull(email, "El correo no debe ser null");
        assertTrue(email.contains("@"), "El correo debe contener @: " + email);
        assertTrue(email.contains("."), "El correo debe contener un punto: " + email);
    }

    /**
     * Verifica que un valor float está dentro de un rango
     */
    public static void assertFloatInRange(Float value, Float min, Float max) {
        assertNotNull(value, "El valor no debe ser null");
        assertTrue(value >= min, "El valor " + value + " debe ser >= " + min);
        assertTrue(value <= max, "El valor " + value + " debe ser <= " + max);
    }

    /**
     * Verifica que un empleado es administrador (tiene acceso)
     */
    public static void assertEmpleadoEsAdministrador(Empleado empleado) {
        assertNotNull(empleado, "El empleado no debe ser null");
        assertNotNull(empleado.getAcceso(), "El empleado administrador debe tener acceso");
        assertNotNull(empleado.getAcceso().getHashedPassword(),
                     "El acceso debe tener password hasheada");
    }

    /**
     * Verifica que un empleado NO es administrador (no tiene acceso)
     */
    public static void assertEmpleadoNoEsAdministrador(Empleado empleado) {
        assertNotNull(empleado, "El empleado no debe ser null");
        assertNull(empleado.getAcceso(), "El empleado normal no debe tener acceso");
    }
}

