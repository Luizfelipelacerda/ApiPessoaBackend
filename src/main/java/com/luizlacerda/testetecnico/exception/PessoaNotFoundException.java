package com.luizlacerda.testetecnico.exception;

public class PessoaNotFoundException extends RuntimeException{

    public PessoaNotFoundException(){
        super("Pessoa não encontrada");
    }
}
