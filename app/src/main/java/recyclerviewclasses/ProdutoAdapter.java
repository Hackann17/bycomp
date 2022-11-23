package recyclerviewclasses;

import android.app.AlertDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.ItemPesq;
import com.example.projetofinal.R;

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
        return new ProdutosViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.itempesq,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ProdutosViewHolder holder, int position) {
        //convertendo o objet viewHolder para o nosso ViewHolder


        //agora podemos acessar os nossos coponenetes atraves do objeto "produtoVH"
        holder.nome.setText(listaitem.get(position).getNomeP());
        holder.preco.setText("R$"+listaitem.get(position).getPrecoP());
        holder.mercado.setText(listaitem.get(position).getNomeM());




                //link para acessar como fazer o pop app

                   //pegar o indice do item, gerar/aproveitar seu objeto ,
                   //passá-lo para o outro layout e applicar os valores nas textos
                   // https://developer.android.com/guide/topics/ui/dialogs?hl=pt-br#CustomLayout

              holder.btExDetalhes.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {










                  }
              });


    }

    private void pegarInfo( int position) {


   }


    @Override
    public int getItemCount() {
        return listaitem.size();
    }

    class ProdutosViewHolder extends RecyclerView.ViewHolder {

        TextView nome, preco, mercado;
        Button btExDetalhes;

        public ProdutosViewHolder(@NonNull View itemView) {
            super(itemView);

            nome = itemView.findViewById(R.id.txtNome);
            preco = itemView.findViewById(R.id.txtPrecoP);
            mercado = itemView.findViewById(R.id.txtNomeM);
            btExDetalhes= itemView.findViewById(R.id.btExDetalhes);
        }
    }
}
