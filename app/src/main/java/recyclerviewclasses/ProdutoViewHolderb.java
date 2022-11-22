package recyclerviewclasses;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

public class ProdutoViewHolderb extends RecyclerView.ViewHolder {

    //atributos que serao preenchidos comos dados
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
