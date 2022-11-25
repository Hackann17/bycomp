package recyclerviewclasses;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;

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
