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

import com.example.rafael.todolist.DAO.DaoLista;
import com.example.rafael.todolist.Models.Lista;
import com.facebook.stetho.Stetho;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private EditText txtItem;
    private ListView lst1;
    private ArrayList<Lista> lista;
    private Lista listaSelecionada;
    private ArrayAdapter<Lista> adapter;
    DaoLista daoLista;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Stetho.initializeWithDefaults(this);
        setContentView(R.layout.activity_main);
        lst1 = (ListView) findViewById(R.id.lst1);
        daoLista = new DaoLista(this);
        Log.i("Sair","Iniciou");
        lista = (ArrayList<Lista>) daoLista.consultarLista(null);
        //lista = new ArrayList<Lista>();

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
        lst1.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
                new AlertDialog.Builder(view.getContext())
                        .setTitle("Deletar")
                        .setMessage("Deseja deletar " + lista.get(position) + " ?")
                        .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                daoLista.deletar(lista.get(position));
                                lista.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        })
                        .setNegativeButton("Não",null)
                        .show();

                return true;
            }
        });

    }

    @Override
    protected void onStop() {
        Log.i("Sair","On Stop Teste");
        salvarLista();
        super.onStop();
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
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.salvarLista:
                String resp;
                resp = salvarLista();
                Toast.makeText(this,resp,Toast.LENGTH_LONG).show();
                break;
        }
        return true;
    }

    private String salvarLista()
    {
        String resp = "Nenhuma lista para Adicionar";
        for (Lista l : lista)
        {
            Log.i("Sair",l.getId() + " - Lista");
            if(l.getId() == 0) {
                resp = daoLista.insert(l);
            }else{
                resp = daoLista.update(l);
            }
        }
        return resp;
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
