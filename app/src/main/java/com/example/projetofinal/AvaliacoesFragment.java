package com.example.projetofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

public class AvaliacoesFragment extends Fragment {

    //variaveis
    Button butBaixaQualidade, butCaro, butBarato, butAtendimentoOk, publicar, arquivar;
    RatingBar qtdEstrelas;
    TextView comentarioUsuario;
    String comentario;
    View v;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_avaliacoes, container, false);

        //botoes
        butBaixaQualidade = v.findViewById(R.id.butBaixaQualidade);
        butAtendimentoOk = v.findViewById(R.id.butAtendimentoOk);
        butBarato = v.findViewById(R.id.butBarato);
        butCaro = v.findViewById(R.id.butCaro);
        publicar = v.findViewById(R.id.butPub);
        arquivar = v.findViewById(R.id.butArq);

        //ratingBar
        qtdEstrelas = v.findViewById(R.id.ratingBar);

        //input que o usuario comentou
        comentarioUsuario = v.findViewById(R.id.comentarioUsuario);

        publicar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                comentario = comentarioUsuario.getText().toString();
                //pega a quantidade de estrelas que o usuario passou
                String rating = String.valueOf(qtdEstrelas.getRating());

                if(rating.equals("0.0")){
                    Toast.makeText(getContext(), "Por favor, faça a avaliação do mercado", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("Estrelas: ", rating);
                }
            }
        });

        butBaixaQualidade.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //muda o shape dos botoes ao ser clicado
                butBaixaQualidade.setBackgroundResource(R.drawable.botaoroxoescuro);
                //teste
                String texto;
                texto = butBaixaQualidade.getText().toString();
                Log.e("Texto: ", texto);
            }
        });

        butCaro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //muda o shape dos botoes ao ser clicado
                butBaixaQualidade.setBackgroundResource(R.drawable.botaoroxoescuro);
            }
        });

        butBarato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //muda o shape dos botoes ao ser clicado
                butBaixaQualidade.setBackgroundResource(R.drawable.botaoroxoescuro);
            }
        });

        butAtendimentoOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //muda o shape dos botoes ao ser clicado
                butBaixaQualidade.setBackgroundResource(R.drawable.botaoroxoescuro);
            }
        });

        return v;
    }
}