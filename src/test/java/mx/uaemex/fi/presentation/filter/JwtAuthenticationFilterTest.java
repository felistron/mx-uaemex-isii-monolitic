package mx.uaemex.fi.presentation.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mx.uaemex.fi.logic.service.CustomUserDetailsService;
import mx.uaemex.fi.logic.service.JwtService;
import mx.uaemex.fi.logic.service.UserDetailsAdapter;
import mx.uaemex.fi.util.TestDataBuilder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.IOException;

import static mx.uaemex.fi.util.TestDataBuilder.TEST_RFC;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

/**
 * Pruebas unitarias para JwtAuthenticationFilter
 * Verifica el filtro de autenticación basado en JWT
 */
@ExtendWith(MockitoExtension.class)
@DisplayName("JwtAuthenticationFilter - Pruebas de filtro de seguridad JWT")
class JwtAuthenticationFilterTest {

    @Mock
    private JwtService jwtService;

    @Mock
    private CustomUserDetailsService userDetailsService;

    @Mock
    private HttpServletRequest request;

    @Mock
    private HttpServletResponse response;

    @Mock
    private FilterChain filterChain;

    @InjectMocks
    private JwtAuthenticationFilter jwtAuthenticationFilter;

    private static final String TEST_TOKEN = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.test";
    private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        // Limpiar el contexto de seguridad antes de cada prueba
        SecurityContextHolder.clearContext();

