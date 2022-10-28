package com.example.projetofinal;

public class ItemPost {

    //variaveis
    private int id;
    private int estrelas;
    private String obs;
    private String comentario;
    private String user;
    private String nomeMercado;

    //construtor
    public ItemPost(int id, int estrelas, String obs, String comentario, String user, String nomeMercado) {
        this.id = id;
        this.estrelas = estrelas;
        this.obs = obs;
        this.comentario = comentario;
        this.user = user;
        this.nomeMercado = nomeMercado;
    }

    //getter e setter
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getEstrelas() {
        return estrelas;
    }

    public void setEstrelas(int estrelas) {
        this.estrelas = estrelas;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getNomeMercado() {
        return nomeMercado;
    }

    public void setNomeMercado(String nomeMercado) {
        this.nomeMercado = nomeMercado;
    }















}
