package br.com.chequecardapio.controller;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.exceptions.CartaoJaExisteException;
import br.com.chequecardapio.exceptions.CartaoNotFoundException;
import br.com.chequecardapio.exceptions.SaldoInsuficienteException;
import br.com.chequecardapio.exceptions.SenhaInvalidaexception;
import br.com.chequecardapio.service.CartaoService;
import br.com.chequecardapio.status.Status;

/**
 * AutorizadorController
 * 
 * Responsável por atendar às requisições
 */
@RestController("/")
public class AutorizadorController {

    private final CartaoService cartaoService;

    @Autowired
    public AutorizadorController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }

    /**
     * all
     * Obtém todos os cartões
     * 
     * @return uma lsta com todos os cartões
     */
    @GetMapping("/cartoes")
    public ResponseEntity<List<Cartao>> all() {
        return ResponseEntity.ok(cartaoService.cartoes());
    }

    /**
     * saldo
     * Obtém o saldo pelo número do cartão
     * 
     * @param numero número do cartão
     * @return o valor do saldo
     * @throws CartaoNotFoundException
     * @throws SenhaInvalidaexception
     */
    @GetMapping("/cartoes/{numero}")
    public ResponseEntity<BigDecimal> saldo(@PathVariable String numero) throws CartaoNotFoundException {
        return ResponseEntity.ok(cartaoService.saldo(numero));
    }

    /**
     * Add
     * Adiciona um cartão
     * 
     * @param cartao a ser adicionado
     * @return o cartão que acabara de ser adicionado
     * @throws CartaoJaExisteException
     */
    @PostMapping("/cartao")
    public ResponseEntity<Cartao> add(@RequestBody Cartao cartao) throws CartaoJaExisteException {
        return ResponseEntity.ok(cartaoService.add_cartao(cartao));
    }

    /**
     * transacao
     * Executa uma trasacao com o cartão
     * 
     * @param cartao com a transacao
     * @return um status indicando se a transação foi efetuada ou nao
     * @throws CartaoNotFoundException
     * @throws SaldoInsuficienteException
     * @throws SenhaInvalidaexception
     */
    @PutMapping("/transacao")
    public ResponseEntity<Status> transacao(@RequestBody Cartao cartao) throws CartaoNotFoundException, SaldoInsuficienteException, SenhaInvalidaexception {
        return ResponseEntity.ok(cartaoService.transacao(cartao));
    }
}
