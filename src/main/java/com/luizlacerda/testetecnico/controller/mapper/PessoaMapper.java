package com.luizlacerda.testetecnico.controller.mapper;

import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import com.luizlacerda.testetecnico.entity.Pessoa;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PessoaMapper {

    public PessoaRetornoDTO toDTO(Pessoa pessoa){
        return new PessoaRetornoDTO(pessoa.getPessoaId(), pessoa.getNome(), pessoa.getSobreNome(), pessoa.getCpf(), pessoa.getEmail(), pessoa.getTelefone(), pessoa.getDataDeNascimento());
    }

    public List<PessoaRetornoDTO> toDTO(List<Pessoa> pessoas){
        return pessoas.stream().map(this::toDTO).toList();
    }

    public Page<PessoaRetornoDTO> toDTO(Page<Pessoa> pessoas){
        return pessoas.map( p ->
                new PessoaRetornoDTO(p.getPessoaId(),p.getNome(), p.getSobreNome(), p.getCpf(), p.getEmail(),p.getTelefone(),p.getDataDeNascimento())
        );
    }

    public Pessoa toEntity(PessoaInsertDTO dto) {
        return new Pessoa(dto.nome(), dto.sobreNome(), dto.cpf(), dto.email(), dto.telefone(), dto.dataDeNascimento());
    }

}
