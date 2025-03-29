package com.luizlacerda.testetecnico.exception;
public class PessoaAlreadyExistException extends RuntimeException {
    public PessoaAlreadyExistException() {
        super("Pessoa com o mesmo Cpf ou Email ja existe");
    }
}
