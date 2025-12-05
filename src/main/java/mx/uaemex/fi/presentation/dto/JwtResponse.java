package mx.uaemex.fi.presentation.dto;

public record JwtResponse(String token, String type, Long expiresIn) {
}

