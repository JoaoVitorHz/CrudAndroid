package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText editTitulo, editCorpo, editId;
    Button btnAdicionar, btnEditar, btnExcluir;
    ListView listViewPost;

    private String HOST = "https://jsonplaceholder.typicode.com";

    PostAdapter postAdapter;
    List<Post> lista;

    private int itemClicado;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editCorpo = (EditText) findViewById(R.id.editCorpo);
        editId = (EditText) findViewById(R.id.editId);

        btnAdicionar = (Button) findViewById(R.id.btnSalvar);
        btnEditar = (Button) findViewById(R.id.btnEditar);
        btnExcluir = (Button) findViewById(R.id.btnExcluir);

        listViewPost = (ListView) findViewById(R.id.listViewPost);

        lista = new ArrayList<Post>();
        postAdapter = new PostAdapter(MainActivity.this, lista);

        listViewPost.setAdapter(postAdapter);

        listaContato();

        btnAdicionar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String titulo = editTitulo.getText().toString();
                String corpo = editCorpo.getText().toString();

                String url = HOST + "/posts";

                if(titulo.isEmpty()) {
                    editTitulo.setError("O titulo é obrigatorio");
                }
                else if (corpo.isEmpty()){
                    editCorpo.setError("O corpo é obrigatorio");
                } else {
                    JsonObject json = new JsonObject();
                    json.addProperty("title", titulo);
                    json.addProperty("body", corpo);
                    json.addProperty("userId", 1);

                    Ion.with(MainActivity.this)
                            .load(url)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    Toast.makeText(MainActivity.this,
                                            "Salvo com sucesso",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                    limparCampos();
                }
            }

        });

        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String titulo = editTitulo.getText().toString();
                String corpo = editCorpo.getText().toString();
                String id = editId.getText().toString();

                String url = HOST + "/posts";

                if(titulo.isEmpty()) {
                    editTitulo.setError("O titulo é obrigatorio");
                }
                else if (corpo.isEmpty()){
                    editCorpo.setError("O corpo é obrigatorio");
                }
                else {
                    JsonObject json = new JsonObject();
                    json.addProperty("title", titulo);
                    json.addProperty("body", corpo);
                    json.addProperty("userId", id);

                    Ion.with(MainActivity.this)
                            .load(url)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    Toast.makeText(MainActivity.this,
                                            "Editado com sucesso",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                    limparCampos();
                }
            }
        });

        btnExcluir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = editId.getText().toString();

                if(id.isEmpty()){
                    Toast.makeText(MainActivity.this,
                            "Nenhum contatao selecioando",
                            Toast.LENGTH_LONG).show();
                } else {
                    String url = HOST + "/posts";

                    JsonObject json = new JsonObject();
                    json.addProperty("userId", id);

                    Ion.with(MainActivity.this)
                            .load(url)
                            .setJsonObjectBody(json)
                            .asJsonObject()
                            .setCallback(new FutureCallback<JsonObject>() {
                                @Override
                                public void onCompleted(Exception e, JsonObject result) {
                                    Toast.makeText(MainActivity.this,
                                            "Apagado com sucesso",
                                            Toast.LENGTH_LONG).show();
                                }
                            });

                    limparCampos();
                }
            }
        });

        listViewPost.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {

                Post p = (Post) adapterView.getAdapter().getItem(position);

                editTitulo.setText(p.getTitulo());
                editCorpo.setText(p.getCorpo());
                editId.setText(p.getId());

                itemClicado = position;
            }
        });


    }
    public void limparCampos() {
        editCorpo.setText("");
        editTitulo.setText("");
        editId.setText("");
    }

    private  void listaContato(){
        String url = HOST + "/posts";

        Ion.with(getBaseContext())
                .load(url)
                .asJsonArray()
                .setCallback(new FutureCallback<JsonArray>() {
                    @Override
                    public void onCompleted(Exception e, JsonArray result) {

                        for(int i = 0; i < result.size(); i++) {

                            JsonObject obj = result.get(i).getAsJsonObject();

                            Post p = new Post();

                            p.setId(obj.get("id").getAsString());
                            p.setUser_id(obj.get("userId").getAsString());
                            p.setTitulo(obj.get("title").getAsString());
                            p.setCorpo(obj.get("body").getAsString());

                            lista.add(p);
                        }

                        postAdapter.notifyDataSetChanged();

                    }
                });
    }
}