package recyclerviewclasses;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;

import androidx.recyclerview.widget.RecyclerView;

import com.example.projetofinal.R;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.ItemHist;
import classesmodelos.Postagem;

public class HistAdapter extends RecyclerView.Adapter<HistViewHolder>{

    List<ItemHist> listaitem;


    public HistAdapter(ArrayList<ItemHist> listaitem) {
        this.listaitem = (List<ItemHist>) listaitem;
    }

    @NonNull
    @Override
    public HistViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return  new HistViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.historico, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistViewHolder holder, int position) {
        //convertendo o objet viewHolder para o nosso ViewHolder
        HistViewHolder mercadoVH = holder;

        //agora podemos acessar os nossos coponenetes atraves do objeto
        mercadoVH.nomeMercado.setText(listaitem.get(position).getNomeMercado());



        mercadoVH.btVerMais.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences ler = view.getContext().getSharedPreferences("postagem", Context.MODE_PRIVATE);
                Postagem p = new Gson().fromJson(ler.getString("postagem", "{}"), Postagem.class);

                Bundle bundle = new Bundle();

                bundle.putSerializable("p", p);

                Navigation.findNavController(view).navigate(R.id.hist_avaliacao, bundle);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listaitem.size();
    }
}
