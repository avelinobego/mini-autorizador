package br.com.chequecardapio.chain;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class EncontrarChain extends Chain {
    private final CartoesRepository repository;

    @Autowired
    public EncontrarChain(CartoesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected boolean isValid(Context context) {
        Cartao cartao = context.getCartao();
        Optional<Cartao> result = repository.findById(cartao.getNumero());
        if (result.isEmpty()) {
            context.setStatus(Status.CARTAO_INEXISTENTE);
            return false;
        }
        Cartao byNumero = result.get();
        context.setEncontrado(byNumero);
        return true;
    }
}
