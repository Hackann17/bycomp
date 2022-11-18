package com.example.projetofinal;

import static android.content.Intent.getIntent;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.gson.Gson;

import java.util.ArrayList;

import classesmodelos.Postagem;

public class Historico extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public static Historico newInstance(String param1, String param2) {
        Historico fragment = new Historico();
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
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historico, container, false);

        SharedPreferences ler = getContext().getSharedPreferences("postagem", Context.MODE_PRIVATE);

        Postagem p = new Gson().fromJson(ler.getString("postagem", "{}"), Postagem.class);


        Log.e("passou","================>"+p.getAvaliacaoMercado());

        //simulando itens a serem integrados na tela
        //esses dois representam um item do recycler view
        ItemHist itp1 = new ItemHist("Calegaris");

        ArrayList<ItemHist> itempe = new ArrayList<>();
        itempe.add(itp1);

        RecyclerView recyclerTela = view.findViewById(R.id.histItem);

        recyclerTela.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerTela.setAdapter(new HistAdapter(itempe));


    return view;








    }
}