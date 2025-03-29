package com.luizlacerda.testetecnico.entity;

import com.luizlacerda.testetecnico.PessoaBaseTest;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


import java.time.LocalDate;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;


class PessoaDTOValidationTest extends PessoaBaseTest {

    private Validator validator;

    @BeforeEach
    void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveFalhar_quandoNomeMuitoCurto(){
        Pessoa pessoaInvalida = new Pessoa(
                "lu",
                "la",
                "111.222.333.444",
                "luizfelipe-gmail.com",
                "9854321",
                LocalDate.of(2030,10,10)
        );

        Set<ConstraintViolation<Pessoa>> violations = validator.validate(pessoaInvalida);

        assertThat(violations).isNotEmpty();
        assertThat(violations).size().isEqualTo(6);
    }

}