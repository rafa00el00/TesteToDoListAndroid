package com.example.rafael.todolist.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;

import com.example.rafael.todolist.Models.Lista;
import com.example.rafael.todolist.Models.Tarefa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rafae on 19/02/2017.
 */

public class DaoLista extends AbstractDao implements IDao<Lista> {

    private DaoTarefa daoTarefa;
    public DaoLista(Context context) {
        super(context);
        tableName = "lista";
        daoTarefa = new DaoTarefa(context);
    }

    @Override
    public String insert(Lista obj) {
        db = getWritableDatabase();

        ContentValues valores = new ContentValues();

        valores.put("nome",obj.getNome());

        Long id = db.insert(tableName,null,valores);
        db.close();
        obj.setId(id.intValue());

        for (Tarefa t: obj.getTarefas()) {
            t.setIdLista(obj.getId());
            daoTarefa.insert(t);
        }

        if (id == -1)
            return "Não Foi possivel inserir o registro";


        return "Lista Inserida";


    }

    @Override
    public String update(Lista obj) {
        db = getWritableDatabase();
        String where = " id = " + obj.getId();
        ContentValues valores = new ContentValues();

        valores.put("nome",obj.getNome());

        db.update(tableName,valores,where,null);
        db.close();

        for (Tarefa t: obj.getTarefas()) {
            if (t.getId() != 0){
                daoTarefa.update(t);
            }else {
                t.setIdLista(obj.getId());
                daoTarefa.insert(t);
            }
        }


        return "Lista alterada";

    }

    @Override
    public List<Lista> consultarLista(Object obj) {
        db = getReadableDatabase();
        List<Lista> listas = new ArrayList<Lista>();
        Cursor cursor;
        String where= null;

        if (obj instanceof Integer)
            where = " id = " + obj;

        cursor = db.query(tableName,new String[]{"id","nome"},where,null,null,null,null);

        Lista l;
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            l = new Lista();
            l.setId(cursor.getInt(0));
            l.setNome(cursor.getString(1));
            l.setTarefas(daoTarefa.consultarLista(l));
            listas.add(l);
            cursor.moveToNext();
        }
        db.close();;
        return listas;
    }

    @Override
    public void deletar(Lista obj) {
        for (Tarefa t : obj.getTarefas()) {
            daoTarefa.deletar(t);
        }

        excluir(obj.getId());

    }

    @Override
    public Lista consultar(Object obj) {
        List<Lista> listas = consultarLista(obj);
        if (listas.size() > 0)
            return listas.get(0);

        return null;
    }
}
