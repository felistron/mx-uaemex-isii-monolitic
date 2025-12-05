package mx.uaemex.fi.logic.service;

import mx.uaemex.fi.persistence.model.Empleado;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

public class UserDetailsAdapter implements UserDetails {
    private final Empleado empleado;

    public UserDetailsAdapter(Empleado empleado) {
        this.empleado = empleado;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        var roles = new ArrayList<SimpleGrantedAuthority>();
        roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        if (!Objects.isNull(empleado.getAcceso())) roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        return roles;
    }

    @Override
    public String getPassword() {
        if (Objects.isNull(empleado.getAcceso())) return null;
        return empleado.getAcceso().getHashedPassword();
    }

    @Override
    public String getUsername() {
        return empleado.getRfc();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
