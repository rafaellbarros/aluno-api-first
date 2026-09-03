package br.com.rafaellbarros.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados completos do aluno")
public class AlunoResponse {

    @Schema(description = "ID do aluno", example = "1")
    private Long id;

    @Schema(description = "Nome completo do aluno", example = "João Silva")
    private String nome;

    @Schema(description = "Email do aluno", example = "joao.silva@email.com")
    private String email;

    @Schema(description = "Curso do aluno", example = "Engenharia de Software")
    private String curso;

    @Schema(description = "Semestre atual do aluno", example = "5")
    private Integer semestre;

    @Schema(description = "Status do aluno", example = "true")
    private Boolean ativo;

    @Schema(description = "Data de cadastro do aluno", example = "2024-01-15T10:30:00")
    private LocalDateTime dataCadastro;
}
