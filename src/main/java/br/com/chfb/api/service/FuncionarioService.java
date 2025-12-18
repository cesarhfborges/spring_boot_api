package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.FuncionarioMapper;
import br.com.chfb.api.dto.req.FuncionarioRequest;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.model.Funcionario;
import br.com.chfb.api.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    @Transactional(readOnly = true)
    public Set<FuncionarioResponse> listarTodos(String sort, String direction) {

        Sort.Direction dir = Sort.Direction.ASC;

        if ("desc".equalsIgnoreCase(direction)) {
            dir = Sort.Direction.DESC;
        }

        Sort ordenacao = (sort == null || sort.isBlank())
                ? Sort.by(dir, "id")
                : Sort.by(dir, sort);

        return funcionarioRepository.findAll(ordenacao)
                .stream()
                .map(funcionarioMapper::toDTO)
                .collect(Collectors.toSet());
    }

    @Transactional(readOnly = true)
    public FuncionarioResponse buscarPorId(Long id) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        return funcionarioMapper.toDTO(funcionario);
    }

    @Transactional
    public FuncionarioResponse criar(FuncionarioRequest request) {

        Funcionario funcionario = funcionarioMapper.toEntity(request);

        funcionarioRepository.save(funcionario);

        return funcionarioMapper.toDTO(funcionario);
    }

    @Transactional
    public FuncionarioResponse atualizar(Long id, FuncionarioRequest request) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        funcionario.setNome(request.nome());
        funcionario.setSobrenome(request.sobrenome());
        funcionario.setDataNascimento(request.dataNascimento());
        funcionario.setCpf(request.cpf());
        funcionario.setRg(request.rg());

        return funcionarioMapper.toDTO(funcionario);
    }

    @Transactional
    public void remover(Long id) {

        Funcionario funcionario = funcionarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));

        funcionarioRepository.delete(funcionario);
    }
}
