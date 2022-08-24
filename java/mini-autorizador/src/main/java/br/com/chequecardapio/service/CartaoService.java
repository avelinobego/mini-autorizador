package br.com.chequecardapio.service;

import br.com.chequecardapio.entity.Cartao;
import org.springframework.stereotype.Service;

import java.util.List;

public interface CartaoService {

    List<Cartao> cartoes();
}
