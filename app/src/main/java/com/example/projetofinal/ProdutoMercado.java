package com.example.projetofinal;

import classesmodelos.Mercado;
import classesmodelos.Produto;

public class ProdutoMercado{
    private Mercado m;
    private Produto p;

    public ProdutoMercado(Mercado m, Produto p) {
        this.m = m;
        this.p = p;
    }

    //getters e setters
    public Mercado getM() {
        return m;
    }

    public void setM(Mercado m) {
        this.m = m;
    }

    public Produto getP() {
        return p;
    }

    public void setP(Produto p) {
        this.p = p;
    }

}
