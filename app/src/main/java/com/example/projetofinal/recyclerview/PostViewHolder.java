package com.example.projetofinal.recyclerview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

public class PostViewHolder extends RecyclerView.ViewHolder {

    //componentes que terão na tela
    public TextView txtEstrelas;
    public TextView txtObs;
    public TextView txtComentario;
    public TextView txtUser;
    public TextView txtMercado;


    public PostViewHolder(@NonNull View itemView) {
        super(itemView);

        //vincular id
        txtUser = itemView.findViewById(R.id.txtUser);
        txtMercado = itemView.findViewById(R.id.txtMercado);
        txtComentario = itemView.findViewById(R.id.txtComentario);
        txtEstrelas = itemView.findViewById(R.id.txtEstrelas);
        txtObs = itemView.findViewById(R.id.txtObs);
    }

}
