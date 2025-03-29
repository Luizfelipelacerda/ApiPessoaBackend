package com.luizlacerda.testetecnico.controller.dto;

import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.Date;

public record PessoaInsertDTO(

        @NotBlank(message = "O Nome não pode estar vazio")
        @Size(min = 3, max = 20, message = "O nome deve ter entre 3 e 20 caracteres")
        String nome,

        @NotBlank(message = "O Sobrenome não pode estar vazio")
        @Size(min = 5, max = 50, message = "O sobrenome deve ter entre 5 e 50 caracteres")
        String sobreNome,

        @NotBlank(message = "O cpf não pode estar vazio")
        @Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}\\-\\d{2}$", message = "O cpf deve estar no formato xxx.xxx.xxx-xx")
        @Size(min = 14, max = 14, message = "Numeros de caracteres no cpf invalidos")
        String cpf,

        @NotBlank(message = "O Email não pode estar vazio")
        @Email(message = "Formato de Email esta inválido")
        String email,
        @NotBlank(message = "O telefone não pode estar vazia")
        @Size(min = 9, message = "O telefone deve ter pelo menos 8 caracteres")
        String telefone,

        @Past(message = "A data de nascimento deve estar no passado")
        LocalDate dataDeNascimento


) {
}
