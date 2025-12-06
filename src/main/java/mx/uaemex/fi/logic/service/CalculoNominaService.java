package mx.uaemex.fi.logic.service;

public interface CalculoNominaService {
    Float calcularCuotaFija(Float salarioBruto);
    Float calcularExcedente(Float salarioBruto);
    Float calcularPorcentaje(Float salarioBruto);
}
