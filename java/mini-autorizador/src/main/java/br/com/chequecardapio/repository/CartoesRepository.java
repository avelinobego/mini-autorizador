package br.com.chequecardapio.repository;

import br.com.chequecardapio.entity.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartoesRepository extends MongoRepository<Cartao, String> {
    Cartao findByNumero(String numero);
}
