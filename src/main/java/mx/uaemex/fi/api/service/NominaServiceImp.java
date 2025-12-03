package mx.uaemex.fi.api.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.api.dto.NominaRequest;
import mx.uaemex.fi.api.model.Nomina;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import mx.uaemex.fi.api.repository.NominaRepository;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NominaServiceImp implements NominaService {
    private final NominaRepository nominaRepository;
    private final EmpleadoRepository empleadoRepository;
    private final CalculoNominaService calculoNominaService;

    @Transactional
    @Override
    public void generarNomina(NominaRequest request) {
        var empleado = empleadoRepository.findByRfc(request.rfc());

        var nomina = Nomina.builder()
                .empleado(empleado)
                .salarioBruto(request.salario())
                .excedenteSobreLimiteInferior(calculoNominaService.calcularExcedente(request.salario()))
                .cuotaFija(calculoNominaService.calcularCuotaFija(request.salario()))
                .porcentajeSobreExcedente(calculoNominaService.calcularPorcentaje(request.salario()))
                .periodoInicio(request.fechaInicio())
                .periodoFin(request.fechaFin())
                .build();

        empleado.getNominas().add(nomina);

        empleadoRepository.save(empleado);
    }

    @Override
    public void eliminarNomina(Integer id) {
        nominaRepository.deleteById(id);
    }
}
