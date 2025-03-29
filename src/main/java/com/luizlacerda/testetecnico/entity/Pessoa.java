package com.luizlacerda.testetecnico.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pessoa {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pessoaId;
    @Column(nullable = false)
    @Size(min = 5, max = 20)
    private String nome;

    @Column(nullable = false)
    @Size(min = 5, max = 50)
    private String sobreNome;

    @Column(unique = true, nullable = false)
    @Size(min = 14, max = 14)
    private String cpf;
    @Column(unique = true, nullable = false)
    @Email
    private String email;
    @Column(nullable = false)
    @Size(min = 9, max = 15)
    private String telefone;
    @Column(nullable = false)
    @Past
    @NotNull
    private LocalDate dataDeNascimento;

    public Pessoa(String nome, String sobreNome, String cpf, String email, String telefone, LocalDate dataDeNascimento) {
        this.nome = nome;
        this.sobreNome = sobreNome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.dataDeNascimento = dataDeNascimento;
    }
}
