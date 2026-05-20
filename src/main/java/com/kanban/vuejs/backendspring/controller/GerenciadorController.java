package com.kanban.vuejs.backendspring.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.kanban.vuejs.backendspring.enums.Status;
import com.kanban.vuejs.backendspring.model.Gerenciador;
import com.kanban.vuejs.backendspring.service.GerenciadorService;

@RestController
@RequestMapping("/tarefas")
@CrossOrigin("*")
public class GerenciadorController {

    private final GerenciadorService service;

    public GerenciadorController(GerenciadorService service) {
        this.service = service;
    }

    @GetMapping
    public List<Gerenciador> listar() {
        return service.listar();
    }

    @PostMapping
    public Gerenciador criar(@RequestBody Gerenciador gerenciador) {

        gerenciador.setStatus(Status.PENDENTE);
        gerenciador.setCompleted(false);

        return service.salvar(gerenciador);
    }

    @PutMapping("/{id}")
    public Gerenciador atualizar(
            @PathVariable Long id,
            @RequestBody Gerenciador gerenciador) {

        return service.atualizar(id, gerenciador);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Long id) {
        service.deletar(id);
    }
}