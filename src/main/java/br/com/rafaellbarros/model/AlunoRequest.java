package br.com.rafaellbarros.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Dados para criação/atualização de um aluno")
public class AlunoRequest {

    @Schema(description = "Nome completo do aluno", example = "João Silva", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Nome é obrigatório")
    @Size(max = 100, message = "Nome deve ter no máximo 100 caracteres")
    private String nome;

    @Schema(description = "Email do aluno", example = "joao.silva@email.com", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Email é obrigatório")
    @Email(message = "Email deve ser válido")
    @Size(max = 100, message = "Email deve ter no máximo 100 caracteres")
    private String email;

    @Schema(description = "Curso do aluno", example = "Engenharia de Software", requiredMode = Schema.RequiredMode.REQUIRED)
    @NotBlank(message = "Curso é obrigatório")
    @Size(max = 50, message = "Curso deve ter no máximo 50 caracteres")
    private String curso;

    @Schema(description = "Semestre atual do aluno", example = "5", minimum = "1", maximum = "10")
    @Min(value = 1, message = "Semestre deve ser no mínimo 1")
    @Max(value = 10, message = "Semestre deve ser no máximo 10")
    private Integer semestre;

    @Schema(description = "Status do aluno (ativo/inativo)", example = "true", defaultValue = "true")
    private Boolean ativo;
}
