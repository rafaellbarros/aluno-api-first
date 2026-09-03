package br.com.rafaellbarros.exception;

import lombok.Getter;

@Getter
public class AlunoNotFoundException extends RuntimeException {

    private final Long id;

    public AlunoNotFoundException(Long id) {
        super("Aluno não encontrado com ID: " + id);
        this.id = id;
    }
}
