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
import classesmodelos.ProdutoMercado;
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
    List<ProdutoMercado> produtosMercado = new ArrayList<ProdutoMercado>();
    TextView limparLista; //texto clicavel para limpar o texto na lista de compras

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
                                        //lista auxiliar que vai ser usada para a verificação
                                        List<Produto> aux = new ArrayList<Produto>();

                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Produto p = new Produto(document.getString("nome"), document.getString("cnpj"), document.getString("codigo"),
                                                    Float.parseFloat(document.getString("preco")), document.getString("unidade"));
                                            aux.add(p);
                                        }

                                        //método para separar os produtos do banco de acordo com os itens que o usuario digitou na lista de compras
                                        produtos = Produto.separarProdutos(aux,itens);

                                        //método para reorganizar uma lista de produtos pela ordem de mais barato para mais caro
                                        produtos = Produto.organizarPorPreco(produtos);
                                        List<Mercado> mercados;
                                        try {
                                            mercados = compararLocal(produtos);
                                        } catch (IOException e) {
                                            Log.e("TESTE","gamer");
                                            mercados = null;
                                            e.printStackTrace();
                                        }


//                                        for (Produto p : produtos) {
//                                            Log.e("ordem de preço: ",p.getNome()+" "+p.getPreco()+" "+p.getCnpj());
//                                        }

                                        produtosMercado = separaProdutoPorMercado(produtos,mercados);

                                        //DANDO ERRO
                                        for (ProdutoMercado pm : produtosMercado){
                                            for(Produto p : pm.getProdutos())
                                            Log.e("ordem de mercado: ", p.getNome()+" "+p.getPreco()+" "+p.getCnpj() + " " + pm.getMercado().getCnpj());
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
        List<Mercado> mercados = new ArrayList<>();
        SharedPreferences preferences = getContext().getSharedPreferences("LocalizacaoUsuario", Context.MODE_PRIVATE);
        String bairroU = preferences.getString("bairroU", "");
        String cidadeU = preferences.getString("cidadeU", "");
        try {
            Log.e("TESTE","0");
            List<String> cnpjProdutos = new ArrayList<>();
            for (Produto produto : produtos) {
                cnpjProdutos.add(produto.getCnpj());
            }
                firestore = FirebaseFirestore.getInstance();
                firestore.collection("Mercados")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                Log.e("TESTE","1");
                                if (task.isSuccessful()) {
                                    Log.e("TESTE","2");
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Mercado m = new Mercado(document.getString("nome"), document.getString("cnpj"), document.getString("bairro"),
                                                document.getString("rua"), document.getString("uf"), document.getString("numero"), document.getDouble("avaliacao"));
                                        Log.e("cnpj",m.getCnpj());
                                        //verifica se o mecado possui o mesmo cnpj que o produto e se esta pelo menos no mesmo bairro ou cidade que o usuario
                                        if (m.getBairro().equals(cidadeU)&&cnpjProdutos.contains(m.getCnpj())) {
                                            //caso o mercado atenda aos requisitos o mercado sera adicionado a lista
                                            mercados.add(m);
                                        }
                                    }
                                } else {
                                    Toast.makeText(getContext(), "erro", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

        } catch (Exception e) {
            e.printStackTrace();
        }
        //talvez seja possivel retornar uma lista bem mais completa do que as identificaçoes dos mecados
        //que ja estao selecionados para a outra tela para serem selecionados de novo
        return  mercados;
    }

    private List<ProdutoMercado> separaProdutoPorMercado(List<Produto> produtos,List<Mercado> mercados){
        List<ProdutoMercado> produtoMercado = new ArrayList<>();
        //armazenar cnpjs para não repetir
        List<String> cnpjsListados = new ArrayList<>();

        for (int i = 0; i < produtos.size(); i++) {
            //pegar o cnpj do mercado do produto
            String cnpj = produtos.get(i).getCnpj();

            //verificar se ja existe um mercado na lista com esse cnpj
            if(!cnpjsListados.contains(cnpj)){
                cnpjsListados.add(cnpj);
                //lista com todos os produtos na lista de produtos que tem esse cnpj
                List<Produto> produtosDoMercado = new ArrayList<>();
                //for para adicionar todos os produtos com esse cnpj na lista
                for (Produto p : produtos) {
                    if(p.getCnpj().equals(cnpj)){
                        produtosDoMercado.add(p);
                    }
                }
                //trazer o mercado do banco pelo cnpj
                Mercado mercado = null;
                //for para encontrar o mercado da lista que tem o mesmo cnpj do produto
                for(Mercado m : mercados){
                    if(m.getCnpj().equals(cnpj)) {
                        mercado = m; break;
                    }
                }

                //adicionar o mercado e os produtos desse mercado na lista de produtoMercado
                produtoMercado.add(new ProdutoMercado(produtosDoMercado,mercado));
            }

        }
        return produtoMercado;
    }

    //método que devolve todos os mercados do banco
    private List<Mercado> extrairMercadosBanco(){
        List<Mercado> mercados = new ArrayList<>();
        try {
            //firestore = FirebaseFirestore.getInstance();
                firestore.collection("Mercados")
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                                if (task.isSuccessful()) {
                                    for (QueryDocumentSnapshot document : task.getResult()) {
                                        Mercado m = new Mercado(document.getString("nome"), document.getString("cnpj"), document.getString("bairro"),
                                                document.getString("rua"), document.getString("uf"), document.getString("numero"), document.getDouble("avaliacao"));
                                        mercados.add(m);
                                    }
                                }
                            }
                        });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return  mercados;
    }

}




