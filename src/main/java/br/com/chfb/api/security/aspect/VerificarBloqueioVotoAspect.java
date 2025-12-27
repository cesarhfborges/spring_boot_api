package br.com.chfb.api.security.aspect;

import br.com.chfb.api.repository.BloqueioVotoPautaRepository;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class VerificarBloqueioVotoAspect {

    private final BloqueioVotoPautaRepository bloqueioRepository;
    private final UsuarioLogadoProvider usuarioLogadoProvider;

    @Before("@annotation(br.com.chfb.api.security.annotation.VerificarBloqueioVoto)")
    public void verificarBloqueio(JoinPoint joinPoint) {

        Object[] args = joinPoint.getArgs();

        Long pautaId = extrairPautaId(args);
        Long funcionarioId = usuarioLogadoProvider.getUsuarioLogado().getId();

        bloqueioRepository
                .findByFuncionario_IdAndPauta_IdAndAtivoTrue(funcionarioId, pautaId)
                .ifPresent(bloqueio -> {
                    throw new RuntimeException(
                            "Funcionário impedido de votar nesta pauta: " +
                                    bloqueio.getMotivo()
                    );
                });
    }

    private Long extrairPautaId(Object[] args) {
        for (Object arg : args) {
            if (arg instanceof Long id) {
                return id;
            }
        }
        throw new IllegalArgumentException("ID da pauta não encontrado");
    }
}
