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

public class DaoTarefa extends AbstractDao implements IDao<Tarefa> {

    public DaoTarefa(Context context) {
        super(context);
        tableName = "Tarefa";
    }

    @Override
    public String insert(Tarefa obj) {
        db = getWritableDatabase();
        ContentValues valores;

        valores = new ContentValues();
        valores.put("descricao",obj.getDescricao());
        valores.put("feito",obj.isFeito() ? 1 : 0);
        valores.put("idLista",obj.getIdLista());

        Long id = db.insert(tableName,null,valores);
        db.close();
        if (id == -1)
            return "Não Foi possivel inserir o registro";

        obj.setId(id.intValue());
        return "Tarefa Inserida";
    }

    @Override
    public String update(Tarefa obj) {
        db = getWritableDatabase();
        ContentValues valores;
        String where = " id = " + obj.getId();

        valores = new ContentValues();
        valores.put("descricao",obj.getDescricao());
        valores.put("feito",obj.isFeito() ? 1 : 0);
        valores.put("idLista",obj.getIdLista());

        db.update(tableName,valores,where,null);
        db.close();
        return "Tarefa Alterada";
    }

    @Override
    public List<Tarefa> consultarLista(Object obj) {
        String where = null;
        List<Tarefa> tarefas = new ArrayList<Tarefa>();
        if(obj instanceof Integer)
            where = " id = " + obj;
        else if (obj instanceof Lista)
            where = " idLista = " +  ((Lista) obj).getId();

        Cursor cursor;
        db = getReadableDatabase();
        
        cursor = db.query(tableName,new String[]{"id","descricao","feito","idLista"},where,null,null,null,null);

        Tarefa t;
        try {
            cursor.moveToFirst();
            while (!cursor.isAfterLast()) {
                t = new Tarefa();
                t.setId(cursor.getInt(0));
                t.setDescricao(cursor.getString(1));
                t.setFeito(cursor.getInt(2) == 1);
                t.setIdLista(cursor.getInt(3));
                tarefas.add(t);
                cursor.moveToNext();
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        db.close();
        return tarefas;
    }


    @Override
    public void deletar(Tarefa obj) {
        excluir(obj.getId());
    }

    @Override
    public Tarefa consultar(Object obj) {
        List<Tarefa> lst = consultarLista(obj);
        if (lst.size() > 0)
            return lst.get(0);

        return null;
    }
}
