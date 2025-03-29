package com.luizlacerda.testetecnico.controller.dto;
import java.time.LocalDate;

public record PessoaRetornoDTO(String nome, String sobreNome, String cpf, String email, String telefone, LocalDate dataDeNascimento) {}
