package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.exception.NotFoundException;
import mx.uaemex.fi.api.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado buscarPorRFC(String rfc) throws NotFoundException;
    List<Empleado> obtenerTodos();
}
