package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.dto.NominaRequest;
import mx.uaemex.fi.api.exception.NotFoundException;
import mx.uaemex.fi.api.model.Nomina;

public interface NominaService {
    void generarNomina(NominaRequest request) throws NotFoundException;
    void eliminarNomina(Integer id) throws NotFoundException;
    Nomina obtenerNomina(Integer id) throws NotFoundException;
}
