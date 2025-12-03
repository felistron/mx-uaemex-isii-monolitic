package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado buscarPorRFC(String rfc);
    List<Empleado> obtenerTodos();
}
