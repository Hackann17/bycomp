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

    //lista auxiliar que vai ser usada para a verificação
    List<Produto> aux = new ArrayList<Produto>();


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
                //método que divide a String e coloca numa lista
                separarElementos();

                //busca os produtos no banco
                try{
                firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Produtos")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Produto p = new Produto(document.getString("nome"), document.getString("cnpj"), document.getString("codigo"),
                                                    document.getString("preco"), document.getString("unidade"));

                                            aux.add(p);


                                        }

                                        Log.e("tamanho1: ",itens.size()+"");
                                        Log.e("tamanho2: ",aux.size()+"");

                                        for (int indexP = 0; indexP < itens.size(); indexP++) {
                                            //percorre o tamanho da lista aux de produtos
                                            Log.e("indice produto: ",indexP+"");

                                            for (int index = 0; index < aux.size(); index++) {
                                                Log.e("indice lista banco: ",index+"");
                                                //pega o nome do objeto contido no indice e joga numa variavel do tipo string
                                                String produtoBanco = aux.get(index).getNome().toUpperCase();
                                                String produtoUsuario = itens.get(indexP).toUpperCase();
                                                //se a variavel conter uma parte da string armazenada em cada indice da lista que o usuario passou
                                                if (produtoBanco.contains(produtoUsuario)) {
                                                    produtos.add(aux.get(index)); //adiciona na lista de produtos global
                                                    Log.e("Produtos contem: ", aux.get(index).getNome());
                                                    //Toast.makeText(getContext(), "Produtos contem: " + produtos.get(index).getNome(), Toast.LENGTH_SHORT).show();
                                                } else {
                                                    Log.e("Não foi encontrado: ", aux.get(index).getNome());
                                                    //Toast.makeText(getContext(), "Não foi encontrado nenhum produto: " + p.getNome(), Toast.LENGTH_SHORT).show();
                                                }
                                            }
                                        }

                                        for (int indexP = 0; indexP < produtos.size(); indexP++){
                                            Log.e("nome do produto: ", produtos.get(indexP).getNome());
                                        }
                                    }
                                }
                            });
                } catch (Exception e){
                    Toast.makeText(getContext(), "O erro foi: " + e, Toast.LENGTH_SHORT).show();
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
    }

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
        int i = 0;  //vai auxiliar o método para garantir que não vai estar somando os mesmos precos sempre

        //enquanto index for menor/igual a lista de produtos
        for(int index = 0; index <= produtos.size(); index++){
            //pega o preco do indice 0 e coloca no preco 1
            String preco = produtos.get(index).getPreco();

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

  /* private void verificarCorrespondencia(){
        for(int i = 0; i < aux.size(); i++) {
            for(int a = 0; a < itens.size(); i++){
                if(aux.get(i).getNome().contains(itens.get(a).toUpperCase())){
                    Log.e("Produtos contem: ", produtos.get(i).getNome());
                }else{
                    Log.e("Não foi encontrado: ", produtos.get(i).getNome());
                }

            }
        }
    }*/


    //método que compara a localização dos mercados no nosso banco e verifica a distancia da localização do usuario
    // !!!!!!!! PEDRO GODINHO
    private void compararLocal(){
    }

    }


