package com.example.rafael.todolist.Models;

/**
 * Created by rafae on 14/02/2017.
 */

public class Tarefa {
    private int id;
    private String descricao;
    private boolean feito;

    public Tarefa() {
        this(0,"não informado",false);
    }

    public Tarefa(int id, String descricao, boolean feito) {
        this.id = id;
        this.descricao = descricao;
        this.feito = feito;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public boolean isFeito() {
        return feito;
    }

    public void setFeito(boolean feito) {
        this.feito = feito;
    }

    public int getId() {

        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return getDescricao() + " - " + (isFeito()? "Concluido":"Por Fazer");
    }
}
