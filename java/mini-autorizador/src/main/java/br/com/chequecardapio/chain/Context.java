package br.com.chequecardapio.chain;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.status.Status;

public class Context {
    private Cartao cartao;
    private Cartao encontrado;
    private Status status;
    private String numero;

    public Cartao getCartao() {
        return cartao;
    }

    public void setCartao(Cartao cartao) {
        this.cartao = cartao;
    }

    public Cartao getEncontrado() {
        return encontrado;
    }

    public void setEncontrado(Cartao encontrado) {
        this.encontrado = encontrado;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getNumero() {
        return numero;
    }

    
}
