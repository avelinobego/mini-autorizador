package br.com.chequecardapio.exceptions;

import br.com.chequecardapio.entity.Cartao;

public class SenhaInvalidaexception extends Exception {

    public SenhaInvalidaexception(String message) {
        super(message,
                null,
                false,
                false);
    }

    public SenhaInvalidaexception(Cartao cartao) {
        super(String.format("Senha invalida: %s", String.valueOf(cartao)),
                null,
                false,
                false);
    }

}
