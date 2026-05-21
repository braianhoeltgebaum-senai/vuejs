package com.kanban.vuejs.backendspring.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.kanban.vuejs.backendspring.enums.Status;
import com.kanban.vuejs.backendspring.model.Gerenciador;
import com.kanban.vuejs.backendspring.repository.GerenciadorRepository;

@Service
public class GerenciadorService {

    private final GerenciadorRepository repository;

    public GerenciadorService(GerenciadorRepository repository) {
        this.repository = repository;
    }

    public List<Gerenciador> listar() {
        List<Gerenciador> tarefas = repository.findAll();
        System.out.println("Tarefas listadas: " + tarefas);
        return tarefas;
    }

    public Gerenciador salvar(Gerenciador gerenciador) {
        if (gerenciador.getStatus() == null) {
            gerenciador.setStatus(Status.PENDENTE);
        }
        if (gerenciador.getCompleted() == null) {
            gerenciador.setCompleted(false);
        }
        Gerenciador salvo = repository.save(gerenciador);
        System.out.println("Tarefa criada: " + salvo);
        return salvo;
    }

    public Gerenciador atualizar(Long id, Gerenciador gerenciadorAtualizada) {
        Gerenciador gerenciador = repository.findById(id).orElseThrow();

        if (gerenciadorAtualizada.getText() != null) {
            gerenciador.setText(gerenciadorAtualizada.getText());
        }
        if (gerenciadorAtualizada.getStatus() != null) {
            gerenciador.setStatus(gerenciadorAtualizada.getStatus());
        }
        if (gerenciadorAtualizada.getCompleted() != null) {
            gerenciador.setCompleted(gerenciadorAtualizada.getCompleted());
        }

        Gerenciador atualizado = repository.save(gerenciador);
        System.out.println("Tarefa atualizada (id " + id + "): " + atualizado);
        return atualizado;
    }

    public void deletar(Long id) {
        repository.deleteById(id);
        System.out.println("Tarefa deletada (id " + id + ")");
    }
}