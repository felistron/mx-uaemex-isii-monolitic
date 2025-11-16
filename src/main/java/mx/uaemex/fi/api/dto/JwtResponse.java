package mx.uaemex.fi.api.dto;

public record JwtResponse(String token, String type, Long expiresIn) {
}

