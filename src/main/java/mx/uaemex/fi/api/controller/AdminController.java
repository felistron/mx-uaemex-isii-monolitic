package mx.uaemex.fi.api.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.uaemex.fi.api.service.EmpleadoService;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final EmpleadoService empleadoService;

    @RequestMapping("/dashboard")
    public String dashboardPage(Authentication auth, Model model) {
        var empleado = empleadoService.buscarPorRFC(auth.getName());
        model.addAttribute("empleado", empleado);
        return "admin/dashboard";
    }
}
