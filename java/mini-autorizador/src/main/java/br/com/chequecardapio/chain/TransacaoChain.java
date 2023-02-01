package br.com.chequecardapio.chain;

import java.math.BigDecimal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.status.Status;

@Component
public class TransacaoChain extends Chain {
    private final CartoesRepository repository;

    @Autowired
    public TransacaoChain(CartoesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected boolean isValid(Context context) {
        Cartao cartao = context.getCartao();
        Cartao encontrado = context.getEncontrado();

        BigDecimal add = encontrado.getValor().add(cartao.getValor());
        if (add == null) {
            context.setStatus(Status.TRANSACAO_NAO_EFETUADA);
            return false;
        }
        encontrado.setValor(add);
        context.setCartao(repository.save(encontrado));
        context.setStatus(Status.TRANSACAO_EFETUADA);
        return true;
    }
}
