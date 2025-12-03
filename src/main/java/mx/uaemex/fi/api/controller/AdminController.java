package mx.uaemex.fi.api.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.uaemex.fi.api.dto.NominaRequest;
import mx.uaemex.fi.api.service.EmpleadoService;
import mx.uaemex.fi.api.service.NominaService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final EmpleadoService empleadoService;
    private final NominaService nominaService;

    @GetMapping("/dashboard")
    public String dashboardPage(Authentication auth, Model model) {
        var empleado = empleadoService.buscarPorRFC(auth.getName());
        model.addAttribute("admin", empleado);
        var empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "admin/dashboard";
    }

    @GetMapping("/empleado/{rfc}/nomina")
    public String verNomina(Model model, Authentication auth, @PathVariable String rfc) {
        var admin = empleadoService.buscarPorRFC(auth.getName());
        model.addAttribute("admin", admin);
        var empleado = empleadoService.buscarPorRFC(rfc);
        model.addAttribute("empleado", empleado);
        return "admin/ver_nomina";
    }

    @GetMapping("/empleado/{rfc}/nomina/registrar")
    public String nominaPage(Model model, Authentication auth, @PathVariable String rfc) {
        var admin = empleadoService.buscarPorRFC(auth.getName());
        model.addAttribute("admin", admin);
        var empleado = empleadoService.buscarPorRFC(rfc);
        model.addAttribute("empleado", empleado);
        return "admin/nomina";
    }

    @PostMapping(value = "/empleado/{rfc}/nomina/registrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String calcularNomina(Model model, @Valid NominaRequest req, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            var admin = empleadoService.buscarPorRFC(req.rfc());
            model.addAttribute("admin", admin);
            var empleado = empleadoService.buscarPorRFC(req.rfc());
            model.addAttribute("empleado", empleado);

            model.addAttribute("error", "Uno o más campos contienen errores");

            bindingResult.getGlobalErrors().forEach(error ->
                    model.addAttribute(error.getObjectName() + "Error", error.getDefaultMessage())
            );

            bindingResult.getFieldErrors().forEach(error ->
                model.addAttribute(error.getField() + "Error", error.getDefaultMessage())
            );

            model.addAttribute("salario", req.salario());
            model.addAttribute("fechaInicio", req.fechaInicio());
            model.addAttribute("fechaFin", req.fechaFin());

            return "admin/nomina";
        }
        nominaService.generarNomina(req);
        return "redirect:/admin/dashboard";
    }

    @PostMapping("/empleado/{rfc}/nomina/{id}/eliminar")
    public String eliminarNomina(@PathVariable String rfc, @PathVariable Integer id) {
        try {
            nominaService.eliminarNomina(id);
        } catch (Exception e) {
            log.error("Error al eliminar nomina", e);
        }
        return "redirect:/admin/empleado/" + rfc + "/nomina";
    }
}
