<script setup>
import { ref, onMounted } from "vue";
import api from "./services/api";

const tarefas = ref([]);
const novaTarefa = ref("");

const carregarTarefas = async () => {
  const response = await api.get("/tarefas");
  tarefas.value = response.data;
};

const criarTarefa = async () => {
  if (!novaTarefa.value.trim()) return;
  await api.post("/tarefas", { text: novaTarefa.value });
  novaTarefa.value = "";
  carregarTarefas();
};

const moverStatus = async (tarefa, novoStatus) => {
  await api.put(`/tarefas/${tarefa.id}`, {
    text: tarefa.text,
    completed: tarefa.completed,
    status: novoStatus,
  });
  carregarTarefas();
};

const excluir = async (id) => {
  await api.delete(`/tarefas/${id}`);
  carregarTarefas();
};

onMounted(() => {
  carregarTarefas();
});
</script>

<template>
  <div class="page">
    <div class="header">
      <span class="header-icon"></span>
      <div>
        <h1>Meu Quadro Kanban</h1>
        <p>Gerencie suas tarefas em tempo real</p>
      </div>
    </div>

    <div class="input-area">
      <input
        v-model="novaTarefa"
        placeholder="Nova tarefa..."
        @keyup.enter="criarTarefa"
      />
      <button class="btn-criar" @click="criarTarefa">Criar</button>
    </div>

    <div class="kanban">

      <!-- Pendentes -->
      <div class="coluna">
        <div class="col-header">
          <span> Pendentes</span>
          <span class="badge badge-amarelo">{{ tarefas.filter(t => t.status === 'PENDENTE').length }}</span>
        </div>
        <div
          v-for="tarefa in tarefas.filter(t => t.status === 'PENDENTE')"
          :key="tarefa.id"
          class="card"
        >
          <p>{{ tarefa.text }}</p>
          <div class="card-acoes">
            <button class="btn-excluir" @click="excluir(tarefa.id)">🗑 Excluir</button>
            <button class="btn-avancar" @click="moverStatus(tarefa, 'EXECUCAO')">Iniciar →</button>
          </div>
        </div>
        <p v-if="tarefas.filter(t => t.status === 'PENDENTE').length === 0" class="vazia">Nenhuma tarefa</p>
      </div>

      <!-- Em Execução -->
      <div class="coluna">
        <div class="col-header">
          <span> Em Execução</span>
          <span class="badge badge-azul">{{ tarefas.filter(t => t.status === 'EXECUCAO').length }}</span>
        </div>
        <div
          v-for="tarefa in tarefas.filter(t => t.status === 'EXECUCAO')"
          :key="tarefa.id"
          class="card"
        >
          <p>{{ tarefa.text }}</p>
          <div class="card-acoes">
            <button class="btn-excluir" @click="excluir(tarefa.id)">🗑 Excluir</button>
            <div>
              <button class="btn-voltar" @click="moverStatus(tarefa, 'PENDENTE')">← Voltar</button>
              <button class="btn-avancar" @click="moverStatus(tarefa, 'CONCLUIDA')">Concluir →</button>
            </div>
          </div>
        </div>
        <p v-if="tarefas.filter(t => t.status === 'EXECUCAO').length === 0" class="vazia">Nenhuma tarefa</p>
      </div>

      <!-- Concluídas -->
      <div class="coluna">
        <div class="col-header">
          <span> Concluídas</span>
          <span class="badge badge-verde">{{ tarefas.filter(t => t.status === 'CONCLUIDA').length }}</span>
        </div>
        <div
          v-for="tarefa in tarefas.filter(t => t.status === 'CONCLUIDA')"
          :key="tarefa.id"
          class="card"
        >
          <p>{{ tarefa.text }}</p>
          <div class="card-acoes">
            <button class="btn-excluir" @click="excluir(tarefa.id)">🗑 Excluir</button>
            <button class="btn-voltar" @click="moverStatus(tarefa, 'EXECUCAO')">← Voltar</button>
          </div>
        </div>
        <p v-if="tarefas.filter(t => t.status === 'CONCLUIDA').length === 0" class="vazia">Nenhuma tarefa</p>
      </div>

    </div>
  </div>
