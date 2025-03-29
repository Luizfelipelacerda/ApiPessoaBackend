package com.luizlacerda.testetecnico.service;

import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public interface PessoaService {
//    Page<PessoaRetornoDTO> getListaDePessoas(int page, int size);

    List<PessoaRetornoDTO> getListaDePessoas();

    PessoaRetornoDTO inserirPessoa(PessoaInsertDTO pessoaInsertDTO);

    PessoaRetornoDTO getPessoaPorCpf(String pessoaCpf);

    void deletePessoaPorId(UUID pessoaId);

    PessoaRetornoDTO atualizaPessoaPorId(UUID pessoaId, PessoaInsertDTO pessoaInsertDTO);
}
