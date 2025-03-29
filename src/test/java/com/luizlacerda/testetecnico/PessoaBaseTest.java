package com.luizlacerda.testetecnico;

import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import com.luizlacerda.testetecnico.entity.Pessoa;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public class PessoaBaseTest {

    protected Pessoa pessoaInsert;
    protected Pessoa pessoa;
    protected List<Pessoa> listPessoa;
    protected PessoaRetornoDTO pessoaRetornoDTO;
    protected PessoaRetornoDTO pessoaUpdateReturnDTO;
    protected PessoaInsertDTO pessoaInsertDTO;
    protected PessoaInsertDTO pessoaUpdateDTO;
    protected List<PessoaRetornoDTO> listPessoaDto;

    @BeforeEach
    void setUp(){
        UUID uuid = UUID.randomUUID();
        pessoaInsert = new Pessoa(
                "luiz felipe",
                "lacerda",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10));
        pessoa = new Pessoa(
                uuid,
                "luiz felipe",
                "lacerda",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10));

        listPessoa = List.of(this.pessoa);

        pessoaInsertDTO = new PessoaInsertDTO(
                "luiz felipe",
                "lacerda",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10));
        pessoaUpdateDTO = new PessoaInsertDTO(
                "alterado",
                "alterado",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10));

        pessoaUpdateReturnDTO = new PessoaRetornoDTO(
                "alterado",
                "alterado",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10));

        pessoaRetornoDTO = new PessoaRetornoDTO(
                "luiz felipe",
                "lacerda",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10)
        );

        listPessoaDto = List.of(pessoaRetornoDTO);
    }
}
