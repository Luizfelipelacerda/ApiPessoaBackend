package com.luizlacerda.testetecnico.controller.mapper;

import com.luizlacerda.testetecnico.PessoaBaseTest;
import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import com.luizlacerda.testetecnico.entity.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class PessoaMapperTest extends PessoaBaseTest {

    @InjectMocks
    private PessoaMapper pessoaMapper;


    @Test
    @DisplayName("Teste para garantir que o metodo retorne uma PessoaReturnoDTO")
    void testToDTO_deveRetornarUmaPessoaRetornoDTO() {
        PessoaRetornoDTO pessoaRetornoDTO1 = pessoaMapper.toDTO(pessoa);

        assertThat(pessoaRetornoDTO1).isNotNull();
        assertThat(pessoaRetornoDTO1.getClass()).isEqualTo(PessoaRetornoDTO.class);
        assertThat(pessoaRetornoDTO1.nome()).isEqualTo("luiz felipe");

    }



    @Test
    @DisplayName("Teste para garantir que o metodo retorne uma lista de PessoaReturnoDTO")
    void testToDTO_deveRetornarUmaListDePessoRetornoDTO() {
        List<PessoaRetornoDTO> listaPessoasDTO = pessoaMapper.toDTO(listPessoa);

        assertThat(listaPessoasDTO).isNotNull();
        assertThat(listaPessoasDTO.get(0).nome()).isEqualTo("luiz felipe");

    }

    @Test
    @DisplayName("Teste para garantir que o metodo retorne uma entidade pessoa")
    void testToEntity_deveRetornarUmaEntidadePessoa() {
        Pessoa pessoa1 = pessoaMapper.toEntity(pessoaInsertDTO);
        assertThat(pessoa1).isNotNull();
        assertThat(pessoa1.getNome()).isEqualTo("luiz felipe");
    }
}