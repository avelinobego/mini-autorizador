package br.com.chequecardapio.chain;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.status.Status;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import static org.apache.commons.lang3.ObjectUtils.defaultIfNull;

@Component
public class SemSaldoChain extends Chain {

    @Override
    protected boolean isValid(Context context) {
        Cartao encontrado = context.getEncontrado();
        if (defaultIfNull(encontrado.getValor(), BigDecimal.ZERO).compareTo(BigDecimal.ZERO) < 1) {
            context.setStatus(Status.SALDO_INSUFICIENTE);
            return false;
        }
        context.setStatus(Status.OK);
        return true;
    }
}
