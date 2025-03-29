package com.luizlacerda.testetecnico.controller.dto;
import java.time.LocalDate;
import java.util.UUID;

public record PessoaRetornoDTO(UUID id, String nome, String sobreNome, String cpf, String email, String telefone, LocalDate dataDeNascimento) {}
