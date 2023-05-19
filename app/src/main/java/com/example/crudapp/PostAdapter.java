package com.example.crudapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

public class PostAdapter extends BaseAdapter {

    private Context ctx;
    private List<Post> lista;

    public PostAdapter(Context ctx2, List<Post> lista2){
        ctx = ctx2;
        lista = lista2;
    }

    @Override
    public int getCount() {
        return lista.size();
    }

    @Override
    public Post getItem(int position) {
        return lista.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = null;

        if(convertView == null){
            LayoutInflater inflater = ((Activity)ctx).getLayoutInflater();
            v = inflater.inflate(R.layout.item_lista, null);
        } else {
            v = convertView;
        }

        Post p = getItem(position);

        TextView itemTitulo = (TextView)v.findViewById(R.id.itemTitulo);
        TextView itemCorpo = (TextView)v.findViewById(R.id.itemCorpo);
        TextView itemUserId = (TextView)v.findViewById(R.id.itemUserId);
        TextView itemId = (TextView)v.findViewById(R.id.itemId);

        itemTitulo.setText("Titulo do post: "+ p.getTitulo());
        itemCorpo.setText("Corpo do post: "+p.getCorpo());
        itemUserId.setText("Id Post: "+p.getId());
        itemId.setText("UserId: "+p.getUser_id());

        return v;
    }
}
