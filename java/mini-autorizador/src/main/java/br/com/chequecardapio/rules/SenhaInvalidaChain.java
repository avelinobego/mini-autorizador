package br.com.chequecardapio.rules;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.status.Status;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Objects;

@Component
public class SenhaInvalidaChain extends Chain {
    @Override
    protected boolean isValid(Map<String, Object> context) {
        Cartao cartao = (Cartao) context.get("cartao");

        if(cartao.getSenha().equals("123")){
            context.put("status", Status.OK);
            return true;
        }
        context.put("status", Status.SENHA_INVALIDA);
        return false;
    }
}

