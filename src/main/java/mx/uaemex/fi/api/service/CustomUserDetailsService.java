package mx.uaemex.fi.api.service;

import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.api.repository.EmpleadoRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final EmpleadoRepository empleadoRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var empleado = empleadoRepository.findByRfc(username);
        if (empleado == null) {
            throw new UsernameNotFoundException("Empleado no encontrado: " + username);
        }
        return new UserDetailsAdapter(empleado);
    }
}
