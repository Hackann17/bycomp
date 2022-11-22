package com.example.projetofinal;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.Postagem;
import io.grpc.internal.JsonParser;

public class AvaliacoesFragment extends Fragment {

    //componentes da tela
    Button publicar, arquivar;
    RatingBar qtdEstrelas;
    TextView comentarioUsuario;
    RadioButton barato, qualidadeBoa, qualidadeRuim, caro;
    String rating;
    View v;

    //variaveis
    String comentario;
    Postagem postagem = null; //lista que vai guardar tudo o que vier da tela avaliacao
    RadioButton[] rdButtom = new RadioButton[4]; //guarda todos os checkboxes que possuem na tela
    List <String> opcoes = new ArrayList<>(); //guarda as opcoes

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_avaliacoes, container, false);

        Bundle bundle = getArguments();

        if(getBoolean(bundle, "p") == true) {
            Postagem p = (Postagem) bundle.getSerializable("p");

            if(p != null) {
                Toast.makeText(getContext(), "Foi!", Toast.LENGTH_SHORT).show();
            }
        }


        //botoes
        publicar = v.findViewById(R.id.butPub);
        arquivar = v.findViewById(R.id.butArq);

        //ratingBar
        qtdEstrelas = v.findViewById(R.id.ratingBar);

        //input que o usuario comentou
        comentarioUsuario = v.findViewById(R.id.comentarioUsuario);

        //radioButton
        barato = v.findViewById(R.id.radioButtonBarato);
        qualidadeBoa = v.findViewById(R.id.radioButtonQualidadeBoa);
        qualidadeRuim = v.findViewById(R.id.radioButtonQualidadeInf);
        caro = v.findViewById(R.id.radioButtonCaro);
        
        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentario = comentarioUsuario.getText().toString();
                //pega a quantidade de estrelas que o usuario passou
                 rating = String.valueOf(qtdEstrelas.getRating());

                if(rating.equals("0.0")){
                    Toast.makeText(getContext(), "Avalie o mercado com estrelas", Toast.LENGTH_LONG).show();
                } else {
                    //adiciona os checkboxs na lista
                    rdButtom[0] = qualidadeBoa;
                    rdButtom[1] = qualidadeRuim;
                    rdButtom[2] = barato;
                    rdButtom[3] = caro;

                    verificarCheckSelecionado();

                    //mostrar no log
                    Log.e("Postagem: ", postagem.getAvaliacaoMercado());
                    Log.e("Postagem: ", postagem.getComentario());

                    for(int i = 0; i < postagem.getAdicional().size(); i++){
                        Log.e("Postagem: ", postagem.getAdicional().get(i));
                    }
                }
            }


        });

        arquivar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences.Editor gravar = getContext().getSharedPreferences("postagem", Context.MODE_PRIVATE).edit();
                gravar.putString("postagem", new Gson().toJson(postagem));
                gravar.apply();

                Toast.makeText(getContext(), "Arquivado com sucesso", Toast.LENGTH_SHORT).show();

                SharedPreferences ler = null;

                Log.e("FEITO!", "FEITO");

            }
        });


        return v;
    }

    private void verificarCheckSelecionado() {
        Postagem p = null;
        //tamanho do array checkboxes
        for(int i = 0; i < rdButtom.length; i++){
            //verifica os indices que estão selecionados
            if(rdButtom[i].isChecked()){
                //adiciona numa lista de string
                opcoes.add(rdButtom[i].getText().toString());
            }
        }

        //cria o objeto postagem e adiciona o que o usuario colocou
        p = new Postagem(rating, comentario, opcoes);

        //percorre o tamanho das opcoes
        for(int i = 0; i < opcoes.size(); i++){
            Log.e("Foi selecionado: ", opcoes.get(i));
            //adiciona numa lista de objetos
        }

        postagem = p;
    }

    public static Boolean getBoolean(Bundle arguments, String key) {
        if (arguments != null && arguments.containsKey(key)) {
            return true;
        } else {/* w  w  w  . ja v a  2  s  . co m*/
            return false;
        }
    }
}