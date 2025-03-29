package com.luizlacerda.testetecnico.controller.dto;

import com.luizlacerda.testetecnico.entity.Pessoa;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class PessoaInsertDTOValidationTest {

    private Validator validator;

    @BeforeEach
    void init(){
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void deveFalhar_quandoNomeMuitoCurto(){
        PessoaInsertDTO pessoaDto = new PessoaInsertDTO(
                "lui",
                "la",
                "111.222.333444",
                "luizfelipe_gmail.com",
                "654321",
                LocalDate.of(2030, 01,01)
        );

        Set<ConstraintViolation<PessoaInsertDTO>> violations = validator.validate(pessoaDto);

        assertThat(violations).isNotEmpty();
        assertThat(violations).size().isEqualTo(6);
    }

}