package br.com.chequecardapio.repository;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.chequecardapio.entity.Cartao;

@SpringBootTest
public class Repotest {

    private static final Logger log = LoggerFactory.getLogger(Repotest.class);

    @Autowired
    private CartoesRepository repository;

    @Test
    public void testFindaAll() {
        List<Cartao> result = repository.findAll();
        for (Cartao c: result){
            log.info(c.toString());
        }
    }

}
