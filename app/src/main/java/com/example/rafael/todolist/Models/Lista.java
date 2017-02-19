package com.example.rafael.todolist.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 14/02/2017.
 */

public class Lista {

    private int id;
    private String nome;
    private List<Tarefa> tarefas;


    public Lista() {
        this("");
    }

    public Lista(String nome) {
        this.nome = nome;
        tarefas = new ArrayList<Tarefa>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public List<Tarefa> getTarefas() {
        return tarefas;
    }

    public void setTarefas(List<Tarefa> tarefas) {
        this.tarefas = tarefas;
    }


    public   List<Tarefa> getTarefasConcluidas()
    {
        List<Tarefa> concluidas = new ArrayList<Tarefa>();

        for (Tarefa t: tarefas) {
            if (t.isFeito())
                concluidas.add(t);
        }

        return concluidas;
    }
    private  List<Tarefa> getTarefasNaoConcluidas(){
        List<Tarefa> naoConcluidas = new ArrayList<Tarefa>();

        for (Tarefa t: tarefas) {
            if (!t.isFeito())
                naoConcluidas.add(t);
        }

        return naoConcluidas;
    }


    @Override
    public String toString() {

        return getNome()
                + " - por Fazer: " + getTarefasNaoConcluidas().size()
                + " - Concluidas: " + getTarefasConcluidas().size();
    }
}
