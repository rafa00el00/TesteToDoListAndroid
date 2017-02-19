package com.example.rafael.todolist;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.rafael.todolist.Models.Lista;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtItem;
    private ListView lst1;
    private ArrayList<Lista> lista;
    private Lista listaSelecionada;
    private ArrayAdapter<Lista> adapter;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lst1 = (ListView) findViewById(R.id.lst1);
        lista = new ArrayList<Lista>();
        adapter = new ArrayAdapter<Lista>(this,android.R.layout.simple_list_item_1,lista);
        lst1.setAdapter(adapter);
        lst1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent =new Intent(view.getContext(),tarefasActivity.class);
                listaSelecionada = lista.get(position);
                intent.putExtra("lista",
                        new Gson().toJson( lista.get(position) ) );
                startActivityForResult(intent,1);
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode)
        {
            case 1:
                Log.d("Result1",data.getStringExtra("lista"));
                Lista ret = (new Gson()).fromJson(data.getStringExtra("lista"),Lista.class);
                listaSelecionada.setTarefas(ret.getTarefas());
                adapter.notifyDataSetChanged();
                break;
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_contextual_list, menu);
        return true;
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {

        return true;
    }

    public void btnAddClick(View v)
    {
        String txt = ((EditText) findViewById(R.id.txtItem)).getText().toString();
        Lista l = new Lista();
        l.setNome(txt);

        lista.add(l);
        adapter.notifyDataSetChanged();
        ((EditText) findViewById(R.id.txtItem)).setText("");
    }

    public void lstOnClick(View v){
        Toast.makeText(null,"clicou na lista",Toast.LENGTH_SHORT).show();
    }


}
