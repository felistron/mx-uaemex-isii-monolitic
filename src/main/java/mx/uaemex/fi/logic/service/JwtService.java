package mx.uaemex.fi.logic.service;

public interface JwtService {
    String generateToken(String rfc);
    boolean validateToken(String token);
    String getRfcFromToken(String token);
    Long getExpirationTime();
}
