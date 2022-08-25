package br.com.chequecardapio.service;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.status.Status;

import java.util.List;

public interface CartaoService {

    List<Cartao> cartoes();

    Cartao add_cartao(Cartao cartao);

    Cartao saldo(String numero);

    Status transacao(Cartao cartao);
}
