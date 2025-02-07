package com.example.projetofinal;

import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

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
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import classesmodelos.Avaliacao;
import classesmodelos.Mercado;
import classesmodelos.Produto;
import classesmodelos.ProdutoMercado;


public class Lista<Int> extends Fragment {

    //variaveis
    private FirebaseFirestore firestore = FirebaseFirestore.getInstance();
    View v;
    EditText listaCompras;
    Button btNavegar;
    String lista;
    List<String> itens = new ArrayList<String>(); //lista que vai receber os produtos da tela
    List<Produto> produtos = new ArrayList<Produto>(); //lista que vai armazenar os dados vindos do firebase
    TextView limparLista; //texto clicavel para limpar o texto na lista de compras
    List<Mercado> mercadosBanco = new ArrayList<>();
    List<Avaliacao> avaliacoesBanco = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        v = inflater.inflate(R.layout.fragment_lista, container, false);

        SharedPreferences preferences = getContext().getSharedPreferences("LocalizacaoUsuario", Context.MODE_PRIVATE);
        String cidadeU = preferences.getString("cidadeU", "");
        String bairroU = preferences.getString("bairroU", "");


        Log.e("cidade/bairro", cidadeU + bairroU);
        //achando os ids
        btNavegar= v.findViewById(R.id.butPesq);
        limparLista = v.findViewById(R.id.txtLimpar);
        listaCompras = v.findViewById(R.id.listaProdutos);

        limparLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //criar tela de confirmação para perguntar se o usuario deseja limpar a lista
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Deseja limpar a lista de produtos?")
                        .setPositiveButton("Limpar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //limpar a lista
                                listaCompras.setText("");
                            }
                        })
                        .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                //cancelar
                                dialog.cancel();
                            }
                        });

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

            }
        });

        //metodo que ocorrera ao rodar a classe
        extrairMercadosBanco();

        //realiza os processos do botao de pesquisar
        btNavegar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                lista = listaCompras.getText().toString();
                if (!lista.equals("")) {
                    //método que divide a String e coloca numa lista
                    separarElementos();
                    //busca os produtos no banco
                    try {
                        firestore = FirebaseFirestore.getInstance();
                        firestore.collection("Produtos")
                                .get()
                                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                        if (task.isSuccessful()) {
                                            //lista auxiliar que vai ser usada para a verificação
                                            List<Produto> aux = new ArrayList<Produto>();

                                            for (QueryDocumentSnapshot document : task.getResult()) {
                                                Produto p = new Produto(document.getString("nome"), document.getString("cnpj"), document.getString("codigo"),
                                                        Float.parseFloat(document.getString("preco")), document.getString("unidade"));
                                                aux.add(p);
                                            }

                                            //método para separar os produtos do banco de acordo com os itens que o usuario digitou na lista de compras
                                            produtos = Produto.separarProdutos(aux, itens);

                                            //método para reorganizar uma lista de produtos pela ordem de mais barato para mais caro
                                            produtos = Produto.organizarPorPreco(produtos);

                                            try {

                                                if(!(mercadosBanco==null)){
                                                List<ProdutoMercado> produtosMercado = ProdutoMercado.separaProdutoPorMercado(produtos, mercadosBanco);

                                                if (produtosMercado != null && produtosMercado.size() > 0) {
                                                    Intent it = new Intent(getContext(), PesquisaListaProdutos.class);
                                                    it.putExtra("produtos", (ArrayList) produtos);
                                                    it.putExtra("mercados", (ArrayList) mercadosBanco);
                                                    startActivity(it);
                                                } else {
                                                    Toast.makeText(getContext(), "Nenhum produto encontrado.", Toast.LENGTH_LONG).show();
                                                }
                                                }else{
                                                    Toast.makeText(getContext(), "Nenhum mercado encontrado", Toast.LENGTH_SHORT).show();
                                                }
                                            } catch (IOException e) {

                                                Log.e("Ioexception", e.getMessage());

                                                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                                                builder.setMessage("Ocorreu erro de conexão")
                                                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                                            public void onClick(DialogInterface dialog, int id) {
                                                                dialog.cancel();
                                                            }
                                                        });
                                                AlertDialog alertDialog = builder.create();
                                                alertDialog.show();
                                                e.printStackTrace();
                                            }

                                        }
                                    }
                                });
                    } catch (Exception e) {
                        Log.e("ERRO: ", e.getMessage());
                        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                        builder.setMessage("Ocorreu erro de :" + e.getMessage())
                                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {
                                        dialog.cancel();
                                    }
                                });
                        AlertDialog alertDialog = builder.create();
                        alertDialog.show();
                        e.printStackTrace();
                    }
                }else{
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    builder.setMessage("Digite a lista que deseja pesquisar no espaço indicado")
                            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                    AlertDialog alertDialog = builder.create();
                    alertDialog.show();
                }
            }

        });


        return v;
}

    //método que separa as strings e coloca numa lista
    private void separarElementos() {
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


    //método que devolve todos os mercados do banco
    private void extrairMercadosBanco(){
        //pegar a localização do usuário para buscar apenas os mercados próximos do usuario
        SharedPreferences preferences = getContext().getSharedPreferences("LocalizacaoUsuario", Context.MODE_PRIVATE);
        String cidadeU = preferences.getString("cidadeU","");
        String bairroU = preferences.getString("bairroU","");

        Log.e("cidade/bairro",cidadeU + bairroU);

        //caso os lvaores de cidade e mercado sejam diferenes de vazios é que sera feita a seleção
        if(!cidadeU.equals("")||!bairroU.equals("")) {
            try {
                firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Mercados").whereEqualTo("cidade", cidadeU.toUpperCase())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Mercado m = new Mercado(document.getString("nome"), document.getString("cnpj"), document.getString("cidade"),
                                                    document.getString("bairro"), document.getString("logradouro"), 0);
                                            mercadosBanco.add(m);
                                        }

                                        Log.e("Mercados",mercadosBanco+"");
                                        extrairAvaliacoesBanco();
                                    }
                                }
                            });

            } catch (Exception e) {
                Log.e("erro", "deu erro");
                e.printStackTrace();
            }

        }else{
            //caso na se tenha a localisaçao do usuario ele poderá ver todos os mercados
            Log.e("nao há cidade","------------------->"+cidadeU+"   "+bairroU);
            AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
            builder.setMessage("Não foi encontrado nenhum mercado em suas proximidades, deseja ver sua lista de compra em qualquer mercado ?")
                    .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            //seleciona qualquer mercado do banco
                            dialog.cancel();

                            try {
                                firestore = FirebaseFirestore.getInstance();
                                firestore.collection("Mercados").get()
                                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                            @Override
                                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                                if (task.isSuccessful()) {
                                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                                        Mercado m = new Mercado(document.getString("nome"), document.getString("cnpj"), document.getString("cidade"),
                                                                document.getString("bairro"), document.getString("logradouro"), 0);
                                                        mercadosBanco.add(m);
                                                    }

                                                    extrairAvaliacoesBanco();
                                                }
                                            }
                                        });

                            } catch (Exception e) {
                                Log.e("erro", "deu erro");
                                e.printStackTrace();
                            }
                        }
                    }).setNegativeButton("Não", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            //fecha a mensagem e só
                            dialogInterface.cancel();
                            mercadosBanco=null;
                        }
                    });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();




        }
    }

    //devolver a avaliação de um mercado
    private void extrairAvaliacoesBanco() {
        try {
            //buscar todas as avaliações do mercado no banco
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("Avaliacao")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if (task.isSuccessful()) {
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    avaliacoesBanco.add(new Avaliacao(Double.parseDouble(document.getString("avaliacao")), document.getString("cnpj")));
                                }
                                avaliacoesBanco = Avaliacao.avaliacoesMercado(avaliacoesBanco);
                                Mercado.configurarAvaliacoesDosMercados(mercadosBanco,avaliacoesBanco);
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}




