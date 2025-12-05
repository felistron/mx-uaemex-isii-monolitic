package mx.uaemex.fi.logic.service;

import mx.uaemex.fi.logic.exception.NotFoundException;
import mx.uaemex.fi.persistence.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado buscarPorRFC(String rfc) throws NotFoundException;
    List<Empleado> obtenerTodos();
}
