package br.com.chequecardapio.service.implementation;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.rules.*;
import br.com.chequecardapio.service.CartaoService;
import br.com.chequecardapio.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartaoServiceImpl implements CartaoService {

    private final CartoesRepository repository;
    private final SenhaInvalidaChain senhaInvalidaChain;
    private final SemSaldoChain semSaldoChain;
    private final EncontradoChain encontradoChain;
    private final TransacaoChain transacaoChain;

    @Autowired
    public CartaoServiceImpl(CartoesRepository repository, SenhaInvalidaChain senhaInvalidaChain, SemSaldoChain semSaldoChain, EncontradoChain encontradoChain, TransacaoChain transacaoChain) {
        this.repository = repository;
        this.senhaInvalidaChain = senhaInvalidaChain;
        this.semSaldoChain = semSaldoChain;
        this.encontradoChain = encontradoChain;
        this.transacaoChain = transacaoChain;
    }

    @Override
    public List<Cartao> cartoes() {
        return repository.findAll();
    }

    @Override
    public Cartao add_cartao(Cartao cartao) {
        return repository.save(cartao);
    }

    @Override
    public Cartao saldo(String numero) {
        return repository.findByNumero(numero);
    }

    public Status transacao(Cartao cartao) {

        senhaInvalidaChain.setNext(encontradoChain);
        encontradoChain.setNext(semSaldoChain);
        semSaldoChain.setNext(transacaoChain);

        Map<String, Object> context = new HashMap<>();
        context.put("cartao", cartao);
        senhaInvalidaChain.execute(context);

        return (Status) context.get("status");
    }

}
