package com.luizlacerda.testetecnico.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.luizlacerda.testetecnico.PessoaBaseTest;
import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.exception.PessoaNotFoundException;
import com.luizlacerda.testetecnico.service.PessoaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ActiveProfiles("test")
@WebMvcTest(controllers = PessoaController.class)
@ExtendWith(MockitoExtension.class)
class PessoaControllerTest extends PessoaBaseTest {

    @Autowired
    private MockMvc mockMvc;
    @MockitoBean
    private PessoaService pessoaService;

    @Autowired
    private ObjectMapper objectMapper;

    private PessoaInsertDTO pessoaInsertDTOValido;
    private PessoaInsertDTO pessoaInsertDTOInvalido;
    @BeforeEach
    void init(){
        this.pessoaInsertDTOValido = new PessoaInsertDTO(
                "luiz felipe",
                "lacerda",
                "111.222.333-44",
                "luizlacerda@gmail.com",
                "123456789",
                LocalDate.of(1980, 01,01));
        this.pessoaInsertDTOInvalido = new PessoaInsertDTO(
                "lui",
                "la",
                "11122233344555",
                "algum_email",
                "1234567",
                LocalDate.of(2030, 01,01));
    }

    @Test
    @DisplayName("Deve retornar uma listas de pessoas e status 200 ok")
    void deveRetornarStatusOkEUmaListaDePessoas() throws Exception {
        given(pessoaService.getListaDePessoas()).willReturn(List.of(pessoaRetornoDTO));

        mockMvc.perform(get("/api/v1/pessoas")
                .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.[0].nome").value("luiz felipe"));
    }

    @Test
    @DisplayName("Criar uma pessoa com dados válidos deve retornar 201 Created")
    void deveRetornerStatusCreated_quandoCriarPessoaValida() throws Exception {
        given(pessoaService.inserirPessoa(pessoaInsertDTOValido))
                .willReturn(pessoaRetornoDTO);

        mockMvc.perform(post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaInsertDTOValido))
                )
                .andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    @DisplayName("Tentar criar uma pessoa com dados invalidos e deve retornar status 400")
    void deveRetornarStatus400_quandoCriarPessoaInvalida() throws Exception{
        given(pessoaService.inserirPessoa(pessoaInsertDTOInvalido))
                .willReturn(pessoaRetornoDTO);

        mockMvc.perform(
                        post("/api/v1/pessoas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaInsertDTOInvalido))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(jsonPath("$.nome").value("O nome deve ter entre 5 e 20 caracteres"))
                .andExpect(jsonPath("$.sobreNome").value("O sobrenome deve ter entre 5 e 50 caracteres"))
                .andExpect(jsonPath("$.telefone").value("O telefone deve ter pelo menos 8 caracteres"))
                .andExpect(jsonPath("$.cpf").value("O cpf deve estar no formato xxx.xxx.xxx-xx"))
                .andExpect(jsonPath("$.email").value("Formato de Email esta inválido"))
                .andExpect(jsonPath("$.dataDeNascimento").value("A data de nascimento deve estar no passado"))
        ;

    }

    @Test
    @DisplayName("Procura pessoa por um cpf correto e deve retornar status 200 ok e pessoa")
    void deveRetornarStatusOkay_quandoProcurarPessoaPorCpfCorreto() throws Exception {
        //given
        String cpf = "111.222.333-44";

        //when
        when(pessoaService.getPessoaPorCpf(cpf)).thenReturn(pessoaRetornoDTO);

        mockMvc.perform(
                        get("/api/v1/pessoas/cpf/"+cpf)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.nome").value("luiz felipe"));
    }

    @Test
    @DisplayName("Procura pessoa por um cpf correto e deve retornar status 200 ok e pessoa")
    void deveRetornarException_quandoProcurarPessoaPorCpfInexistente() throws Exception {
        String cpf = "111.222.333-55";

        when(pessoaService.getPessoaPorCpf(cpf)).thenThrow(new PessoaNotFoundException());

        mockMvc.perform(
                        get("/api/v1/pessoas/cpf/"+cpf)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
    @Test
    @DisplayName("Deve atualizar uma pessoa por id e retornar codigo 200 ok")
    void deveRetornarStatusOkay_quandoAtualizarUmaPessoa() throws Exception {
        UUID id = UUID.randomUUID();
        when(pessoaService.atualizaPessoaPorId(id, pessoaInsertDTOValido)).thenReturn(pessoaRetornoDTO);

        mockMvc.perform(
                        put("/api/v1/pessoas/"+id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(pessoaInsertDTOValido))
                )
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    @DisplayName("Deve lançar uma exception quando tentar atualizar uma pessoa inexistente e retornar status 400 bad request ")
    void deveRetornarException_quandoTentarAtualizarUmaPessoaInexistente() throws Exception {
        UUID id = UUID.randomUUID();
        when(pessoaService.atualizaPessoaPorId(id, pessoaInsertDTOValido)).thenThrow(PessoaNotFoundException.class);

        mockMvc.perform(
                        put("/api/v1/pessoas/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(pessoaInsertDTOValido))
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

    @Test
    @DisplayName("Deve deletar uma pessoa por id e retornar codigo 204 no content")
    void deveRetornarStatusNoContent_quandoDeletarUmaPessoa() throws Exception {
        UUID id = UUID.randomUUID();
        doNothing().when(pessoaService).deletePessoaPorId(id);

        mockMvc.perform(
                        delete("/api/v1/pessoas/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isNoContent());


    }

    @Test
    @DisplayName("Deve lançar uma PessoaNotFoundException quando tentar deletar uma pessoa inexistente e retornar codigo 400 bad request")
    void deveRetornarException_quandoTentarDeletarUmaPessoaInexistente() throws Exception {
        UUID id = UUID.randomUUID();
        doThrow(PessoaNotFoundException.class).when(pessoaService).deletePessoaPorId(id);

        mockMvc.perform(
                        delete("/api/v1/pessoas/"+id)
                                .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest());


    }
}