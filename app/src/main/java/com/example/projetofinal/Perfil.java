package com.example.projetofinal;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;



import classesmodelos.Usuario;

public class Perfil extends Fragment {

    Button btAlterarDados;
    Button btAlterarSenha;
    EditText inputNomeUsuario;
    EditText inputEmailUsuario;
    TextView textView37;
    TextView textView38;

    View v;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Perfil() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_perfil, container, false);

        try {
            //objt de testes
            Usuario usuario = new Usuario("Campinas", "marina", "marina@gmail.com", "1234");

            //declarando os inputs / pegar informações dos inputs

            textView37 = v.findViewById(R.id.textView37);
            textView38 = v.findViewById(R.id.textView38);
            inputNomeUsuario = v.findViewById(R.id.inputNomeUsuario);
            inputEmailUsuario = v.findViewById(R.id.inputEmailUsuario);

            btAlterarDados = v.findViewById(R.id.btAlterarDados);
            btAlterarSenha = v.findViewById(R.id.btAlterarSenha);

            btAlterarSenha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(v.getContext(), Senha.class));

                }
            });

            // pegar as informações do banco local e passar para os inputs

            inputNomeUsuario.setHint(usuario.getNome());
            inputEmailUsuario.setHint(usuario.getEmail());
        }
        catch (Exception e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return v;
    }
}
