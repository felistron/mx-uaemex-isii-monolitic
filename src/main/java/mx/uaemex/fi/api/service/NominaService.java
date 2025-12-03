package mx.uaemex.fi.api.service;

import mx.uaemex.fi.api.dto.NominaRequest;

public interface NominaService {
    void generarNomina(NominaRequest request);
    void eliminarNomina(Integer id);
}
