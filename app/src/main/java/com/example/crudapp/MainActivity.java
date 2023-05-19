package com.example.crudapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
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

    EditText editTitulo, editCorpo;
    Button btnAdicionar;
    ListView listViewPost;

    private String HOST = "https://jsonplaceholder.typicode.com";

    PostAdapter postAdapter;
    List<Post> lista;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTitulo = (EditText) findViewById(R.id.editTitulo);
        editCorpo = (EditText) findViewById(R.id.editCorpo);

        btnAdicionar = (Button) findViewById(R.id.btnSalvar);

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
                } else if (corpo.isEmpty()){
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

                                    if(result.get("user_id") != null){
                                        Toast.makeText(MainActivity.this,
                                                "Salvo sucesso",
                                                Toast.LENGTH_LONG).show();
                                    } else {
                                        Toast.makeText(MainActivity.this,
                                                "Ocorreu um erro ao salvar",
                                                Toast.LENGTH_LONG).show();
                                    }
                                }
                            });
                }
            }
            public void limparCampos() {
                editCorpo.setText("");
                editTitulo.setText("");
            }
        });

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