package br.com.chequecardapio.rules;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.status.Status;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

@Component
public class TransacaoChain extends Chain {
    private final CartoesRepository repository;

    @Autowired
    public TransacaoChain(CartoesRepository repository) {
        this.repository = repository;
    }

    @Override
    protected boolean isValid(Map<String, Object> context) {
        Cartao cartao = (Cartao) context.get("cartao");
        Cartao encontrado = (Cartao) context.get("encontrado");

        BigDecimal add = encontrado.getValor().add(cartao.getValor());
        encontrado.setValor(add);
        repository.save(encontrado);
        context.put("status", Status.TRANSACAO_EFETUADA);
        return true;
    }
}
