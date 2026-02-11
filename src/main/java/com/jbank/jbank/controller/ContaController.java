package com.jbank.jbank.controller;

import com.jbank.jbank.dto.*;
import com.jbank.jbank.service.ContaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;
import java.util.List;

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

    @PutMapping("/{id}/deposito")
    public ResponseEntity<Void> depositar(@PathVariable Long id, @RequestBody DepositoDTO dados){
        service.depositar(id, dados.valor());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}/saque")
    public ResponseEntity<Void> sacar(@PathVariable Long id, @RequestBody SaqueDTO dados){
        service.sacar(id, dados.valor());

        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{idOrigem}/transferencia")
    public ResponseEntity<Void> transferir(@PathVariable Long idOrigem, @RequestBody TransferenciaDTO dados){
        service.transferir(idOrigem, dados.idDestino(), dados.valor());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/extrato")
    public ResponseEntity<List<ExtratoDTO>> verExtrato(@PathVariable Long id){
        var extrato = service.listarExtrato(id);
        return ResponseEntity.ok(extrato);
    }
}
