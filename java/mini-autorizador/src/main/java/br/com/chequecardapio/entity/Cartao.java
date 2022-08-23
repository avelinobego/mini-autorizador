package br.com.chequecardapio.entity;

import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

public class Cartao {
    private String numero;
    private String senhaCartao;
    private BigDecimal valor;

    public Cartao() {
    }

    public Cartao(@NonNull String numero, String senhaCartao, BigDecimal valor) {
        this.numero = numero;
        this.senhaCartao = senhaCartao;
        this.valor = valor;
    }

    public String getNumero() {
        return numero;
    }

    @NonNull
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSenhaCartao() {
        return senhaCartao;
    }

    public void setSenhaCartao(String senhaCartao) {
        this.senhaCartao = senhaCartao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Cartao cartao = (Cartao) o;
        return numero.equals(cartao.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
}
