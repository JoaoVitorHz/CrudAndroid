package com.example.crudapp;

public class Post {

    private String id;
    private String user_id;
    private String titulo;
    private String corpo;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String title) {
        this.titulo = title;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String body) {
        this.corpo = body;
    }
}
