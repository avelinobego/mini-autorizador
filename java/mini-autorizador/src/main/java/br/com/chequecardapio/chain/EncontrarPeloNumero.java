package br.com.chequecardapio.chain;

import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.status.Status;

@Component
public class EncontrarPeloNumero extends Chain {

    private final CartoesRepository cartoesRepository;

    public EncontrarPeloNumero(CartoesRepository cartoesRepository) {
        this.cartoesRepository = cartoesRepository;
    }

    @Override
    protected boolean isValid(Context context) {
        Optional<Cartao> result = this.cartoesRepository.findById(context.getNumero());
        if (result.isEmpty()){
            context.setStatus(Status.CARTAO_INEXISTENTE);
            return false;
        }
        context.setEncontrado(result.get());
        return true;
    }

}
