package br.com.chequecardapio.exceptions;

import br.com.chequecardapio.entity.Cartao;

public class CartaoNotFoundException extends Exception {

    public CartaoNotFoundException(String message) {
        super(message,
                null,
                false,
                false);
    }

    public CartaoNotFoundException(Cartao cartao) {
        super(String.format("Cartao não encontrado: %s", String.valueOf(cartao)),
                null,
                false,
                false);
    }

}
