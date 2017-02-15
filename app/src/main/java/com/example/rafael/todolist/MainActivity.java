package com.example.rafael.todolist;

import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private EditText txtItem;
    private ListView lst1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void btnAddClick(View v)
    {
        Toast.makeText(v.getContext(),"entrou",Toast.LENGTH_LONG).show();
        lst1 = (ListView) findViewById(R.id.lst1);
        txtItem = (EditText) findViewById(R.id.txtItem);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(v.getContext(),
                            R.layout.activity_main,
                            R.id.lst1);
        Log.d("Teste",txtItem.getText().toString());


        adapter.add(txtItem.getText().toString());
        lst1.setAdapter(adapter);

    }


}
