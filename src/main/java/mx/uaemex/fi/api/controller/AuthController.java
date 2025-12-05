package mx.uaemex.fi.api.controller;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.uaemex.fi.api.dto.*;
import mx.uaemex.fi.api.exception.InvalidCredentialsException;
import mx.uaemex.fi.api.service.AuthService;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {
    private final AuthService authService;

    @GetMapping("/registrar")
    public String registrarPage() {
        return "auth/register";
    }

    @PostMapping(
            value = "/registrar",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String registrar(Model model, @Valid RegisterRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            // Errores de validacion de campos (400)
            model.addAttribute("error", "Uno o más campos contienen errores");

            bindingResult.getFieldErrors().forEach(error -> model.addAttribute(error.getField() + "Error", error.getDefaultMessage()));
        } else {
            var res = authService.register(request);
            model.addAttribute("res", res);
            if (Boolean.TRUE.equals(request.esAdministrador())) {
                model.addAttribute("admin", true);
            }
            return "auth/register";
        }

        model.addAttribute("rfc", request.rfc());
        model.addAttribute("nombre", request.nombre());
        model.addAttribute("apellidos", request.apellidos());
        model.addAttribute("correo", request.correo());
        model.addAttribute("esAdministrador", request.esAdministrador());
        return "auth/register";
    }

    @GetMapping("/login")
    public String loginPage(Model model, @RequestParam(required = false) Boolean tokenError) {
        if (Boolean.TRUE.equals(tokenError)) model.addAttribute("error", "La sesión ha expirado, por favor inicia sesión de nuevo");
        return "auth/login";
    }

    @PostMapping(
            value = "/login",
            consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE
    )
    public String login(HttpServletResponse response, Model model, LoginRequest request) {
        try {
            var res = authService.login(request);
            Cookie cookie = new Cookie("access_token", res.token());
            cookie.setHttpOnly(true);
            cookie.setPath("/");
            cookie.setMaxAge((int) (res.expiresIn()/1000));
            response.addCookie(cookie);
            return "redirect:/admin/dashboard";
        } catch (InvalidCredentialsException e) {
            model.addAttribute("error", e.getMessage());
            return "auth/login";
        }
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        Cookie cookie = new Cookie("access_token", null);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        return "redirect:/auth/login";
    }
}
