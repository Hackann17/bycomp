package com.example.projetofinal;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

import classesmodelos.Produto;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Pesquisar#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Pesquisar extends Fragment {

    View view;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Pesquisar() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment pesquisar.
     */
    // TODO: Rename and change types and number of parameters
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

        //simulando itens a serem integrados na tela
        //esses dois representam um item do recycler view
        ItemPost itp = new ItemPost(2, 3, "Bom e Barato", "Adorei o mercado", "@marininha", "Calegaris");

        ArrayList<ItemPost> itemposts = new ArrayList<>();
        for (int i = 0; i < 4; i++){
            itemposts.add(itp);
        }
        //passando a lista par o adapter personalizad
        /*
        RecyclerView recyclerTela = view.findViewById(R.id.ListaTelapes);

        recyclerTela.setLayoutManager(new LinearLayoutManager(view.getContext()));
        recyclerTela.setAdapter(new PostAdapter(itemposts));

        // Inflate the layout for this fragment*/
        return inflater.inflate(R.layout.fragment_pesquisar, container, false);
    }

}