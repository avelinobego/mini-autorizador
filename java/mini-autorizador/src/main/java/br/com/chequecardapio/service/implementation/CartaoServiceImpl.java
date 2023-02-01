package br.com.chequecardapio.service.implementation;

import br.com.chequecardapio.chain.*;
import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.exceptions.CartaoJaExisteException;
import br.com.chequecardapio.exceptions.CartaoNotFoundException;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.service.CartaoService;
import br.com.chequecardapio.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;

import java.math.BigDecimal;
import java.util.List;

@Service
public class CartaoServiceImpl implements CartaoService {

    private final CartoesRepository repository;
    private final SenhaInvalidaChain senhaInvalidaChain;
    private final SemSaldoChain semSaldoChain;
    private final EncontrarChain encontrarChain;
    private final TransacaoChain transacaoChain;
    private final EncontrarPeloNumero encontrarPeloNumero;

    @Autowired
    public CartaoServiceImpl(CartoesRepository repository,
            SenhaInvalidaChain senhaInvalidaChain,
            SemSaldoChain semSaldoChain,
            EncontrarChain encontrarChain,
            TransacaoChain transacaoChain,
            EncontrarPeloNumero encontrarPeloNumero) {
        this.repository = repository;
        this.senhaInvalidaChain = senhaInvalidaChain;
        this.semSaldoChain = semSaldoChain;
        this.encontrarChain = encontrarChain;
        this.transacaoChain = transacaoChain;
        this.encontrarPeloNumero = encontrarPeloNumero;
    }

    @Override
    public List<Cartao> cartoes() {
        return repository.findAll();
    }

    @Override
    public Cartao add_cartao(Cartao cartao) throws CartaoJaExisteException {
        try{
        return repository.insert(cartao);
        } catch(DuplicateKeyException ex){
            throw new CartaoJaExisteException(cartao);
        }
    }

    @Override
    public BigDecimal saldo(String numero) throws CartaoNotFoundException {

        Context context = new Context();
        context.setNumero(numero);
        encontrarPeloNumero.execute(context);

        if (context.getStatus() == Status.CARTAO_INEXISTENTE) {
            throw new CartaoNotFoundException(String.format("Cartao com o número %s não encontrado", numero));
        }

        return context.getEncontrado().getValor();
    }

    public Status transacao(Cartao cartao){

        encontrarChain.setNext(senhaInvalidaChain);
        senhaInvalidaChain.setNext(semSaldoChain);
        semSaldoChain.setNext(transacaoChain);

        Context context = new Context();
        context.setCartao(cartao);
        encontrarChain.execute(context);

        return context.getStatus();
    }

}
