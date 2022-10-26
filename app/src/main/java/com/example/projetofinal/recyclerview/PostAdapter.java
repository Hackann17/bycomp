package com.example.projetofinal.recyclerview;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;
import com.example.projetofinal.recyclerview.ItemPost;
import com.example.projetofinal.recyclerview.PostViewHolder;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostViewHolder> {

    //lista de exemplo
    List<ItemPost> listaitem;


    public PostAdapter(List<ItemPost> listaitem) {
        this.listaitem = listaitem;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //chamando o layout a ser utilizado
        return new PostViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itempost, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        //convertendo o objet viewHolder para o nosso ViewHolder
        PostViewHolder produtVH = (PostViewHolder) holder;


        //agora podemos acessar os nossos coponenetes atraves do objeto "produtoVH"
        produtVH.txtMercado.setText(listaitem.get(position).getNomeMercado());
        produtVH.txtEstrelas.setText("R$"+listaitem.get(position).getEstrelas());
        produtVH.txtObs.setText(listaitem.get(position).getObs());
        produtVH.txtUser.setText(listaitem.get(position).getUser());
        produtVH.txtComentario.setText(listaitem.get(position).getComentario());
    }


    @Override
    public int getItemCount(){
        return listaitem.size();
    }


}
