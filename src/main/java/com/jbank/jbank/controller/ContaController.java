package com.jbank.jbank.controller;

import com.jbank.jbank.dto.ContaDTO;
import com.jbank.jbank.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service){
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<ContaDTO> criarConta(@RequestBody ContaDTO dados, UriComponentsBuilder uriBuilder){
        ContaDTO contaCriada = service.salvar(dados);

        URI uri = uriBuilder.path("/contas/{id}").buildAndExpand(contaCriada.getId()).toUri();

        return ResponseEntity.created(uri).body(contaCriada);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ContaDTO> buscarPorId(@PathVariable Long id){
        ContaDTO conta = service.buscarPorId(id);
        return ResponseEntity.ok(conta);
    }

}
