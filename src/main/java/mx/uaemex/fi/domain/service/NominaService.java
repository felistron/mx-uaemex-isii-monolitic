package mx.uaemex.fi.domain.service;

import mx.uaemex.fi.presentation.dto.NominaRequest;
import mx.uaemex.fi.domain.exception.NotFoundException;
import mx.uaemex.fi.persistence.model.Nomina;

public interface NominaService {
    void generarNomina(NominaRequest request) throws NotFoundException;
    void eliminarNomina(Integer id) throws NotFoundException;
    Nomina obtenerNomina(Integer id) throws NotFoundException;
}
