package mx.uaemex.fi.util;

import mx.uaemex.fi.persistence.model.Acceso;
import mx.uaemex.fi.persistence.model.Empleado;
import mx.uaemex.fi.persistence.model.Nomina;

import java.time.LocalDate;
import java.util.ArrayList;

/**
 * Clase de utilidad para crear datos de prueba reutilizables.
 * Proporciona métodos factory para crear instancias de objetos de dominio
 * con datos de prueba consistentes.
 */
public class TestDataBuilder {

    // Constantes para datos de prueba
    public static final String TEST_RFC = "AAAA012345XXX";
    public static final String TEST_RFC_2 = "BBBB987654YYY";
    public static final String TEST_NOMBRE = "JUAN";
    public static final String TEST_APELLIDOS = "PEREZ LOPEZ";
    public static final String TEST_CORREO = "juan.perez@test.com";
    public static final String TEST_CORREO_2 = "maria.lopez@test.com";
    public static final String TEST_PASSWORD = "Qwertyuiop1*";
    public static final String TEST_HASHED_PASSWORD = "$2a$10$dummyHashedPasswordForTesting";

    // Constantes para datos de nómina
    public static final Float TEST_SALARIO_MINIMO = 500.00f;
    public static final Float TEST_SALARIO_RANGO_2 = 5000.00f;
    public static final Float TEST_SALARIO_RANGO_3 = 10000.00f;
    public static final Float TEST_SALARIO_ALTO = 50000.00f;

    /**
     * Crea un empleado de prueba básico sin acceso ni nóminas
     */
    public static Empleado crearEmpleado() {
        return Empleado.builder()
                .id(1)
                .rfc(TEST_RFC)
                .nombre(TEST_NOMBRE)
                .apellidos(TEST_APELLIDOS)
                .correo(TEST_CORREO)
                .nominas(new ArrayList<>())
                .build();
    }

    /**
     * Crea un empleado de prueba con ID personalizado
     */
    public static Empleado crearEmpleado(Integer id) {
        return Empleado.builder()
                .id(id)
                .rfc(TEST_RFC)
                .nombre(TEST_NOMBRE)
                .apellidos(TEST_APELLIDOS)
                .correo(TEST_CORREO)
                .nominas(new ArrayList<>())
                .build();
    }

    /**
     * Crea un empleado de prueba con todos los campos personalizados
     */
    public static Empleado crearEmpleado(Integer id, String rfc, String nombre, String apellidos, String correo) {
        return Empleado.builder()
                .id(id)
                .rfc(rfc)
                .nombre(nombre)
                .apellidos(apellidos)
                .correo(correo)
                .nominas(new ArrayList<>())
                .build();
    }

    /**
     * Crea un empleado administrador con acceso
     */
    public static Empleado crearEmpleadoAdministrador() {
        Empleado empleado = crearEmpleado();
        Acceso acceso = crearAcceso(empleado);
        empleado.setAcceso(acceso);
        return empleado;
    }

    /**
     * Crea un empleado administrador con ID personalizado
     */
    public static Empleado crearEmpleadoAdministrador(Integer id) {
        Empleado empleado = crearEmpleado(id);
        Acceso acceso = crearAcceso(empleado);
        empleado.setAcceso(acceso);
        return empleado;
    }

    /**
     * Crea un objeto Acceso de prueba
     */
    public static Acceso crearAcceso(Empleado empleado) {
        return Acceso.builder()
                .id(1)
                .empleado(empleado)
                .hashedPassword(TEST_HASHED_PASSWORD)
                .build();
    }

    /**
     * Crea una nómina de prueba con salario por defecto
     */
    public static Nomina crearNomina(Empleado empleado) {
        return crearNomina(empleado, TEST_SALARIO_RANGO_2);
    }

    /**
     * Crea una nómina de prueba con salario personalizado
     */
    public static Nomina crearNomina(Empleado empleado, Float salario) {
        return Nomina.builder()
                .id(1)
                .empleado(empleado)
                .salarioBruto(salario)
                .excedenteSobreLimiteInferior(salario - 746.05f)
                .cuotaFija(14.32f)
                .porcentajeSobreExcedente(0.0640f)
                .periodoInicio(LocalDate.of(2025, 1, 1))
                .periodoFin(LocalDate.of(2025, 1, 15))
                .build();
    }

    /**
     * Crea una nómina de prueba con todos los campos personalizados
     */
    public static Nomina crearNomina(Integer id, Empleado empleado, Float salario,
                                     Float excedente, Float cuotaFija, Float porcentaje,
                                     LocalDate inicio, LocalDate fin) {
        return Nomina.builder()
                .id(id)
                .empleado(empleado)
                .salarioBruto(salario)
                .excedenteSobreLimiteInferior(excedente)
                .cuotaFija(cuotaFija)
                .porcentajeSobreExcedente(porcentaje)
                .periodoInicio(inicio)
                .periodoFin(fin)
                .build();
    }

    /**
     * Crea una lista de empleados de prueba
     */
    public static java.util.List<Empleado> crearListaEmpleados() {
        java.util.List<Empleado> empleados = new ArrayList<>();
        empleados.add(crearEmpleado(1));
        empleados.add(crearEmpleado(2, TEST_RFC_2, "MARIA", "LOPEZ GARCIA", TEST_CORREO_2));
        return empleados;
    }
}

