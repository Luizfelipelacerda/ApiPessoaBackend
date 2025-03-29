package com.luizlacerda.testetecnico.controller;

import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import com.luizlacerda.testetecnico.service.PessoaService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/pessoas")
public class PessoaController {


    private final PessoaService pessoaService;

//    @GetMapping("/pagination")
//    @Operation(summary = "Get", description = "Paginar as pessoas", tags = "Pessoa")
//    public ResponseEntity<Page<PessoaRetornoDTO>> getAllPessoasPage(@RequestParam(defaultValue = "0") int page,
//                                                                @RequestParam(defaultValue = "10") int size){
//        Page<PessoaRetornoDTO> pessoasDtoPage = this.pessoaService.getListaDePessoas(page, size);
//
//        return ResponseEntity.ok().body(pessoasDtoPage);
//
//    }

    @GetMapping
    @Operation(summary = "Get", description = "Listar todas as pessoas", tags = "Pessoa")
    public ResponseEntity<List<PessoaRetornoDTO>> getAllPessoas(){
        List<PessoaRetornoDTO> pessoasDtoList = this.pessoaService.getListaDePessoas();
        return ResponseEntity.ok().body(pessoasDtoList);

    }

    @PostMapping
    @Operation(summary = "Insert", description = "Criar uma nova pessoa", tags = "Pessoa")
    public ResponseEntity<PessoaRetornoDTO> createPessoa(@Valid @RequestBody PessoaInsertDTO pessoaInsertDTO){
        PessoaRetornoDTO pessoaCriada = this.pessoaService.inserirPessoa(pessoaInsertDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(pessoaCriada);
    }

    @GetMapping("/cpf/{cpf}")
    @Operation(summary = "Get", description = "Buscar uma pessoa pelo CPF", tags = "Pessoa")
    public ResponseEntity<PessoaRetornoDTO> getPessoaByCpf(@PathVariable("cpf") String cpf){

        PessoaRetornoDTO pessoaRetornoDTO = this.pessoaService.getPessoaPorCpf(cpf);
        return ResponseEntity.ok(pessoaRetornoDTO);
    }

    @PutMapping("{id}")
    @Operation(summary = "Put", description = "Atualiza pessoa pelo Id", tags = "Pessoa")
    public ResponseEntity<PessoaRetornoDTO> updatePessoaById(@PathVariable("id") UUID pessoaId, @Valid @RequestBody PessoaInsertDTO pessoaInsertDTO){
        PessoaRetornoDTO pessoaAtualizada = this.pessoaService.atualizaPessoaPorId(pessoaId, pessoaInsertDTO);
        return ResponseEntity.ok(pessoaAtualizada);
    }

    @DeleteMapping("{id}")
    @Operation(summary = "Delete", description = "Deleta pessoa pelo Id", tags = "Pessoa")
    public ResponseEntity<Void> deletePessoaById(@PathVariable("id") UUID pessoaId){
        this.pessoaService.deletePessoaPorId(pessoaId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

}
