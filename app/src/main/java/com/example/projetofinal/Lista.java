package com.example.projetofinal;

import static com.android.volley.VolleyLog.TAG;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

import classesmodelos.Mercado;
import classesmodelos.Produto;
import classesmodelos.Usuario;


public class Lista<Int> extends Fragment {

    //variaveis
    private FirebaseFirestore firestore;
    View v;
    Button pesq;
    EditText listaCompras;
    String endereco; //do mercado
    String localizacao; //do usuario
    String lista;
    int melhoresPrecos; //vai guardar os precos mais baratos
    List<String> itens = new ArrayList<String>(); //lista que vai receber os produtos da tela
    List<Produto> produtos = new ArrayList<Produto>(); //lista que vai armazenar os dados vindos do firebase
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
        limparLista = v.findViewById(R.id.txtLimpar);
        listaCompras = v.findViewById(R.id.listaProdutos);

        limparLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //verificarCorrespondencia();
              //  listaCompras.setText("");

                //verificarCorrespondencia();
               // recuperarDadosProduto();

                //verificarCorrespondencia();


            }
        });


        btNavegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                separarElementos();
                try{
                firestore = FirebaseFirestore.getInstance();

                    firestore.collection("Produtos")
                            //.whereEqualTo("nome", itens.toArray()[index].toString())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Produto p = new Produto(document.getString("nome"), document.getString("cnpj"), document.getString("codigo"),
                                                    document.getString("preco"), document.getString("unidade"));

                                            List<Produto> aux = new ArrayList<Produto>();
                                            aux.add(p);

                                            for (int index = 0; index < aux.toArray().length; index++) {
                                                String aux1 = itens.toString().toUpperCase();
                                                if (aux1.contains("SAL BATATA")) {
                                                    produtos.add(p);
                                                    //Log.e("Produtos contem: ", produtos.toString());
                                                    Toast.makeText(getContext(), "Produtos contem: " + au1, Toast.LENGTH_SHORT).show();
                                                    // Toast.makeText(getContext(), "Produto: " + p.toString(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Toast.makeText(getContext(), "Não foi encontrado nenhum produto: " + p.getNome(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }
                                    } else {
                                        Toast.makeText(getContext(), "erro", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                } catch (Exception e){
                    Toast.makeText(getContext(), "O erro foi: " + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return  v;
    }

    private void separarElementos() {
        //divide a string em partes a cada virgula
        //adiciona a string no array

        lista = listaCompras.getText().toString();

        //separando as strings a cada virgula
        String[] array = lista.split(",");

        //enquanto indice menor que o comprimento do array
        //inicialmente a lista tem 1 elemento
        //int index = 0;

        try{
            for(int index = 0; index < array.length; index++){
                itens.add(array[index]);
            }
            Log.e("lista: ", itens.toString());
        }catch (Exception e){
            Toast.makeText(getContext(), "Erro: " + e.toString(), Toast.LENGTH_SHORT).show();

        }

//contains(itens.toArray()[index].toString().toUpperCase()
    }


   /*private void recuperarDadosProduto() {
       firestore = FirebaseFirestore.getInstance();
        for(int index = 0; index < itens.toArray().length; index++){
            firestore.collection("Produtos")
                   //.whereEqualTo("nome", itens.toArray()[index].toString())
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Produto p = new Produto(document.getString("nome"), document.getString("cnpj"),document.getString("codigo"),
                                            document.getString("preco"), document.getString("unidade"));
                                    produtos.add(p);

                                    //Log.e("Produtos contem: ", produtos.toString());
                                   //Toast.makeText(getContext(), "Produtos contem: " + produtos.toArray()[0].toString(), Toast.LENGTH_SHORT).show();
                                   // Toast.makeText(getContext(), "Produto: " + p.toString(), Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "erro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        }
        try{

        } catch (Exception e){
            Toast.makeText(getContext(), "O erro foi: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }*/

    /*private void verificarCorrespondencia(){
        //recuperarDadosProduto();
        try{
            for(int index = 0; index < produtos.toArray().length; index++){
                for(int i = 0; i  < itens.toArray().length; i++){
                    String produto = produtos.toArray()[index].toString();
                    String item = itens.toArray()[i].toString().toUpperCase();
                    if(item.contains(produto)){
                        Log.e("Produtos contem: ", produto);
                       Toast.makeText(getContext(), "Produtos que contem: " + produtos.toString(), Toast.LENGTH_SHORT).show();
                    } else {
                        Log.e("Erro: valor nulo ", produto);
                        Toast.makeText(getContext(), "Não foi encontrado nenhum produto", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        } catch (Exception e){
            Log.e("ERRO: ", e.toString());
        }


    }*/

    private void recuperarDadosMercado() {
        firestore = FirebaseFirestore.getInstance();
        try{
            firestore.collection("Mercados")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    Mercado m = new Mercado(document.getString("nome"), document.getString("cnpj"),document.getString("bairro"),
                                            document.getString("rua"), document.getString("uf"), document.getString("numero"), document.getDouble("avaliacao"));
                                    mercados.add(String.valueOf(m));
                                }
                            } else {
                                Toast.makeText(getContext(), "erro", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
        } catch (Exception e){
            Toast.makeText(getContext(), "O erro foi: " + e.toString(), Toast.LENGTH_SHORT).show();
        }
    }

    /*private void comparaPrecos(){
        double preco2 = 0;
        int i = 0;  //vai auxiliar o método para garantir que não vai
        //estar somando os mesmos precos sempre

        //enquanto index for menor/igual a lista de produtos
        for(int index = 0; index <= itens.size(); index++){
            //pega o preco do indice 0 e coloca no preco 1
            double preco1 = produtos.get(index).getPreco();

            //verifica o valor que a variavel está guardando
            //se for maior, ele armazena na variavel para fazer a próxima comparação
            //se for menor, ele guarda na lista
            if(preco1 > preco2){ //exemplo: se 2 > 0
                preco2 = preco1; //entao preco2 = 2 e preco1 = 2
            } else {
                //substitui os precos mais baratos na lista
                for(i = 0; i <= itens.size(); i++){
                    //guarda na lista
                    produtos.get(i).setPreco(preco1);
                }
            }
        }
    }*/

    //método que compara a localização dos mercados no nosso banco e verifica a distancia da localização do usuario
    // !!!!!!!! PEDRO GODINHO
    private void compararLocal(){
    }

    }


