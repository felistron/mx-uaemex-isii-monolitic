package mx.uaemex.fi.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import mx.uaemex.fi.logic.service.CustomUserDetailsService;
import mx.uaemex.fi.logic.service.JwtService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtService jwtService;
    private final CustomUserDetailsService userDetailsService;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return path.startsWith("/auth/") ||
                path.startsWith("/css/") ||
                path.startsWith("/js/") ||
                path.startsWith("/images/") ||
                path.equals("/favicon.ico");
    }

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        try {
            var jwt = getJwtFromCookies(request.getCookies());
            if (Objects.isNull(jwt)) {
                filterChain.doFilter(request, response);
                return;
            }

            if (jwtService.validateToken(jwt)) {
                var rfc = jwtService.getRfcFromToken(jwt);

                var user = userDetailsService.loadUserByUsername(rfc);

                var authToken = new UsernamePasswordAuthenticationToken(
                        user.getUsername(),
                        null,
                        user.getAuthorities());

                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            } else {
                response.sendRedirect("/auth/login?tokenError=true");
                return;
            }
        } catch (Exception e) {
            logger.error("Error al validar el token", e);
            response.sendRedirect("/auth/login?tokenError=true");
            return;
        }

        filterChain.doFilter(request, response);
    }

    private String getJwtFromCookies(Cookie[] cookies) {
        if (cookies == null || cookies.length == 0) return null;
        var accessCookie = Arrays.stream(cookies)
                .filter(cookie -> "access_token".equals(cookie.getName()))
                .findFirst()
                .orElse(null);
        if (Objects.isNull(accessCookie)) return null;
        return accessCookie.getValue();
    }
}
