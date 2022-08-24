package br.com.chequecardapio.controller;

import br.com.chequecardapio.entity.Cartao;
import br.com.chequecardapio.service.CartaoService;
import br.com.chequecardapio.service.implementation.CartaoServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@Controller("/")
public class AutorizadorController {

    private final CartaoService cartaoService;

    @Autowired
    public AutorizadorController(CartaoService cartaoService) {
        this.cartaoService = cartaoService;
    }
    @GetMapping("/cartoes")
    public ResponseEntity<List<Cartao>> all_cartoes(){
        return ResponseEntity.ok(cartaoService.cartoes());
    }
    @PostMapping("/cartao")
    public ResponseEntity<Cartao> add_cartoes(@RequestBody Cartao cartao){
        return ResponseEntity.ok(cartao);
    }

}
