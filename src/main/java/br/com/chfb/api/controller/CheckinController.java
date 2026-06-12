package br.com.chfb.api.controller;

import br.com.chfb.api.dto.resp.CheckinResponse;
import br.com.chfb.api.model.Reuniao;
import br.com.chfb.api.model.Usuario;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import br.com.chfb.api.service.ReuniaoCheckinService;
import br.com.chfb.api.service.ReuniaoService;
import br.com.chfb.api.utils.RequestUtils;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

@RestController
@RequestMapping("/api/reunioes/{reuniaoId}/checkin")
@RequiredArgsConstructor
@Tag(name = "11 - Checkin", description = "Registro de presença em reunião")
@SecurityRequirement(name = "bearerAuth")
public class CheckinController {

    private final ReuniaoCheckinService checkinService;
    private final ReuniaoService reuniaoService;
    private final UsuarioLogadoProvider usuarioLogadoProvider;

    @GetMapping
    @Operation(summary = "Verificar check-in")
    public ResponseEntity<CheckinResponse> verificar(
            @PathVariable Long reuniaoId
    ) {

        Usuario usuario = usuarioLogadoProvider.getUsuarioLogado();

        return ResponseEntity.ok(
                new CheckinResponse(checkinService.possuiCheckin(reuniaoId, usuario.getId()), new Date())
        );
    }

    @PostMapping
    @Operation(summary = "Realizar check-in")
    public ResponseEntity<CheckinResponse> realizar(
            @PathVariable Long reuniaoId,
            HttpServletRequest request
    ) {

        Usuario usuario = usuarioLogadoProvider.getUsuarioLogado();
        Reuniao reuniao = reuniaoService.buscarPorId(reuniaoId);

        checkinService.realizarCheckin(
                reuniao,
                usuario,
//                request.getRemoteAddr(),
//                request.getHeader("Host"),
                RequestUtils.getClientIp(request),
                request.getHeader("User-Agent"),
                request.getHeader("Application")
        );

        return ResponseEntity.ok().build();
    }
}
