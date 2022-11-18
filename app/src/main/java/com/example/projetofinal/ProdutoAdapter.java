package com.example.projetofinal;

import static androidx.core.content.ContextCompat.startActivity;

import static java.security.AccessController.getContext;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutosViewHolder> {

    List<ItemPesq> listaitem;//lista da clase para ser usada em+-. mais de um metodo
    Button verMais;


    //construtor
    public ProdutoAdapter(List<ItemPesq> itempe) {
        this.listaitem = itempe;
    }

    @NonNull
    @Override
    public ProdutosViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        //chamando layout em questao para definir como modelo aser usado
        //apos tudo ser adicionado na view , a mesma é retornada pelo viewholder
        return new ProdutosViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.itempesq,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosViewHolder holder, int position) {
        //convertendo o objet viewHolder para o nosso ViewHolder

        //agora podemos acessar os nossos coponenetes atraves do objeto "produtoVH"
        holder.nome.setText(listaitem.get(position).getNomeP());
        holder.preco.setText("R$"+listaitem.get(position).getPrecoP());
        holder.mercado.setText(listaitem.get(position).getNomeM());


        /*verMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });*/
    }

    @Override
    public int getItemCount() {
        return listaitem.size();
    }

    class ProdutosViewHolder extends RecyclerView.ViewHolder {

        TextView nome, preco, mercado;

        public ProdutosViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            preco = itemView.findViewById(R.id.txtPreco);
            mercado = itemView.findViewById(R.id.txtNomeM);
        }
    }
}
