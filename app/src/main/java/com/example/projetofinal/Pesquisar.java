package com.example.projetofinal;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import classesmodelos.Postagem;
import classesmodelos.Produto;
import classesmodelos.ProdutoMercado;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pesquisar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pesquisar extends Fragment {

    //essa classe msotrar o resultado da pesquisa de mercados
    //recebera dois layouts para o recycler view
    View view;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;

    public static Pesquisar newInstance(String param1, String param2) {
        Pesquisar fragment = new Pesquisar();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_pesquisar, container, false);

        SharedPreferences ler = getContext().getSharedPreferences("InformacoesProdMerc", Context.MODE_PRIVATE);

        ArrayList<ProdutoMercado> pm = new Gson().fromJson(ler.getString("produtosMercado", "{}"),ProdutoMercado.class);

        Log.e("lista: ", pm.toString());



        return inflater.inflate(R.layout.fragment_pesquisar, container, false);
    }

}