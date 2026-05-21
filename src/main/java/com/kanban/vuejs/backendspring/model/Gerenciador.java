package com.kanban.vuejs.backendspring.model;

import com.kanban.vuejs.backendspring.enums.Status;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Gerenciador {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String text;

    private Boolean completed;

    @Enumerated(EnumType.STRING)
    private Status status;
}