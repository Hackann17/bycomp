package recyclerviewclasses;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

public class HistViewHolder extends RecyclerView.ViewHolder {

    //componentes na tela
     public TextView nomeMercado;
     public Button btVerMais;

    public HistViewHolder(@NonNull View itemView) {
        super(itemView);

        //vincular id
        nomeMercado = itemView.findViewById(R.id.txtNomeMercado);
        btVerMais = itemView.findViewById(R.id.btVerMais);
    }
}
