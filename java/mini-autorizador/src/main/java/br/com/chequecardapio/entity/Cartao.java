package br.com.chequecardapio.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

@Document(collection = "cartoes")
public class Cartao {
    @Id private String numero;
    private String nome;
    private String senha;
    private BigDecimal valor;

    public Cartao() {
    }

    public Cartao(@NonNull String numero, String senha, BigDecimal valor) {
        this.numero = numero;
        this.senha = senha;
        this.valor = valor;
    }

    public String getNumero() {
        return numero;
    }

    @NonNull
    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
