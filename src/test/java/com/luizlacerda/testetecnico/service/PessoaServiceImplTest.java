package com.luizlacerda.testetecnico.service;

import com.luizlacerda.testetecnico.PessoaBaseTest;
import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import com.luizlacerda.testetecnico.controller.mapper.PessoaMapper;
import com.luizlacerda.testetecnico.entity.Pessoa;
import com.luizlacerda.testetecnico.exception.PessoaNotFoundException;
import com.luizlacerda.testetecnico.repository.PessoaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class PessoaServiceImplTest extends PessoaBaseTest {


    @Mock
    private PessoaRepository pessoaRepository;
    @Mock
    private PessoaMapper pessoaMapper;
    @InjectMocks
    private PessoaServiceImpl pessoaService;


    @Test
    @DisplayName("Teste para garantir que o metodo retorne uma lista de PessoaReturnoDTO")
    void testarGetListaDePessoas_deveRetornarListaDePessoasDTO() {
        when(pessoaRepository.findAll()).thenReturn(listPessoa);
        when(pessoaMapper.toDTO(listPessoa)).thenReturn(listPessoaDto);

        List<PessoaRetornoDTO> pessoaList = this.pessoaService.getListaDePessoas();

        verify(pessoaRepository).findAll();

        assertThat(pessoaList).hasSize(1);
        assertThat(pessoaList.get(0).nome()).isEqualTo("luiz felipe");

    }

    @Test
    void testGetListaDePessoas() {
    }

    @Test
    @DisplayName("Teste para garantir que o metodo salve uma pessoa e retorne um PessoaReturnoDTO")
    void testarInserirPessoa() {

        when(pessoaMapper.toEntity(pessoaInsertDTO)).thenReturn(pessoaInsert);

        when(pessoaRepository.save(pessoaInsert)).thenReturn(pessoa);

        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaRetornoDTO);


        PessoaRetornoDTO pessoaSalva = this.pessoaService.inserirPessoa(pessoaInsertDTO);

        verify(pessoaRepository).save(pessoaInsert);

        assertThat(pessoaSalva).isNotNull();
    }

    @Test
    @DisplayName("Teste para garantir que o metodo salve uma pessoa e retorne um PessoaReturnoDTO")
    void testarGetPessoaPorCpfValido_retornaUmapessoa() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.of(pessoa));

        when(pessoaMapper.toDTO(pessoa)).thenReturn(pessoaRetornoDTO);

        PessoaRetornoDTO pessoaPorCpf = this.pessoaService.getPessoaPorCpf(anyString());


        verify(pessoaRepository).findByCpf(anyString());

        assertThat(pessoaPorCpf).isNotNull();
        assertThat(pessoaPorCpf.nome()).isEqualTo("luiz felipe");

    }

    @Test
    @DisplayName("Teste para garantir que o metodo salve uma pessoa e retorne um PessoaReturnoDTO")
    void testarGetPessoaPorCpfInvalido_retornaUmaException() {
        when(pessoaRepository.findByCpf(anyString())).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.pessoaService.getPessoaPorCpf(anyString()))
                .isInstanceOf(PessoaNotFoundException.class)
                .hasMessage("Pessoa não encontrada");
    }

    @Test
    @DisplayName("Teste para garantir que o metodo delete é realizado com sucesso")
    void testarDeletePessoaPorIdValido_deveAssegurarDeQueRemoçaoFoiFeita() {
        when(pessoaRepository.findById(pessoa.getPessoaId())).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoa)).thenReturn(pessoa);

        Pessoa save = this.pessoaRepository.save(pessoa);

        this.pessoaService.deletePessoaPorId(save.getPessoaId());

        verify(pessoaRepository, times(1)).delete(save);

    }
    @Test
    @DisplayName("Teste para garantir que o metodo delete é realizado com sucesso")
    void testarDeletePessoaPorIdInvalido_deveRetornarUmaExcessao() {
        UUID uuid = UUID.randomUUID();
        when(pessoaRepository.findById(uuid)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> this.pessoaService.deletePessoaPorId(uuid))
                .isInstanceOf(PessoaNotFoundException.class)
                .hasMessage("Pessoa não encontrada");
    }

    @Test
    @DisplayName("testa para atualizar pessoa por id valido e garantir que o metodo retorne um PessoaRetornoDTO")
    void testarAtualizaPessoaPorIdValido_AtualizaAPessoaERetornaPessoaRetornoDTO() {
        UUID uuid = pessoa.getPessoaId();
        Pessoa pessoaAlt = new Pessoa(
                uuid,
                "alterado",
                "alterado",
                "111.222.333-44",
                "luizfelipe@gmail.com",
                "987654321",
                LocalDate.of(2000,10,10));
        when(pessoaRepository.findById(uuid)).thenReturn(Optional.of(pessoa));
        when(pessoaRepository.save(pessoaAlt)).thenReturn(pessoaAlt);
        when(pessoaMapper.toDTO(pessoaAlt)).thenReturn(pessoaUpdateReturnDTO);

        this.pessoaService.atualizaPessoaPorId(uuid,pessoaUpdateDTO);

        verify(pessoaRepository, times(1)).save(pessoaAlt);
    }
    @Test
    @DisplayName("Teste para garantir que o metodo atualizaDadosPessoas é realizado com sucesso")
    void testeAtualizaDadosPessoas(){
        Pessoa pessoa1 = pessoaService.atualizaDadosPessoas(pessoa, pessoaUpdateDTO);
        assertThat(pessoa1.getNome()).isEqualTo(pessoaUpdateReturnDTO.nome());
    }
}