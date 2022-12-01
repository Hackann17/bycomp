package recyclerviewclasses;

import static java.security.AccessController.getContext;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.Bycomp;
import com.example.projetofinal.ItemPesq;
import com.example.projetofinal.Login;
import com.example.projetofinal.PesquisaProduto;
import com.example.projetofinal.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class ProdutoAdapter extends RecyclerView.Adapter<ProdutoAdapter.ProdutosViewHolder>  {

    List<ItemPesq> listaitem;//lista da clase para ser usada em+-. mais de um metodo

    EditText txtNome1,txtPrecoP1,txtNomeM1,txtEndereco;
    Button bOcultar;

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

        //pegar o indice do item, gerar/aproveitar seu objeto ,
        final ItemPesq itpo = listaitem.get(position);

        //link para acessar como fazer o pop app
        // https://developer.android.com/guide/topics/ui/dialogs?hl=pt-br#CustomLayout

        holder.btExDetalhes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                mostraPOpup(itpo,view);

            }
        });


    }

   /* public class CriarInflater extends DialogFragment {
        public CriarInflater() {}
        public LayoutInflater devolver(){
            return requireActivity().getLayoutInflater();
        }
    }*/

    // cria o popUP que sera puxado quando o botao for acionado
    public  void mostraPOpup(ItemPesq itemPesq, View v ){

        AlertDialog.Builder builder = new AlertDialog.Builder(v.getContext());

        builder.setMessage(itemPesq.getNomeP() +"\n"+
                        "Preço" +"                     "+"R$"+itemPesq.getPrecoP()+"\n"+
                        "Mercado" +"                 "+itemPesq.getNomeM()+"\n"+
                        "Endereço" +"                "+itemPesq.getEnderecoM()+"\n"+
                        "Avaliação"+"                "+itemPesq.getAvaliacaoM()+"\n"
                )
                .setPositiveButton("Ocultar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();


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
