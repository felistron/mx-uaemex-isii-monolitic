package mx.uaemex.fi.logic.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.presentation.dto.NominaRequest;

public class PeriodoValidator implements ConstraintValidator<Periodo, NominaRequest> {
    @Override
    public boolean isValid(NominaRequest nominaRequest, ConstraintValidatorContext constraintValidatorContext) {
        if (nominaRequest.fechaInicio() == null || nominaRequest.fechaFin() == null) return false;
        return nominaRequest.fechaInicio().isBefore(nominaRequest.fechaFin());
    }
}
