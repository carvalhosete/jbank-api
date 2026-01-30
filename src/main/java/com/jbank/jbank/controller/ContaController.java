package com.jbank.jbank.controller;

import com.jbank.jbank.dto.ContaDTO;
import com.jbank.jbank.service.ContaService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/contas")
public class ContaController {

    private final ContaService service;

    public ContaController(ContaService service){
        this.service = service;
    }

    @PostMapping
    public ContaDTO criarConta(@RequestBody ContaDTO dadosRecebidos){

        return service.salvar(dadosRecebidos);
    }

}
