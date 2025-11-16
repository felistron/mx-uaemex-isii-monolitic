package mx.uaemex.fi.api.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.cookie;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;

@WebMvcTest(AuthController.class)
class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @Test
    void registrar() throws Exception {
        mockMvc.perform(
                    post("/auth/registrar")
                            .formField("rfc", "EISF040218XXX")
                            .formField("correo", "jferespinosa18@gmail.com")
                            .formField("nombre", "Jose Fernando")
                            .formField("apellidos", "Espinosa Salinas")
                            .formField("password", "123")
                )
                .andExpectAll(
                        model().attribute("a", true),
                        cookie().exists("access_token"),
                        cookie().httpOnly("access_token", true)
                );
    }

    @Test
    void login() {
    }
}