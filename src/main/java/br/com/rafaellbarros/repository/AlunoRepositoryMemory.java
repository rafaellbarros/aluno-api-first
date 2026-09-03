package br.com.rafaellbarros.repository;

import br.com.rafaellbarros.model.Aluno;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Slf4j
@Repository
public class AlunoRepositoryMemory {

    private final Map<Long, Aluno> alunos = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong(1);

    public AlunoRepositoryMemory() {
        inicializarDados();
    }

    public List<Aluno> findAll() {
        return new ArrayList<>(alunos.values());
    }

    public Optional<Aluno> findById(Long id) {
        return Optional.ofNullable(alunos.get(id));
    }

    public Aluno save(Aluno aluno) {
        if (aluno.getId() == null) {
            Long newId = idGenerator.getAndIncrement();
            aluno.setId(newId);
            if (aluno.getDataCadastro() == null) {
                aluno.setDataCadastro(LocalDateTime.now());
            }
            alunos.put(newId, aluno);
            log.debug("Aluno criado com ID: {}", newId);
        } else {

            if (aluno.getDataCadastro() == null) {
                Aluno existing = alunos.get(aluno.getId());
                if (existing != null) {
                    aluno.setDataCadastro(existing.getDataCadastro());
                } else {
                    aluno.setDataCadastro(LocalDateTime.now());
                }
            }
            alunos.put(aluno.getId(), aluno);
            log.debug("Aluno atualizado com ID: {}", aluno.getId());
        }
        return aluno;
    }

    public void deleteById(Long id) {
        alunos.remove(id);
        log.debug("Aluno removido com ID: {}", id);
    }

    public boolean existsById(Long id) {
        return alunos.containsKey(id);
    }

    public Optional<Aluno> findByEmail(String email) {
        return alunos.values().stream()
                .filter(aluno -> aluno.getEmail().equalsIgnoreCase(email))
                .findFirst();
    }

    public List<Aluno> findByCurso(String curso) {
        return alunos.values().stream()
                .filter(aluno -> aluno.getCurso().equalsIgnoreCase(curso))
                .toList();
    }

    public List<Aluno> findByAtivoTrue() {
        return alunos.values().stream()
                .filter(Aluno::getAtivo)
                .toList();
    }

    public boolean existsByEmail(String email) {
        return alunos.values().stream()
                .anyMatch(aluno -> aluno.getEmail().equalsIgnoreCase(email));
    }

    public long count() {
        return alunos.size();
    }

    public void clear() {
        alunos.clear();
        idGenerator.set(1);
        log.info("Repositório em memória limpo");
    }

    private void inicializarDados() {
        log.info("Inicializando dados em memória...");

        Aluno aluno1 = Aluno.builder()
                .id(idGenerator.getAndIncrement())
                .nome("Maria Silva")
                .email("maria.silva@email.com")
                .curso("Ciência da Computação")
                .semestre(7)
                .ativo(true)
                .dataCadastro(LocalDateTime.now().minusDays(30))
                .build();

        Aluno aluno2 = Aluno.builder()
                .id(idGenerator.getAndIncrement())
                .nome("Pedro Santos")
                .email("pedro.santos@email.com")
                .curso("Engenharia de Software")
                .semestre(5)
                .ativo(true)
                .dataCadastro(LocalDateTime.now().minusDays(15))
                .build();

        Aluno aluno3 = Aluno.builder()
                .id(idGenerator.getAndIncrement())
                .nome("Ana Oliveira")
                .email("ana.oliveira@email.com")
                .curso("Sistemas de Informação")
                .semestre(3)
                .ativo(false)
                .dataCadastro(LocalDateTime.now().minusDays(60))
                .build();

        alunos.put(aluno1.getId(), aluno1);
        alunos.put(aluno2.getId(), aluno2);
        alunos.put(aluno3.getId(), aluno3);

        log.info("Dados iniciais carregados: {} alunos", alunos.size());
    }
}
