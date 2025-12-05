package mx.uaemex.fi.service;

import mx.uaemex.fi.dto.NominaRequest;
import mx.uaemex.fi.exception.NotFoundException;
import mx.uaemex.fi.model.Nomina;

public interface NominaService {
    void generarNomina(NominaRequest request) throws NotFoundException;
    void eliminarNomina(Integer id) throws NotFoundException;
    Nomina obtenerNomina(Integer id) throws NotFoundException;
}
