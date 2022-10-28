package com.example.projetofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Rsocial#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Rsocial extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Rsocial() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Rsocial.
     */
    // TODO: Rename and change types and number of parameters
    public static Rsocial newInstance(String param1, String param2) {
        Rsocial fragment = new Rsocial();
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
        view = inflater.inflate(R.layout.fragment_rsocial, container, false);

        //simulando itens a serem integrados na tela
        //esses dois representam um item do recycler view
        ItemPost itp1 = new ItemPost(2, 3, "Bom e Barato", "Adorei o mercado", "@marininha", "Calegaris");
        ItemPost itp2 = new ItemPost(2, 3, "Bom e Barato", "Adorei o mercado", "@marininha", "Calegaris");
        ItemPost itp3 = new ItemPost(2, 3, "Bom e Barato", "Adorei o mercado", "@marininha", "Calegaris");
        ItemPost itp4 = new ItemPost(2, 3, "Bom e Barato", "Adorei o mercado", "@marininha", "Calegaris");

        ArrayList<ItemPost> itempe = new ArrayList<>();
        itempe.add(itp1);
        itempe.add(itp2);
        itempe.add(itp3);
        itempe.add(itp4);
        //pas=ando a lista par o adapter personalizad

        RecyclerView recyclerTela = view.findViewById(R.id.listaPost);

        recyclerTela.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerTela.setAdapter(new PostAdapter(itempe));


        return view;

    }
}