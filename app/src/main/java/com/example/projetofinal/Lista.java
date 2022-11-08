package com.example.projetofinal;

import static com.android.volley.VolleyLog.TAG;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

import java.io.IOException;
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
                listaCompras.setText("");
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
                                                    Double.parseDouble(document.getString("preco")), document.getString("unidade"));
                                            aux.add(p);
                                        }
                                        //for que vai percorrer a lista de itens do usuario
                                        for (int indexP = 0; indexP < itens.size(); indexP++) {
                                            //for que percorre a lista aux que contém todos os produtos do firebase
                                            for (int index = 0; index < aux.size(); index++) {
                                                //variavel que recebe cada item na lista aux
                                                String produtoBanco = aux.get(index).getNome().toUpperCase().trim();
                                                //variavel que recebe cada item da lista de usuario
                                                String produtoUsuario = itens.get(indexP).toUpperCase().trim();
                                                //verifica se os produtos no banco contém o item que o usuario passou
                                                if (produtoBanco.contains(produtoUsuario)) {
                                                    produtos.add(aux.get(index)); //adiciona na lista de produtos global
                                                    Log.e("Produtos contem: ", aux.get(index).getNome());
                                                } else {
                                                    Log.e("Não foi encontrado: ", aux.get(index).getNome());
                                                }
                                            }
                                        }
                                        //método para reorganizar uma lista de produtos pela ordem de mais barato para mais caro
                                        produtos = Produto.organizarPorPreco(produtos);

                                        for (Produto p : produtos) {
                                            Log.e("preço depois: ",p.getPreco()+"");
                                        }
                                    }
                                }
                            });
                } catch (Exception e){
                    Log.e("ERRO: ", e.getMessage());
                }
            }
        });
        return v;
    }

    //método que separa as strings e coloca numa lista
    private void separarElementos() {
        lista = listaCompras.getText().toString();
        itens = new ArrayList<>();

        //separando as strings a cada virgula
        String[] array = lista.split(",");

        try {
            for (int index = 0; index < array.length; index++) {
                //adiciona as strings na lista global de itens
                itens.add(array[index]);
            }
            Log.e("lista: ", itens.toString());
        } catch (Exception e) {
            Toast.makeText(getContext(), "Erro: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    //método que compara a localização dos mercados no nosso banco e verifica a distancia da localização do usuario
    private List<Mercado> compararLocal(List<Produto> produtos) throws IOException {
        SharedPreferences preferences = getContext().getSharedPreferences("LocalizacaoUsuario", Context.MODE_PRIVATE);
        String bairroU = preferences.getString("bairroU", "");
        List<Mercado> mercados = new ArrayList<>();
        try {
            for (Produto produto : produtos) {
                String cnpj = produto.getCnpj();
                firestore.collection("Mercados")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Mercado m = new Mercado(document.getString("nome"), document.getString("cnpj"), document.getString("bairro"),
                                                document.getString("rua"), document.getString("uf"), document.getString("numero"), document.getDouble("avaliacao"));
                                        //verifica se o mecado possui o mesmo cnpj que o produto e se esta pelo menos no memso bairro que o usuario
                                        if (m.getCnpj().equals(cnpj) && m.getBairro().equals(bairroU)) {
                                            //caso o mercado atenda aos requisitos seu cnpj sera adicionado a lista
                                            mercados.add(m);
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), "erro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //talvez seja possivel retornar uma lista bem mais completa do que as identificaçoes dos mecados
        //que ja estao selecionados para a outra tela para serem selecionados de novo
        return  mercados;
    }
}




