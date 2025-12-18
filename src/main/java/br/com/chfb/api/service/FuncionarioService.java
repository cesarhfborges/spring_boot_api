package br.com.chfb.api.service;

import br.com.chfb.api.dto.mapper.FuncionarioMapper;
import br.com.chfb.api.dto.resp.FuncionarioResponse;
import br.com.chfb.api.model.Funcionario;
import br.com.chfb.api.repository.FuncionarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FuncionarioService {

    private final FuncionarioRepository funcionarioRepository;
    private final FuncionarioMapper funcionarioMapper;

    @Transactional(readOnly = true)
    public Set<FuncionarioResponse> listarTodos() {

        List<Funcionario> lista = funcionarioRepository.findAll();
        return lista
                .stream()
                .map(funcionarioMapper::toDTO)
                .collect(Collectors.toSet());
    }
}
