package com.luizlacerda.testetecnico.repository;

import com.luizlacerda.testetecnico.PessoaBaseTest;
import com.luizlacerda.testetecnico.entity.Pessoa;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class PessoaRepositoryTest extends PessoaBaseTest {

    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private PessoaRepository pessoaRepository;


    @Test
    @DisplayName("Deve garantir que o metodo retorne uma Pessoa.")
    void testeFindByCpf() {
        String cpf = "111.222.333-44";
        entityManager.persist(this.pessoaInsert);
        entityManager.flush();

        Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(cpf);

        assertThat(pessoaOptional).isPresent();
        assertThat(pessoaOptional.get().getCpf()).isEqualTo(cpf);

    }

    @Test
    @DisplayName("Deve garantir que o metodo retorne uma Pessoa.")
    void testeFindByCpfNaoExistente() {
        String cpf = "111.222.333.55";

        Optional<Pessoa> pessoaOptional = pessoaRepository.findByCpf(cpf);

        assertThat(pessoaOptional).isEmpty();

    }


}