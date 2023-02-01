package br.com.chequecardapio.chain;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.status.Status;

import org.springframework.stereotype.Component;

@Component
public class SenhaInvalidaChain extends Chain {

    @Override
    protected boolean isValid(Context context) {

        Cartao cartao = context.getCartao();
        Cartao encontrado = context.getEncontrado();

        if (encontrado.getSenha().equals(cartao.getSenha())) {
            context.setStatus(Status.OK);
            return true;
        }
        context.setStatus(Status.SENHA_INVALIDA);
        return false;
    }
}
