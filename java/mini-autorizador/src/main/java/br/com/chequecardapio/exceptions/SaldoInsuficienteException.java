package br.com.chequecardapio.exceptions;

import br.com.chequecardapio.entity.Cartao;

public class SaldoInsuficienteException extends Exception {
    
    public SaldoInsuficienteException(String message) {
        super(message,
                null,
                false,
                false);
    }

    public SaldoInsuficienteException(Cartao cartao) {
        super(String.format("Saldo insuficiente para o cartão: %s", String.valueOf(cartao)),
                null,
                false,
                false);
    }

}
