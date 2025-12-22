package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.ContatoMapper;
import br.com.chfb.api.dto.req.ContatoRequest;
import br.com.chfb.api.dto.resp.ContatoResponse;
import br.com.chfb.api.model.Contato;
import br.com.chfb.api.model.Funcionario;
import br.com.chfb.api.repository.ContatoRepository;
import br.com.chfb.api.repository.FuncionarioRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContatoService {

    private final ContatoRepository contatoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final ContatoMapper contatoMapper;

    @Transactional(readOnly = true)
    public Set<ContatoResponse> listarTodos(Long funcionarioId) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        return funcionario.getContatos()
                .stream()
                .map(contatoMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public ContatoResponse criar(Long funcionarioId, ContatoRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Contato contato = contatoMapper.toEntity(request);
        contato.setFuncionario(funcionario);

        contatoRepository.save(contato);

        return contatoMapper.toDTO(contato);
    }

    @Transactional
    public ContatoResponse atualizar(Long funcionarioId, Long id, ContatoRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        if (!contato.getFuncionario().getId().equals(funcionario.getId())) {
            throw new RuntimeException("Contato não pertence ao funcionário informado");
        }

        contato.setTipo(request.tipo());
        contato.setValor(request.valor());

        return contatoMapper.toDTO(contato);
    }

    @Transactional
    public void remover(Long funcionarioId, Long id) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Contato contato = contatoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Contato não encontrado"));

        if (!contato.getFuncionario().getId().equals(funcionario.getId())) {
            throw new RuntimeException("Contato não pertence ao funcionário informado");
        }

        contatoRepository.delete(contato);
    }

    @Transactional(readOnly = true)
    public ContatoResponse buscarPorId(Long funcionarioId, Long id) {
        Contato contato = contatoRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Contato não encontrado"));

        if (!contato.getFuncionario().getId().equals(funcionarioId)) {
            throw new EntityNotFoundException("O contato não pertence ao funcionário informado");
        }
        return contatoMapper.toDTO(contato);
    }
}

