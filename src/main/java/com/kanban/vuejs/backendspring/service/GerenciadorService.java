package com.kanban.vuejs.backendspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kanban.vuejs.backendspring.model.Gerenciador;
import com.kanban.vuejs.backendspring.repository.GerenciadorRepository;

@Service
public class GerenciadorService {

    private final GerenciadorRepository repository;

    public GerenciadorService(GerenciadorRepository repository) {
        this.repository = repository;
    }

    public List<Gerenciador> listar() {
        return repository.findAll();
    }

    public Gerenciador salvar(Gerenciador gerenciador) {
        return repository.save(gerenciador);
    }

    public void deletar(Long id) {
        repository.deleteById(id);
    }

    public Gerenciador atualizar(Long id, Gerenciador gerenciadorAtualizada) {

        Gerenciador gerenciador = repository.findById(id)
                .orElseThrow();

        gerenciador.setText(gerenciadorAtualizada.getText());
        gerenciador.setCompleted(gerenciadorAtualizada.isCompleted());

        return repository.save(gerenciador);
    }
}