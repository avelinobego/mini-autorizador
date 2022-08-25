package br.com.chequecardapio.controller;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.service.CartaoService;
import br.com.chequecardapio.service.implementation.CartaoServiceImpl;
import br.com.chequecardapio.status.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller("/")
public class AutorizadorController {

    private final CartaoService cartaoService;

    @Autowired
    public AutorizadorController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }
    @GetMapping("/cartoes")
    public ResponseEntity<List<Cartao>> all(){
        return ResponseEntity.ok(cartaoService.cartoes());
    }

    @GetMapping("/cartoes/{numero}")
    public ResponseEntity<Cartao> saldo(@PathVariable String numero){
        return ResponseEntity.ok(cartaoService.saldo(numero));
    }


    @PostMapping("/cartao")
    public ResponseEntity<Cartao> add(@RequestBody Cartao cartao){
        return ResponseEntity.ok(cartaoService.add_cartao(cartao));
    }

    @PostMapping("/transacao")
    public ResponseEntity<Status> transacao(@RequestBody Cartao cartao){
        return ResponseEntity.ok(cartaoService.transacao(cartao));
    }
}
