package recyclerviewclasses;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

import java.util.List;

import classesmodelos.ProdutoMercado;

public class ListaProdutoAdapter extends RecyclerView.Adapter<ListaProdutoAdapter.MercadoViewHolder> {
    List<ProdutoMercado> pm ;


    //construtor
    public ListaProdutoAdapter(List<ProdutoMercado> pm){this.pm=pm;}

    @NonNull
    @Override
    public MercadoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MercadoViewHolder(LayoutInflater.from(parent.getContext()).
                inflate(R.layout.itemmercado,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull ListaProdutoAdapter.MercadoViewHolder holder, int position) {

        //atributindo valores aos atributos do viewHolder
        holder.txtNomeMerc.setText(pm.get(position).getMercado().getNome());
        holder.txtSubtotal.setText(pm.get(position).getValorTotal()+"");
        holder.txtEnderecoMerc.setText(pm.get(position).getMercado().getRua());
        holder.txtAvaliacaoMerc.setText((pm.get(position).getMercado().getAvaliacao()+""));

    }

    @Override
    public int getItemCount() {
        return pm.size();
    }

    public class MercadoViewHolder extends RecyclerView.ViewHolder {

        public TextView txtNomeMerc,txtSubtotal,txtEnderecoMerc,txtAvaliacaoMerc;

        public MercadoViewHolder(@NonNull View itemView) {
            super(itemView);
            txtNomeMerc = itemView.findViewById(R.id.txtNomeMerc);
            txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
            txtEnderecoMerc = itemView.findViewById(R.id.txtEnderecoMerc);
            txtAvaliacaoMerc = itemView.findViewById(R.id.txtAvaliacaoMerc);

        }
    }



}
