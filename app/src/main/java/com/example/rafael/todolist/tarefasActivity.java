package com.example.rafael.todolist;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.menu.MenuBuilder;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import com.example.rafael.todolist.Models.Lista;
import com.example.rafael.todolist.Models.Tarefa;
import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by rafae on 18/02/2017.
 */

public class tarefasActivity extends AppCompatActivity
{
    private ListView lstTarefas;
    private Lista lista;
    private ArrayAdapter<Tarefa> adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefas);
        Intent intent = getIntent();
        lista = (Lista) new Gson().fromJson(intent.getStringExtra("lista"),Lista.class);
        if(lista.getTarefas() == null)
            lista.setTarefas(new ArrayList<Tarefa>());
        lstTarefas = (ListView) findViewById(R.id.tarefasList);
        adapter = new ArrayAdapter<Tarefa>(this,android.R.layout.simple_expandable_list_item_1,lista.getTarefas());
        lstTarefas.setAdapter(adapter);
        lstTarefas.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Tarefa feita")
                        .setMessage("Mudar Status da Tarefa " + lista.getTarefas().get(position) + " ?")
                        .setPositiveButton("Concluir", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lista.getTarefas().get(position).setFeito(true);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Não Feito", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                lista.getTarefas().get(position).setFeito(false);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .show();

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = getIntent();
        intent.putExtra("lista",(new Gson()).toJson(lista,Lista.class));
        setResult(1,intent);
        super.onBackPressed();
    }

    public void addTarefa(View view)
    {
        Log.d("Tarefa","Entrou");
        String txt = ((EditText) findViewById(R.id.txtxAddTarefa)).getText().toString();
        Tarefa t = new Tarefa();
        t.setDescricao(txt);
        t.setFeito(false);
        Log.d("Tarefa","Tarefa: " + t.toString());
        lista.getTarefas().add(t);
        adapter.notifyDataSetChanged();
        ((EditText) findViewById(R.id.txtxAddTarefa)).setText("");
    }
}
