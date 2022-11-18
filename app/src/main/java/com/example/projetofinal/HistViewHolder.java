package com.example.projetofinal;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class HistViewHolder extends RecyclerView.ViewHolder {

    //componentes na tela
     public TextView nomeMercado;

    public HistViewHolder(@NonNull View itemView) {
        super(itemView);

        //vincular id
        nomeMercado = itemView.findViewById(R.id.txtNomeMercado);
    }
}
