package com.example.projetofinal.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

public class ProdutosViewHolder extends RecyclerView.ViewHolder {

    //declaraçao dos componentes da tela de item
    public TextView textNomeP ;
    public TextView textPrecoP;
    public TextView textNomeM;


    public ProdutosViewHolder(@NonNull View itemView) {
        super(itemView);
        //VIncular id para cda componente
        textNomeP = itemView.findViewById(R.id.txtNome);
        textPrecoP = itemView.findViewById(R.id.txtPreco);
        textNomeM = itemView.findViewById(R.id.txtNomeM);
    }

}
