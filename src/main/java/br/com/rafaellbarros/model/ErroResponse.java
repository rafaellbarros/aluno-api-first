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
@Schema(description = "Resposta de erro da API")
public class ErroResponse {

    @Schema(description = "Data e hora do erro", example = "2024-01-15T10:30:00")
    private LocalDateTime timestamp;

    @Schema(description = "Código HTTP do erro", example = "404")
    private Integer status;

    @Schema(description = "Mensagem de erro", example = "Aluno não encontrado")
    private String error;

    @Schema(description = "Caminho da requisição", example = "/api/alunos/1")
    private String path;
}
