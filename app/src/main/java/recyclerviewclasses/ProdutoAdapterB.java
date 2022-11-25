package recyclerviewclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.ItemPesq;
import com.example.projetofinal.R;

import java.util.List;

public class ProdutoAdapterB extends RecyclerView.Adapter<ProdutoAdapterB.ProdutoViewHolderb>{
    List<ItemPesq> listaitem;//lista da clase para ser usada em+-. mais de um metodo


    @NonNull
    @Override
    public ProdutoViewHolderb onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ProdutoViewHolderb(LayoutInflater.from(parent.getContext()).inflate(R.layout.itempesqb,parent, false));

    }

    @Override
    public void onBindViewHolder(@NonNull ProdutoAdapterB.ProdutoViewHolderb holder, int position) {
        //convertendo o objet viewHolder para o nosso ViewHolder
        //agora podemos acessar os nossos coponenetes atraves do objeto "produtoVH"

        holder.txtNome1.setText(listaitem.get(position).getNomeP());
        holder.txtPrecoP1.setText(listaitem.get(position).getPrecoP().toString());
        holder.txtNomeM1.setText(listaitem.get(position).getNomeM());
        holder.txtEndereco.setText(listaitem.get(position).getEnderecoM());
        holder.txtNome1.setText(listaitem.get(position).getNomeP());
        holder.txtNome1.setText(listaitem.get(position).getNomeP());


    }

    @Override
    public int getItemCount() {
        return 0;
    }

    //replica da classe de viwholder
    public class ProdutoViewHolderb extends RecyclerView.ViewHolder {

        //lembar de adquirir a a avaliação
        public TextView txtNome1 ,txtPrecoP1,txtNomeM1,txtEndereco;
        public Button bOcultar;

        //construtor
        public ProdutoViewHolderb(@NonNull View itemView) {
            super(itemView);
            txtNome1 = itemView.findViewById(R.id.txtNome1);
            txtPrecoP1 = itemView.findViewById(R.id.txtPrecoP1);
            txtNomeM1 = itemView.findViewById(R.id.txtNomeM1);
            txtEndereco = itemView.findViewById(R.id.txtEndereco);
            bOcultar = itemView.findViewById(R.id.bOcultar);




        }
    }
}