        // Crear UserDetails de prueba
        userDetails = new UserDetailsAdapter(TestDataBuilder.crearEmpleadoAdministrador());
    }

    // ==================== PRUEBAS DE shouldNotFilter ====================

    @Test
    @DisplayName("shouldNotFilter - Ruta /auth/ retorna true")
    void shouldNotFilter_rutaAuth_retornaTrue() {
        // Arrange
        when(request.getServletPath()).thenReturn("/auth/login");

        // Act
        boolean resultado = jwtAuthenticationFilter.shouldNotFilter(request);

        // Assert
        assertTrue(resultado, "Las rutas /auth/* no deben ser filtradas");
    }

    @Test
    @DisplayName("shouldNotFilter - Recursos estáticos retornan true")
    void shouldNotFilter_recursosEstaticos_retornaTrue() {
        // Arrange & Act & Assert
        when(request.getServletPath()).thenReturn("/css/style.css");
        assertTrue(jwtAuthenticationFilter.shouldNotFilter(request), "CSS debe estar excluido");

        when(request.getServletPath()).thenReturn("/js/script.js");
        assertTrue(jwtAuthenticationFilter.shouldNotFilter(request), "JS debe estar excluido");

        when(request.getServletPath()).thenReturn("/images/logo.png");
        assertTrue(jwtAuthenticationFilter.shouldNotFilter(request), "Imágenes deben estar excluidas");

        when(request.getServletPath()).thenReturn("/favicon.ico");
        assertTrue(jwtAuthenticationFilter.shouldNotFilter(request), "Favicon debe estar excluido");
    }

    @Test
    @DisplayName("shouldNotFilter - Ruta admin retorna false")
    void shouldNotFilter_rutaAdmin_retornaFalse() {
        // Arrange
        when(request.getServletPath()).thenReturn("/admin/dashboard");

        // Act
        boolean resultado = jwtAuthenticationFilter.shouldNotFilter(request);

        // Assert
        assertFalse(resultado, "Las rutas protegidas deben ser filtradas");
    }

    // ==================== PRUEBAS DE doFilterInternal ====================

    @Test
    @DisplayName("doFilterInternal - Token válido autentica usuario")
    void doFilterInternal_tokenValido_autenticaUsuario() throws ServletException, IOException {
        // Arrange
        Cookie cookie = new Cookie("access_token", TEST_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(jwtService.validateToken(TEST_TOKEN)).thenReturn(true);
        when(jwtService.getRfcFromToken(TEST_TOKEN)).thenReturn(TEST_RFC);
        when(userDetailsService.loadUserByUsername(TEST_RFC)).thenReturn(userDetails);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).validateToken(TEST_TOKEN);
        verify(jwtService).getRfcFromToken(TEST_TOKEN);
        verify(userDetailsService).loadUserByUsername(TEST_RFC);
        verify(filterChain).doFilter(request, response);

        // Verificar que se estableció la autenticación
        assertNotNull(SecurityContextHolder.getContext().getAuthentication(),
                     "El contexto de seguridad debe tener autenticación");
        assertEquals(TEST_RFC, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    }

    @Test
    @DisplayName("doFilterInternal - Sin token continúa sin autenticar")
    void doFilterInternal_sinToken_continuaSinAutenticar() throws ServletException, IOException {
        // Arrange
        when(request.getCookies()).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, never()).validateToken(anyString());
        verify(filterChain).doFilter(request, response);
        assertNull(SecurityContextHolder.getContext().getAuthentication(),
                  "No debe haber autenticación sin token");
    }

    @Test
    @DisplayName("doFilterInternal - Token inválido redirige a login")
    void doFilterInternal_tokenInvalido_redireccionaLogin() throws ServletException, IOException {
        // Arrange
        Cookie cookie = new Cookie("access_token", TEST_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(jwtService.validateToken(TEST_TOKEN)).thenReturn(false);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).validateToken(TEST_TOKEN);
        verify(response).sendRedirect("/auth/login?tokenError=true");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Token expirado redirige a login")
    void doFilterInternal_tokenExpirado_redireccionaLogin() throws ServletException, IOException {
        // Arrange
        Cookie cookie = new Cookie("access_token", TEST_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(jwtService.validateToken(TEST_TOKEN)).thenReturn(false); // Token expirado es inválido

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(response).sendRedirect("/auth/login?tokenError=true");
        verify(filterChain, never()).doFilter(request, response);
    }

    @Test
    @DisplayName("doFilterInternal - Token válido establece SecurityContext")
    void doFilterInternal_tokenValido_estableceSecurityContext() throws ServletException, IOException {
        // Arrange
        Cookie cookie = new Cookie("access_token", TEST_TOKEN);
        when(request.getCookies()).thenReturn(new Cookie[]{cookie});
        when(jwtService.validateToken(TEST_TOKEN)).thenReturn(true);
        when(jwtService.getRfcFromToken(TEST_TOKEN)).thenReturn(TEST_RFC);
        when(userDetailsService.loadUserByUsername(TEST_RFC)).thenReturn(userDetails);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        assertNotNull(SecurityContextHolder.getContext().getAuthentication());
        assertEquals(TEST_RFC, SecurityContextHolder.getContext().getAuthentication().getPrincipal());
        assertTrue(SecurityContextHolder.getContext().getAuthentication().isAuthenticated());
    }

    // ==================== PRUEBAS DE getJwtFromCookies ====================

    @Test
    @DisplayName("getJwtFromCookies - Cookie access_token extrae token")
    void getJwtFromCookies_cookieAccessToken_extraeToken() throws ServletException, IOException {
        // Arrange
        Cookie accessTokenCookie = new Cookie("access_token", TEST_TOKEN);
        Cookie otraCookie = new Cookie("otra_cookie", "valor");
        when(request.getCookies()).thenReturn(new Cookie[]{otraCookie, accessTokenCookie});
        when(jwtService.validateToken(TEST_TOKEN)).thenReturn(true);
        when(jwtService.getRfcFromToken(TEST_TOKEN)).thenReturn(TEST_RFC);
        when(userDetailsService.loadUserByUsername(TEST_RFC)).thenReturn(userDetails);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService).validateToken(TEST_TOKEN);
    }

    @Test
    @DisplayName("getJwtFromCookies - Sin cookies retorna null")
    void getJwtFromCookies_sinCookies_retornaNull() throws ServletException, IOException {
        // Arrange
        when(request.getCookies()).thenReturn(null);

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, never()).validateToken(anyString());
        verify(filterChain).doFilter(request, response);
    }

    @Test
    @DisplayName("getJwtFromCookies - Cookie diferente retorna null")
    void getJwtFromCookies_cookieDiferente_retornaNull() throws ServletException, IOException {
        // Arrange
        Cookie otraCookie = new Cookie("otra_cookie", "valor");
        when(request.getCookies()).thenReturn(new Cookie[]{otraCookie});

        // Act
        jwtAuthenticationFilter.doFilterInternal(request, response, filterChain);

        // Assert
        verify(jwtService, never()).validateToken(anyString());
        verify(filterChain).doFilter(request, response);
    }
}

