package br.com.chequecardapio.rules;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class EncontradoChain extends Chain {
    private final CartoesRepository repository;

    @Autowired
    public EncontradoChain(CartoesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected boolean isValid(Map<String, Object> context) {
        Cartao cartao = (Cartao) context.get("cartao");
        Cartao byNumero = repository.findById(cartao.getNumero()).orElse(null);
        if (Objects.isNull(byNumero)) {
            context.put("status", Status.CARTAO_INEXISTENTE);
            return false;
        }
        context.put("encontrado", byNumero);
        return true;
    }
}
