package classesmodelos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import recyclerviewclasses.HistAdapter;
import com.example.projetofinal.R;
import com.google.gson.Gson;

import java.util.ArrayList;

public class Historico extends Fragment {

    String estrelas, comentario, adicional;
    Button verMais;
    View view;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_historico, container, false);

        //ID
        //verMais = view.findViewById(R.id.btVerMais);

        //json avaliacao vindo da tela AvaliacoesFragment
        SharedPreferences ler = getContext().getSharedPreferences("postagem", Context.MODE_PRIVATE);
        Postagem p = new Gson().fromJson(ler.getString("postagem", "{}"), Postagem.class);

        //recycler view
        ItemHist itp1 = new ItemHist("Calegaris");

        ArrayList<ItemHist> itempe = new ArrayList<>();
        itempe.add(itp1);

        //xml
        RecyclerView recyclerTela = view.findViewById(R.id.histItem);

        recyclerTela.setLayoutManager(new LinearLayoutManager(this.getContext()));
        recyclerTela.setAdapter(new HistAdapter(itempe));

    return view;
    }


}