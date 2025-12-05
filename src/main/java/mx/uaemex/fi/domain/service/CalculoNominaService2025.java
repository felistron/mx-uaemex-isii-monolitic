package mx.uaemex.fi.domain.service;

import org.springframework.stereotype.Service;

/**
 * Datos obtenidos en la sección A.V de las tarifas aplicables a pagos provisionales, retenciones y cálculo del ISR, en el Anexo no. 8 de
 * la Resolución Miscelánea Fiscal 2025 que se puede consultar en la página <a href="https://www.sat.gob.mx/minisitio/NormatividadRMFyRGCE/documentos2025/rmf/anexos/Anexo8_RMF2025-30122024.pdf">oficial del SAT</a>.
 */
@Service
public class CalculoNominaService2025 implements CalculoNominaService {
    @Override
    public Float calcularCuotaFija(Float salarioBruto) {
        if (salarioBruto >= 0.01 && salarioBruto <= 746.04) {
            return 0.00f;
        }
        else if (salarioBruto >= 746.05 && salarioBruto <= 6332.05) {
            return 14.32f;
        }
        else if (salarioBruto >= 6332.06 && salarioBruto <= 11128.01) {
            return 371.83f;
        }
        else if (salarioBruto >= 11128.02 && salarioBruto <= 12935.82) {
            return 893.63f;
        }
        else if (salarioBruto >= 12935.83 && salarioBruto <= 15487.71) {
            return 1182.88f;
        }
        else if (salarioBruto >= 15487.72 && salarioBruto <= 31236.49) {
            return 1640.18f;
        }
        else if (salarioBruto >= 31236.50 && salarioBruto <= 49233.00) {
            return 5004.12f;
        }
        else if (salarioBruto >= 49233.01 && salarioBruto <= 93993.90) {
            return 9236.89f;
        }
        else if (salarioBruto >= 93993.91 && salarioBruto <= 125325.20) {
            return 22665.17f;
        }
        else if (salarioBruto >= 125325.21 && salarioBruto <= 375975.61) {
            return 32691.18f;
        }
        else {
            return 117912.32f;
        }
    }

    @Override
    public Float calcularExcedente(Float salarioBruto) {
        if (salarioBruto >= 0.01 && salarioBruto <= 746.04) {
            return salarioBruto - 0.01f;
        }
        else if (salarioBruto >= 746.05 && salarioBruto <= 6332.05) {
            return salarioBruto - 746.05f;
        }
        else if (salarioBruto >= 6332.06 && salarioBruto <= 11128.01) {
            return salarioBruto - 6332.06f;
        }
        else if (salarioBruto >= 11128.02 && salarioBruto <= 12935.82) {
            return salarioBruto - 11128.02f;
        }
        else if (salarioBruto >= 12935.83 && salarioBruto <= 15487.71) {
            return salarioBruto - 12935.83f;
        }
        else if (salarioBruto >= 15487.72 && salarioBruto <= 31236.49) {
            return salarioBruto - 15487.72f;
        }
        else if (salarioBruto >= 31236.50 && salarioBruto <= 49233.00) {
            return salarioBruto - 31236.50f;
        }
        else if (salarioBruto >= 49233.01 && salarioBruto <= 93993.90) {
            return salarioBruto - 49233.01f;
        }
        else if (salarioBruto >= 93993.91 && salarioBruto <= 125325.20) {
            return salarioBruto - 93993.91f;
        }
        else if (salarioBruto >= 125325.21 && salarioBruto <= 375975.61) {
            return salarioBruto - 125325.21f;
        }
        else {
            return salarioBruto - 375975.62f;
        }
    }

    @Override
    public Float calcularPorcentaje(Float salarioBruto) {
        if (salarioBruto >= 0.01 && salarioBruto <= 746.04) {
            return 0.0192f;
        }
        else if (salarioBruto >= 746.05 && salarioBruto <= 6332.05) {
            return 0.0640f;
        }
        else if (salarioBruto >= 6332.06 && salarioBruto <= 11128.01) {
            return 0.1088f;
        }
        else if (salarioBruto >= 11128.02 && salarioBruto <= 12935.82) {
            return 0.1600f;
        }
        else if (salarioBruto >= 12935.83 && salarioBruto <= 15487.71) {
            return 0.1792f;
        }
        else if (salarioBruto >= 15487.72 && salarioBruto <= 31236.49) {
            return 0.2136f;
        }
        else if (salarioBruto >= 31236.50 && salarioBruto <= 49233.00) {
            return 0.2352f;
        }
        else if (salarioBruto >= 49233.01 && salarioBruto <= 93993.90) {
            return 0.3000f;
        }
        else if (salarioBruto >= 93993.91 && salarioBruto <= 125325.20) {
            return 0.3200f;
        }
        else if (salarioBruto >= 125325.21 && salarioBruto <= 375975.61) {
            return 0.3400f;
        }
        else {
            return 0.3500f;
        }
    }
}
