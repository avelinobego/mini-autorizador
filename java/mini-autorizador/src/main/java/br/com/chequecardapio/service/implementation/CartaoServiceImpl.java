package br.com.chequecardapio.service.implementation;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.repository.CartoesRepository;
import br.com.chequecardapio.service.CartaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartaoServiceImpl implements CartaoService {

    private final CartoesRepository repository;

    @Autowired
    public CartaoServiceImpl(CartoesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Cartao> cartoes() {
        return repository.findAll();
    }

}
