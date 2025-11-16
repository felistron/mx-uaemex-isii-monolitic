package mx.uaemex.fi.api.dto;

public record RegisterRequest(
        String rfc,
        String nombre,
        String apellidos,
        String correo,
        Boolean esAdministrador,
        String password,
        String confirmPassword
) {
}
