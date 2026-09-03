package br.com.rafaellbarros.mapper;

import br.com.rafaellbarros.model.Aluno;
import br.com.rafaellbarros.model.AlunoRequest;
import br.com.rafaellbarros.model.AlunoResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(
        componentModel = "spring",
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE
)
public interface AlunoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    Aluno toEntity(AlunoRequest request);

    AlunoResponse toResponse(Aluno aluno);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "dataCadastro", ignore = true)
    void updateEntity(AlunoRequest request, @MappingTarget Aluno aluno);
}
