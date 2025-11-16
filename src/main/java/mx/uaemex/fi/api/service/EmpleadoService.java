package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.model.Empleado;

public interface EmpleadoService {
    Empleado buscarPorRFC(String rfc);
}
