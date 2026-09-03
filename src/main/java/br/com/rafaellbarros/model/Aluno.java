package br.com.rafaellbarros.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Aluno {

    private Long id;
    private String nome;
    private String email;
    private String curso;
    private Integer semestre;
    private Boolean ativo;
    private LocalDateTime dataCadastro;
}
