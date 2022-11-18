package com.example.projetofinal;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class HistAdapter extends RecyclerView.Adapter<HistViewHolder>{

    List<ItemHist> listaitem;


    public HistAdapter(ArrayList<ItemHist> listaitem) {
        this.listaitem = (List<ItemHist>) listaitem;
    }

    @NonNull
    @Override
    public HistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new HistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.historico, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistViewHolder holder, int position) {
        //convertendo o objet viewHolder para o nosso ViewHolder
        HistViewHolder mercadoVH = holder;

        //agora podemos acessar os nossos coponenetes atraves do objeto
        mercadoVH.nomeMercado.setText(listaitem.get(position).getNomeMercado());

    }

    @Override
    public int getItemCount() {
        return listaitem.size();
    }
}
