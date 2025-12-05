package mx.uaemex.fi.presentation.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import mx.uaemex.fi.presentation.dto.NominaRequest;
import mx.uaemex.fi.domain.exception.NotFoundException;
import mx.uaemex.fi.domain.service.EmpleadoService;
import mx.uaemex.fi.domain.service.NominaService;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
@Slf4j
public class AdminController {
    private final EmpleadoService empleadoService;
    private final NominaService nominaService;

    @GetMapping("/dashboard")
    public String dashboardPage(Authentication auth, Model model) {
        model.addAttribute("admin", auth.getName());
        var empleados = empleadoService.obtenerTodos();
        model.addAttribute("empleados", empleados);
        return "admin/dashboard";
    }

    @GetMapping("/nomina/consultar")
    public String verNomina(Model model, Authentication auth, @RequestParam String rfc) {
        model.addAttribute("admin", auth.getName());
        var empleado = empleadoService.buscarPorRFC(rfc);
        model.addAttribute("empleado", empleado);
        return "admin/ver_nomina";
    }

    @GetMapping("/nomina/registrar")
    public String nominaPage(Model model, Authentication auth, @RequestParam String rfc) {
        model.addAttribute("admin", auth.getName());
        var empleado = empleadoService.buscarPorRFC(rfc);
        model.addAttribute("empleado", empleado);
        return "admin/nomina";
    }

    @PostMapping(value = "/nomina/registrar", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public String calcularNomina(Model model, Authentication auth, @Valid NominaRequest req, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            var empleado = empleadoService.buscarPorRFC(req.rfc());

            model.addAttribute("admin", auth.getName());
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

    @PostMapping("/nomina/{id}/eliminar")
    public String eliminarNomina(@PathVariable Integer id) {
        var nomina = nominaService.obtenerNomina(id);
        var rfc = nomina.getEmpleado().getRfc();
        nominaService.eliminarNomina(id);
        return "redirect:/admin/nomina/consultar?rfc=" + rfc;
    }

    @GetMapping("/error/notfound")
    public String empleadoNotFound(Model model, Authentication auth) {
        model.addAttribute("admin", auth.getName());
        return "admin/not_found";
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public String handleMissingParams() {
        return "redirect:/admin/error/notfound";
    }

    @ExceptionHandler(NotFoundException.class)
    public String handleNotFoundException() {
        return "redirect:/admin/error/notfound";
    }
}