</template>

<style>
* { box-sizing: border-box; margin: 0; padding: 0; }

body {
  font-family: 'Segoe UI', sans-serif;
  background: #0f1117;
  color: #e2e8f0;
  min-height: 100vh;
  width: 100%;
}

.page {
  padding: 2rem;
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  padding-top: 5rem;
}

.header {
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 1rem;
  margin-bottom: 1.5rem;
  text-align: center;
}

.header-icon {
  font-size: 2.5rem;
}

.header h1 {
  font-size: 1.8rem;
  color: #e2e8f0;
  text-align: center;
}

.header p {
  color: #64748b;
  font-size: 0.9rem;
  margin-top: 2px;
  text-align: center;
}

.input-area {
  display: flex;
  gap: 0.5rem;
  margin: 0 auto 1.5rem;

  width: 100%;
  max-width: 520px;
}

input {
  flex: 1;
  padding: 0.6rem 1rem;
  border-radius: 8px;
  border: 1px solid #334155;
  background: #1e2533;
  color: #e2e8f0;
  font-size: 0.95rem;
  outline: none;
}

input:focus {
  border-color: #38bdf8;
}

.btn-criar {
  padding: 0.6rem 1.2rem;
  background: #38bdf8;
  color: #0f1117;
  border: none;
  border-radius: 8px;
  font-weight: 700;
  cursor: pointer;
  font-size: 0.9rem;
}

.btn-criar:hover { opacity: 0.85; }

.kanban {
  display: flex;
  flex-direction: row;
  gap: 1rem;
  justify-content: center;
  align-items: flex-start;

  width: 100%;
  max-width: 1200px;
}

.coluna {
  flex: 1;
  min-width: 250px;
  max-width: 350px;
  background: #1a2035;
  border: 1px solid #2d3a52;
  border-radius: 12px;
  padding: 1rem;
  min-height: 200px;
}

.col-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 1rem;
  font-weight: 600;
  font-size: 0.95rem;
  white-space: nowrap;
}

.badge {
  font-size: 0.75rem;
  font-weight: 700;
  padding: 2px 10px;
  border-radius: 999px;
}

.badge-amarelo { background: #422006; color: #fbbf24; }
.badge-azul    { background: #0c2a4a; color: #38bdf8; }
.badge-verde   { background: #052e16; color: #4ade80; }

.card {
  background: #0f1828;
  border: 1px solid #2d3a52;
  border-radius: 8px;
  padding: 0.75rem 1rem;
  margin-bottom: 0.75rem;
}

.card p {
  font-size: 0.9rem;
  margin-bottom: 0.6rem;
  font-weight: 500;
}

.card-acoes {
  display: flex;
  justify-content: space-between;
  align-items: center;
  gap: 0.4rem;
}

.btn-excluir {
  background: none;
  border: none;
  color: #f87171;
  font-size: 0.8rem;
  cursor: pointer;
  padding: 0;
}

.btn-excluir:hover { opacity: 0.7; }

.btn-voltar {
  font-size: 0.78rem;
  padding: 3px 10px;
  border: 1px solid #334155;
  border-radius: 6px;
  background: #1e2533;
  color: #94a3b8;
  cursor: pointer;
  margin-right: 4px;
}

.btn-avancar {
  font-size: 0.78rem;
  padding: 3px 10px;
  border-radius: 6px;
  border: none;
  background: #38bdf8;
  color: #0f1117;
  font-weight: 700;
  cursor: pointer;
}

.btn-voltar:hover, .btn-avancar:hover { opacity: 0.85; }

.vazia {
  text-align: center;
  color: #475569;
  font-size: 0.85rem;
  padding: 2rem 0;
}

@media (max-width: 768px) {
  .kanban { flex-direction: column; }
}
</style>