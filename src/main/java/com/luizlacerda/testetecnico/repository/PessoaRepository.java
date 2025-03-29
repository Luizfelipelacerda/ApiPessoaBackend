package com.luizlacerda.testetecnico.repository;

import com.luizlacerda.testetecnico.entity.Pessoa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, UUID> {
    Optional<Pessoa> findByCpf(String pessoaCpf);

    Optional<Pessoa> findByCpfOrEmail(String cpf,String email);
}
