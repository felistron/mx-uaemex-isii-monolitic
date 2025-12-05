package mx.uaemex.fi.domain.service;

import mx.uaemex.fi.domain.exception.NotFoundException;
import mx.uaemex.fi.persistence.model.Empleado;

import java.util.List;

public interface EmpleadoService {
    Empleado buscarPorRFC(String rfc) throws NotFoundException;
    List<Empleado> obtenerTodos();
}
