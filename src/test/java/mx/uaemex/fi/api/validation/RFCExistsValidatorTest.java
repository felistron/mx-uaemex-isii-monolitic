package mx.uaemex.fi.api.validation;

import jakarta.validation.ConstraintValidatorContext;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para RFCExistsValidator
 * Verifica que el RFC exista en el sistema (para referencias)
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("RFCExistsValidator - Validación de RFC existente")
class RFCExistsValidatorTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private ConstraintValidatorContext context;

    @InjectMocks
    private RFCExistsValidator validator;

    @Test
    @DisplayName("isValid - RFC existe retorna true")
    void isValid_rfcExiste_retornaTrue() {
        // Arrange
        String rfc = "AAAA012345XXX";
        when(empleadoRepository.existsByRfc(rfc)).thenReturn(true);

        // Act
        boolean resultado = validator.isValid(rfc, context);

        // Assert
        assertTrue(resultado, "Un RFC que existe debe ser válido");
        verify(empleadoRepository).existsByRfc(rfc);
    }

    @Test
    @DisplayName("isValid - RFC no existe retorna false")
    void isValid_rfcNoExiste_retornaFalse() {
        // Arrange
        String rfc = "XXXX999999XXX";
        when(empleadoRepository.existsByRfc(rfc)).thenReturn(false);

        // Act
        boolean resultado = validator.isValid(rfc, context);

        // Assert
        assertFalse(resultado, "Un RFC que no existe debe ser inválido");
        verify(empleadoRepository).existsByRfc(rfc);
    }

    @Test
    @DisplayName("isValid - RFC null retorna false")
    void isValid_rfcNull_retornaFalse() {
        // Arrange
        when(empleadoRepository.existsByRfc(null)).thenReturn(false);

        // Act
        boolean resultado = validator.isValid(null, context);

        // Assert
        assertFalse(resultado, "RFC null debe ser inválido");
    }
}

