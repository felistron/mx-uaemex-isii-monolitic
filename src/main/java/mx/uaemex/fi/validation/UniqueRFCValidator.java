package mx.uaemex.fi.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.repository.EmpleadoRepository;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UniqueRFCValidator implements ConstraintValidator<UniqueRFC, String> {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        if (s == null || s.isBlank()) {
            return true;
        }
        return !empleadoRepository.existsByRfc(s);
    }
}
