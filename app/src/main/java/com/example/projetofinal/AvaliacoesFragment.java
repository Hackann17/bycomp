package com.example.projetofinal;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.Postagem;

public class AvaliacoesFragment extends Fragment {

    //componentes da tela
    Button publicar, arquivar;
    RatingBar qtdEstrelas;
    TextView comentarioUsuario;
    RadioGroup radioGroup;

    //variaveis
    Boolean clicado = false; //verifica se algum botao foi clicado ou nao
    int clickBotao; //vai aumentar a cada vez que o usuario clicar em um botao
    String comentario;
    List <Postagem> postagem = new ArrayList<>(); //lista que vai guardar tudo o que vier da tela avaliacao
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

        //recurso.setBackgroundResource(R.drawable.botaoroxoescuro);

        //radioGroup
        radioGroup = v.findViewById(R.id.radioGroup);

        //botoes
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
                    verificarRadioButtonSelecionado();
                    //Postagem p = new Postagem();
                }
            }

            private void verificarRadioButtonSelecionado() {
                //verifica o id do radio group selecionado
                int item = radioGroup.getCheckedRadioButtonId();

                if(item != -1){
                    //algum foi selecionado
                    RadioButton itemSelecionado = v.findViewById(R.id.item);

                    String opcao = itemSelecionado.getText().toString();

                }
            }
        });

        return v;
    }
}