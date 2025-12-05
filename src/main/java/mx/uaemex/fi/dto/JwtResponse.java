package mx.uaemex.fi.dto;

public record JwtResponse(String token, String type, Long expiresIn) {
}

