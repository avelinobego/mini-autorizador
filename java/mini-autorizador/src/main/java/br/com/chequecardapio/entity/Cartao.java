package br.com.chequecardapio.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.lang.NonNull;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Entidade que represernta um cartão
 */
@Document(collection = "cartoes")
public class Cartao {
    @Id
    @Indexed(unique = true)
    @NonNull
    private String numero;
    @NonNull
    private String nome;
    @NonNull
    private String senha;
    @NonNull
    private BigDecimal valor;

    public Cartao(@NonNull String nome, @NonNull String numero, @NonNull String senha, @NonNull BigDecimal valor) {
        this.nome = nome;
        this.numero = numero;
        this.senha = senha;
        this.valor = valor;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(@NonNull String numero) {
        this.numero = numero;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(@NonNull String senha) {
        this.senha = senha;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(@NonNull BigDecimal valor) {
        this.valor = valor;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(@NonNull String nome) {
        this.nome = nome;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Cartao cartao = (Cartao) o;
        return numero.equals(cartao.numero);
    }

    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }

    @Override
    public String toString() {
        return "Cartao [numero=" + numero + ", nome=" + nome + "]";
    }

}
