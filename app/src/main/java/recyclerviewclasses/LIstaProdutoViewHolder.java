package recyclerviewclasses;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

public class LIstaProdutoViewHolder extends RecyclerView.ViewHolder {

    public TextView txtNomeMerc,txtSubtotal,txtEnderecoMerc,txtAvaliacaoMerc;
    public Button btPrecoIndividuais;

    public LIstaProdutoViewHolder(@NonNull View itemView) {
        super(itemView);
        //atribui os itens a cada atributo
        txtNomeMerc = itemView.findViewById(R.id.txtNomeMerc);
        txtSubtotal = itemView.findViewById(R.id.txtSubtotal);
        txtEnderecoMerc = itemView.findViewById(R.id.txtEnderecoMerc);
        txtAvaliacaoMerc = itemView.findViewById(R.id.txtAvaliacaoMerc);
        btPrecoIndividuais = itemView.findViewById(R.id.btPrecoIndividuais);
    }
}
