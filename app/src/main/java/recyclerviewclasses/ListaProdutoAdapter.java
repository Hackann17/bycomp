package recyclerviewclasses;

import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

import java.util.List;

import classesmodelos.ProdutoMercado;

public class ListaProdutoAdapter extends RecyclerView.Adapter<ListaProdutoAdapter.LIstaProdutoViewHoLder> {
    List<ProdutoMercado> ProdutosMercados ;


    //construtor
    public ListaProdutoAdapter(List<ProdutoMercado> pm){this.ProdutosMercados=pm;}

    @NonNull
    @Override
    public ListaProdutoAdapter.LIstaProdutoViewHoLder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new LIstaProdutoViewHoLder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.itemlistaproduto,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProdutoAdapter.LIstaProdutoViewHoLder holder, int position) {

        //atributindo valores aos atributos do viewHolder
        holder.txtNomeMerc.setText(ProdutosMercados.get(position).getMercado().getNome());
        holder.txtSubtotal.setText(ProdutosMercados.get(position).getValorTotal()+"");
        holder.txtEnderecoMerc.setText(ProdutosMercados.get(position).getMercado().getRua());
        holder.txtAvaliacaoMerc.setText((ProdutosMercados.get(position).getMercado().getAvaliacao()+""));

        ProdutoMercado pm = ProdutosMercados.get(position);

        //evento do botao do item
        holder.btPrecoIndividuais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mostraPOpup(pm,view);
            }
        });

    }


    @Override
    public int getItemCount() {
        return ProdutosMercados.size();
    }

    public class LIstaProdutoViewHoLder extends RecyclerView.ViewHolder {

        public TextView txtNomeMerc,txtSubtotal,txtEnderecoMerc,txtAvaliacaoMerc;

        public Button btPrecoIndividuais;

        public LIstaProdutoViewHoLder(@NonNull View itemView) {
            super(itemView);
            txtNomeMerc = itemView.findViewById(R.id.txtNomeMerc);
            txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
            txtEnderecoMerc = itemView.findViewById(R.id.txtEnderecoMerc);
            txtAvaliacaoMerc = itemView.findViewById(R.id.txtAvaliacaoMerc);
            btPrecoIndividuais = itemView.findViewById(R.id.btPrecoIndividuais);


        }
    }

    //monta a mensagem que aparecerá
    private void mostraPOpup(ProdutoMercado pm, View view) {

        AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());

        String mensagem="";

        //repete o nome e o preço dos produts da lista
        for(int i=0 ; i < pm.getProdutos().size();i++){
         //monta uma linha da mensagem
         mensagem+= pm.getProdutos().get(i).getNome()+"    "
                 +"R$"+pm.getProdutos().get(i).getPreco()+"\n"+"\n";
        }

        //monta a tela da mensagem
        builder.setMessage(mensagem)
                .setPositiveButton("Ocultar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        // Create the AlertDialog object and return it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();



    }




}
