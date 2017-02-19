package com.example.rafael.todolist.DAO;

import java.util.List;

/**
 * Created by rafae on 19/02/2017.
 */

public interface IDao<T> {

    public String insert(T obj);
    public  String update(T obj);
    public List<T> consultarLista(Object obj);
    public T consultar(Object obj);


}
