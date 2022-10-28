package com.example.projetofinal;

import static com.android.volley.VolleyLog.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.Produto;


public class Lista<Int> extends Fragment {

    //variaveis
    private FirebaseFirestore firestore;
    View v;
    Button pesq;
    EditText listaCompras;
    String endereco; //do mercado
    String localizacao; //do usuario
    Int melhoresPrecos; //vai guardar os precos mais baratos
    List<String> itens = new ArrayList<String>(); //lista que vai receber os produtos da tela
    List<Integer> produtos = new ArrayList<Integer>(); //lista que vai armazenar os dados vindos da WebAPI
    List<String> mercados = new ArrayList<String>(); //lista que vai receber os mercados
    TextView limparLista; //texto clicavel para limpar o texto na lista de compras


    /*// TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Lista() {
        // Required empty public constructor
    }

    public static Lista newInstance(String param1, String param2) {
        Lista fragment = new Lista();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }*/

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

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
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_lista, container, false);
        //achando os ids
        Button btNavegar = v.findViewById(R.id.butPesq);




        btNavegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               Navigation.findNavController(view).navigate(R.id.lista_pesq);


            }
        });
/*
        listaCompras = findViewById(R.id.listaProdutos);

        //evento do texto de limpar a lista
        limparLista = v.findViewById(R.id.txtLimpar);
        limparLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listaCompras.setText("");
            }
        });
*/
        return  v;

    }

   /* private void recuperarDadosFirebase() {
        firestore = FirebaseFirestore.getInstance();

        //Produto produto = new Produto();

        try{
            firestore.collection("Produtos").document("07891000248758").get().addOnSuccessListener(
                    documentSnapshot -> {
                        if(documentSnapshot.exists()){
                            //traz o documento
                            Log.d(TAG, "DocumentSnapshot data: ${document.data}");
                        } else {
                            //avisa que o documento não foi encontrado
                            Log.d(TAG, "Falhou");
                        }
                    }
            );
        } catch (Exception e){
            Toast.makeText(getContext(), "O erro foi: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }*/
}