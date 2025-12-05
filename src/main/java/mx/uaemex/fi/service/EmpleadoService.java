package mx.uaemex.fi.service;

import mx.uaemex.fi.exception.NotFoundException;
import mx.uaemex.fi.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado buscarPorRFC(String rfc) throws NotFoundException;
    List<Empleado> obtenerTodos();
}
