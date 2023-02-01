package br.com.chequecardapio.service;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.exceptions.CartaoJaExisteException;
import br.com.chequecardapio.exceptions.CartaoNotFoundException;
import br.com.chequecardapio.status.Status;

import java.math.BigDecimal;
import java.util.List;

public interface CartaoService {

    List<Cartao> cartoes();

    Cartao add_cartao(Cartao cartao) throws CartaoJaExisteException;

    BigDecimal saldo(String numero) throws CartaoNotFoundException;

    Status transacao(Cartao cartao);
}
