package br.com.chequecardapio.exceptions;

import br.com.chequecardapio.entity.Cartao;

public class CartaoJaExisteException extends Exception {
    
    public CartaoJaExisteException(Cartao cartao) {
        super(String.format("Cartao já existe: %s", String.valueOf(cartao)),
                null,
                false,
                false);
    }

}
