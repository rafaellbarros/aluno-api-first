package br.com.rafaellbarros.service;

import br.com.rafaellbarros.exception.AlunoNotFoundException;
import br.com.rafaellbarros.mapper.AlunoMapper;
import br.com.rafaellbarros.model.Aluno;
import br.com.rafaellbarros.model.AlunoRequest;
import br.com.rafaellbarros.model.AlunoResponse;
import br.com.rafaellbarros.repository.AlunoRepositoryMemory;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlunoService  {

    private final AlunoRepositoryMemory repository;
    private final AlunoMapper mapper;

    public List<AlunoResponse> listarTodos() {
        log.debug("Listando todos os alunos (em memória)");
        return repository.findAll().stream()
                .map(mapper::toResponse)
                .toList();
    }

    public AlunoResponse criar(AlunoRequest request) {
        log.debug("Criando novo aluno: {}", request.getNome());

        if (repository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email já cadastrado: " + request.getEmail());
        }

        Aluno aluno = mapper.toEntity(request);
        aluno.setAtivo(request.getAtivo() != null ? request.getAtivo() : true);

        Aluno saved = repository.save(aluno);
        log.info("Aluno criado com sucesso - ID: {}", saved.getId());

        return mapper.toResponse(saved);
    }

    public AlunoResponse buscarPorId(Long id) {
        log.debug("Buscando aluno por ID: {}", id);
        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException(id));
        return mapper.toResponse(aluno);
    }

    public AlunoResponse atualizar(Long id, AlunoRequest request) {
        log.debug("Atualizando aluno com ID: {}", id);

        Aluno aluno = repository.findById(id)
                .orElseThrow(() -> new AlunoNotFoundException(id));

        repository.findByEmail(request.getEmail())
                .ifPresent(alunoExistente -> {
                    if (!alunoExistente.getId().equals(id)) {
                        throw new IllegalArgumentException("Email já cadastrado para outro aluno");
                    }
                });

        mapper.updateEntity(request, aluno);
        if (request.getAtivo() != null) {
            aluno.setAtivo(request.getAtivo());
        }

        Aluno updated = repository.save(aluno);
        log.info("Aluno atualizado com sucesso - ID: {}", updated.getId());

        return mapper.toResponse(updated);
    }

    public void deletar(Long id) {
        log.debug("Deletando aluno com ID: {}", id);

        if (!repository.existsById(id)) {
            throw new AlunoNotFoundException(id);
        }

        repository.deleteById(id);
        log.info("Aluno deletado com sucesso - ID: {}", id);
    }

    public boolean existePorId(Long id) {
        return repository.existsById(id);
    }
}
