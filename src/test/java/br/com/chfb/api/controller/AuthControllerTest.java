package br.com.chfb.api.controller;

import br.com.chfb.api.dto.req.LoginRequest;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.repository.UsuarioRepository;
import br.com.chfb.api.security.JwtService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean; // O NOVO IMPORTE AQUI
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.when;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AuthController.class, excludeAutoConfiguration = {SecurityAutoConfiguration.class})
@AutoConfigureMockMvc(addFilters = false)
class AuthControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    // Substituído @MockBean por @MockitoBean
    @MockitoBean
    private UsuarioRepository repository;

    @MockitoBean
    private PasswordEncoder encoder;

    @MockitoBean
    private JwtService jwtService;

    @MockitoBean
    private br.com.chfb.api.security.JwtAuthenticationFilter jwtAuthenticationFilter;

    @MockitoBean
    private br.com.chfb.api.security.JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    private Usuario usuarioMock;
    private LocalDateTime dataExpiracaoMock;

    @BeforeEach
    void setUp() {
        usuarioMock = new Usuario();
        usuarioMock.setUsername("admin");
        usuarioMock.setPassword("senha_criptografada");

        dataExpiracaoMock = LocalDateTime.now().plusHours(1);
    }

    @Test
    @DisplayName("Deve realizar login com sucesso e retornar o token JWT")
    void deveRealizarLoginComSucesso() throws Exception {
        LoginRequest request = new LoginRequest("admin", "123456");

        when(repository.findByUsername("admin")).thenReturn(Optional.of(usuarioMock));
        when(encoder.matches("123456", "senha_criptografada")).thenReturn(true);
        when(jwtService.generateToken("admin")).thenReturn("token_gerado_jwt");
        when(jwtService.getExpirationDateTime()).thenReturn(dataExpiracaoMock);

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        ).andExpect(status().isOk());
    }

    @Test
    @DisplayName("Deve retornar erro quando o usuário não existir no banco")
    @WithMockUser
    void deveRetornarErroQuandoUsuarioNaoExistir() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");

        when(repository.findByUsername("usuario_inexistente")).thenReturn(Optional.empty());

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve retornar erro quando a senha informada for incorreta")
    @WithMockUser
    void deveRetornarErroQuandoSenhaForIncorreta() throws Exception {
        LoginRequest request = new LoginRequest();
        request.setUsername("admin");
        request.setPassword("123456");

        when(repository.findByUsername("admin")).thenReturn(Optional.of(usuarioMock));
        when(encoder.matches("senha_errada", "senha_criptografada")).thenReturn(false);

        mockMvc.perform(post("/api/auth/login")
                        .with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    @DisplayName("Deve realizar logout stateless com sucesso")
    @WithMockUser
    void deveRealizarLogoutComSucesso() throws Exception {
        mockMvc.perform(post("/api/auth/logout")
                        .with(csrf()))
                .andExpect(status().isOk());
    }
}
