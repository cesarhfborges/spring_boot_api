package br.com.chfb.api.service;

import br.com.chfb.api.model.Reuniao;
import br.com.chfb.api.model.StatusReuniao;
import br.com.chfb.api.repository.ReuniaoRepository;
import br.com.chfb.api.security.UsuarioLogadoProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReuniaoService {

    private final ReuniaoRepository repository;
    private final UsuarioLogadoProvider usuarioLogadoProvider;

    public Reuniao salvar(Reuniao reuniao) {
        if (reuniao.getStatus() == null) {
            reuniao.setStatus(StatusReuniao.AGENDADA);
        }
        reuniao.setCriadoPor(usuarioLogadoProvider.getUsuarioLogado());
        return repository.save(reuniao);
    }

    public Reuniao cadastrar(Reuniao reuniao) {
        if (reuniao.getDataHoraFim() != null) {
            throw new IllegalArgumentException(
                    "Data/hora de fim não pode ser informada no cadastro"
            );
        }
        reuniao.setStatus(StatusReuniao.AGENDADA);
        reuniao.setCriadoPor(usuarioLogadoProvider.getUsuarioLogado());
        return repository.save(reuniao);
    }

    public Reuniao buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reunião não encontrada"));
    }

    public List<Reuniao> buscarTodos() {
        return repository.findAll();
    }

    public void excluir(Long id) {
        repository.delete(buscarPorId(id));
    }

    public Reuniao atualizar(Long id, Reuniao reuniaoAtualizada) {

        Reuniao reuniao = buscarPorId(id);

        reuniao.setTitulo(reuniaoAtualizada.getTitulo());
        reuniao.setDescricao(reuniaoAtualizada.getDescricao());
        reuniao.setDataHoraInicio(reuniaoAtualizada.getDataHoraInicio());
//        if (reuniaoAtualizada.getStatus().equals(StatusReuniao.ENCERRADA)) {
//            reuniao.setDataHoraFim(reuniaoAtualizada.getDataHoraFim());
//        } else {
//            reuniao.setDataHoraFim(null);
//        }
        return repository.save(reuniao);
    }
}
