package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.EnderecoMapper;
import br.com.chfb.api.dto.req.EnderecoRequest;
import br.com.chfb.api.dto.resp.EnderecoResponse;
import br.com.chfb.api.model.Endereco;
import br.com.chfb.api.model.Funcionario;
import br.com.chfb.api.model.UF;
import br.com.chfb.api.repository.EnderecoRepository;
import br.com.chfb.api.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EnderecoService {

    private final EnderecoRepository enderecoRepository;
    private final FuncionarioRepository funcionarioRepository;
    private final EnderecoMapper enderecoMapper;

    @Transactional(readOnly = true)
    public Set<EnderecoResponse> listarTodos(Long funcionarioId) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        return funcionario.getEnderecos()
                .stream()
                .map(enderecoMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Transactional
    public EnderecoResponse criar(Long funcionarioId, EnderecoRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Endereco endereco = enderecoMapper.toEntity(request);
        endereco.setFuncionario(funcionario);

        enderecoRepository.save(endereco);

        return enderecoMapper.toDTO(endereco);
    }

    @Transactional
    public EnderecoResponse atualizar(Long funcionarioId, Long id, EnderecoRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        if (!endereco.getFuncionario().getId().equals(funcionario.getId())) {
            throw new RuntimeException("Endereço não pertence ao funcionário informado");
        }

        endereco.setLogradouro(request.logradouro());
        endereco.setNumero(request.numero());
        endereco.setComplemento(request.complemento());
        endereco.setBairro(request.bairro());
        endereco.setCidade(request.cidade());
        endereco.setUf(UF.valueOf(request.uf()));
        endereco.setCep(request.cep());

        return enderecoMapper.toDTO(endereco);
    }

    @Transactional
    public void remover(Long funcionarioId, Long id) {

        Funcionario funcionario = funcionarioRepository.findById(funcionarioId)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        Endereco endereco = enderecoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Endereço não encontrado"));

        if (!endereco.getFuncionario().getId().equals(funcionario.getId())) {
            throw new RuntimeException("Endereço não pertence ao funcionário informado");
        }

        enderecoRepository.delete(endereco);
    }
}

