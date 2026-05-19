package com.kanban.vuejs.backendspring.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.kanban.vuejs.backendspring.model.Gerenciador;

public interface GerenciadorRepository extends JpaRepository<Gerenciador, Long> {
}