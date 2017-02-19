package com.example.rafael.todolist.DAO;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.List;

/**
 * Created by rafae on 18/02/2017.
 */

public abstract class AbstractDao extends SQLiteOpenHelper{
    private static final String dbName = "myToDoList";

    private static final int VERSAO = 1;
    protected SQLiteDatabase db;

    protected String tableName;
    public AbstractDao(Context context) {
        super(context,dbName,null,VERSAO);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "Create table Lista(" +
                " id integer primary key autoincrement " +
                " nome Text " +
                ");";
        db.execSQL(sql);
        sql = "Create table Tarefa(" +
                " id integer primary key autoincrement " +
                " descricao Text " +
                " feito integer " +
                ");";
        db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }


    public String excluir(int id) {
        try {
            db = getReadableDatabase();
            db.delete(tableName, "id = " + id, null);
            db.close();
        }catch (Exception e)
        {
            return "Falha ao Deletar Lista";
        }
        return "Lista deletada!";
    }


}
