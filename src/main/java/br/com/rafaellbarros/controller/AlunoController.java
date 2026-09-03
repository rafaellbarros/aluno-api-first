package br.com.rafaellbarros.controller;


import br.com.rafaellbarros.model.AlunoRequest;
import br.com.rafaellbarros.model.AlunoResponse;
import br.com.rafaellbarros.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Alunos", description = "Operações relacionadas aos alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @GetMapping
    @Operation(summary = "Lista todos os alunos", description = "Retorna uma lista com todos os alunos cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de alunos retornada com sucesso"),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class)))
    })
    public ResponseEntity<List<AlunoResponse>> listarAlunos() {
        log.info("GET /alunos - Listando todos os alunos");
        return ResponseEntity.ok(alunoService.listarTodos());
    }

    @PostMapping
    @Operation(summary = "Cria um novo aluno", description = "Cadastra um novo aluno no sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Aluno criado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class)))
    })
    public ResponseEntity<AlunoResponse> criarAluno(@Valid @RequestBody AlunoRequest alunoRequest) {
        log.info("POST /alunos - Criando novo aluno: {}", alunoRequest.getNome());
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(alunoService.criar(alunoRequest));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um aluno por ID", description = "Retorna os dados de um aluno específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno encontrado"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class)))
    })
    public ResponseEntity<AlunoResponse> buscarAlunoPorId(
            @Parameter(description = "ID do aluno", required = true, example = "1")
            @PathVariable Long id) {
        log.info("GET /alunos/{} - Buscando aluno", id);
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno existente", description = "Atualiza os dados de um aluno específico")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Aluno atualizado com sucesso"),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class))),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class)))
    })
    public ResponseEntity<AlunoResponse> atualizarAluno(
            @Parameter(description = "ID do aluno", required = true, example = "1")
            @PathVariable Long id,
            @Valid @RequestBody AlunoRequest alunoRequest) {
        log.info("PUT /alunos/{} - Atualizando aluno", id);
        return ResponseEntity.ok(alunoService.atualizar(id, alunoRequest));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um aluno", description = "Remove um aluno específico do sistema")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Aluno removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Aluno não encontrado",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class))),
            @ApiResponse(responseCode = "500", description = "Erro interno do servidor",
                    content = @Content(schema = @Schema(implementation = br.com.rafaellbarros.model.ErroResponse.class)))
    })
    public ResponseEntity<Void> deletarAluno(
            @Parameter(description = "ID do aluno", required = true, example = "1")
            @PathVariable Long id) {
        log.info("DELETE /alunos/{} - Deletando aluno", id);
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
