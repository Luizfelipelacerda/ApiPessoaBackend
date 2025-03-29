package com.luizlacerda.testetecnico.service;

import com.luizlacerda.testetecnico.controller.dto.PessoaInsertDTO;
import com.luizlacerda.testetecnico.controller.dto.PessoaRetornoDTO;
import com.luizlacerda.testetecnico.controller.mapper.PessoaMapper;
import com.luizlacerda.testetecnico.entity.Pessoa;
import com.luizlacerda.testetecnico.exception.PessoaAlreadyExistException;
import com.luizlacerda.testetecnico.exception.PessoaNotFoundException;
import com.luizlacerda.testetecnico.repository.PessoaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Observable;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class PessoaServiceImpl implements PessoaService{

    private final PessoaRepository pessoaRepository;
    private final PessoaMapper pessoaMapper;

//    @Override
//    public Page<PessoaRetornoDTO> getListaDePessoas(int page, int size) {
//        Page<Pessoa> pessoaPage = this.pessoaRepository.findAll(PageRequest.of(page,size));
//
//        return this.pessoaMapper.toDTO(pessoaPage);
//    }

    @Override
    public List<PessoaRetornoDTO> getListaDePessoas() {
        List<Pessoa> pessoaList = this.pessoaRepository.findAll();
        return this.pessoaMapper.toDTO(pessoaList);
    }

    @Override
    public PessoaRetornoDTO inserirPessoa(PessoaInsertDTO pessoaInsertDTO) {

        Optional<Pessoa> optionCpf = this.pessoaRepository.findByCpfOrEmail(pessoaInsertDTO.cpf(),pessoaInsertDTO.email());
        if(optionCpf.isPresent()){
            throw new PessoaAlreadyExistException();
        }

        Pessoa pessoa = this.pessoaMapper.toEntity(pessoaInsertDTO);
        Pessoa pessoaSalva = this.pessoaRepository.save(pessoa);
        PessoaRetornoDTO pessoaRetornoDTO = this.pessoaMapper.toDTO(pessoaSalva);
        return pessoaRetornoDTO;
    }

    @Override
    public PessoaRetornoDTO getPessoaPorCpf(String pessoaCpf) {

        Optional<Pessoa> optionalPessoa = this.pessoaRepository.findByCpf(pessoaCpf);
        if(optionalPessoa.isEmpty()){
            throw new PessoaNotFoundException();
        }

        return this.pessoaMapper.toDTO(optionalPessoa.get());
    }

    @Override
    public void deletePessoaPorId(UUID pessoaId) {
        Optional<Pessoa> pessoaOptional = this.pessoaRepository.findById(pessoaId);
        if(pessoaOptional.isEmpty()){
                throw new PessoaNotFoundException();
        }
        this.pessoaRepository.delete(pessoaOptional.get());
    }

    @Override
    public PessoaRetornoDTO atualizaPessoaPorId(UUID pessoaId, PessoaInsertDTO pessoaInsertDTO) {
        Optional<Pessoa> pessoaOptional = this.pessoaRepository.findById(pessoaId);
        if(pessoaOptional.isEmpty()){
            throw new PessoaNotFoundException();
        }
        Pessoa pessoa = pessoaOptional.get();

        Pessoa pessoaAlt = this.atualizaDadosPessoas(pessoa, pessoaInsertDTO);
        Pessoa save = this.pessoaRepository.save(pessoaAlt);
        PessoaRetornoDTO pessoaRetornoDTO = this.pessoaMapper.toDTO(save);
        return pessoaRetornoDTO;
    }

    public Pessoa atualizaDadosPessoas(Pessoa pessoa, PessoaInsertDTO pessoaInsertDTO){
        pessoa.setNome(pessoaInsertDTO.nome());
        pessoa.setSobreNome(pessoaInsertDTO.sobreNome());
        pessoa.setTelefone(pessoaInsertDTO.telefone());
        pessoa.setDataDeNascimento(pessoaInsertDTO.dataDeNascimento());
        return pessoa;
    }
}
