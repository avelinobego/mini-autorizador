package br.com.chequecardapio.rules;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.status.Status;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;

import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Component
public class SemSaldoChain extends Chain {

    @Override
    protected boolean isValid(Map<String, Object> context) {
        Cartao encontrado = (Cartao) context.get("encontrado");
        if (defaultIfNull(encontrado.getValor(), BigDecimal.ZERO).compareTo(BigDecimal.ZERO) < 1) {
            context.put("status", Status.SALDO_INSUFICIENTE);
            return false;
        }
        context.put("status", Status.OK);
        return true;
    }
}
