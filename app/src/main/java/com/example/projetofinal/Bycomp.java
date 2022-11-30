package com.example.projetofinal;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinal.databinding.ActivityBycompBinding;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classesmodelos.Avaliacao;
import classesmodelos.Mercado;
import classesmodelos.Produto;

public class Bycomp extends AppCompatActivity {

    //A gente precisa resolver para pegar a cidadedo usuario
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityBycompBinding binding;
    private FirebaseFirestore firestore;

    //O classe abaixo irá fornecer os métodos para interagir com o GPS bem como recuperar os dados do posicionamento
    private Location location;
    private LocationManager locationManager;
    private Address endereco;

    //private TextView navEmail ;

    private SearchView search;
    List<Produto> produtos = new ArrayList<>(); //lista que vai armazenar os dados vindos do firebase
    List<Mercado> mercadosBanco = new ArrayList<>();
    List<Avaliacao> avaliacoesBanco = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.e("oncreate","oncreate");

        binding = ActivityBycompBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.appBarBycomp.toolbar);
        binding.appBarBycomp.appBarLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        extrairMercadosBanco();

        //método da barra de pesquisa
        search = findViewById(R.id.searchViewProduto);
        search.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String produtoPesquisado) {

                //busca os produtos no banco
                try{
                    firestore = FirebaseFirestore.getInstance();
                    firestore.collection("Produtos")
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                    List<Produto> aux = new ArrayList<Produto>();

                                    if (task.isSuccessful()) {
                                        for (QueryDocumentSnapshot document : task.getResult()) {
                                            Produto p = new Produto(document.getString("nome"), document.getString("cnpj"), document.getString("codigo"),
                                                    Float.parseFloat(document.getString("preco")), document.getString("unidade"));
                                            aux.add(p);
                                        }

                                        List<String> produtoPesquisadoLista = new ArrayList<>();
                                        produtoPesquisadoLista.add(produtoPesquisado);

                                        produtos = Produto.separarProdutos(aux,produtoPesquisadoLista);

                                        //método para reorganizar uma lista de produtos pela ordem de mais barato para mais caro
                                        produtos = Produto.organizarPorPreco(produtos);

                                        List<ItemPesq> produtoMercado;

                                        try {
                                            produtoMercado = ItemPesq.separaProdutoPorMercado(produtos,mercadosBanco);
                                        } catch (IOException e) {
                                            produtoMercado = null;
                                        }

                                        if(produtoMercado!=null&&produtoMercado.size()>0) {
                                            //leva as informaçoes para outra tela de pesquisa
                                            //gerando a intent
                                            Intent it = new Intent(Bycomp.this, PesquisaProduto.class);
                                            it.putExtra("produtos", (ArrayList) produtoMercado);
                                            startActivity(it);
                                            finish();
                                        } else{
                                            Toast.makeText(Bycomp.this,"Nenhum produto encontrado.",Toast.LENGTH_LONG).show();
                                        }
                                    }
                                }
                            });


                } catch (Exception e){
                    Log.e("ERRO: ", e.getMessage());
                }
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {return false;}
        });

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_pesquisar,
                R.id.nav_promocoes,R.id.nav_adicionarnota,
                R.id.nav_historico,R.id.avaliacaoTela,
                R.id.nav_perfil,R.id.nav_social)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bycomp);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        //uma das primeiras coisas a se fazer para pegar a localizaçao é pedir a permissao
        //variveis para armazenar a latitude e a longitude
        double latitude = 0.0;
        double longitude = 0.0;


        /*verificaçao de permiçao*/
        try {

            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
            {
                //solicitar a permição do usuario, funciona caso seja permitido
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},120);
            }

            //Toast.makeText(this, "Localização liberada", Toast.LENGTH_SHORT).show();

            locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

            location = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
        } catch(Exception e){}

        if (location != null){

            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }

        //retornando um toast para ver os dados adquirido ,VOLTADO PARA TESTES ESSE TOAST
        try {
            endereco = BuscaEndereco(latitude,longitude);

            SharedPreferences.Editor editor = getSharedPreferences("LocalizacaoUsuario",MODE_PRIVATE).edit();

            editor.putString("bairroU",endereco.getSubLocality());
            editor.putString("cidadeU",endereco.getSubAdminArea());

            editor.commit();

        }

        catch (IOException e) {
            //Toast.makeText(this,  e.getMessage(), Toast.LENGTH_SHORT).show();
            Toast.makeText(this, "Conecte-se a internet para termos acesso a sua localização", Toast.LENGTH_SHORT).show();
            Log.e("TAGCATCH", "---------------->" + e);
        }

        catch (Exception e ){
            //Toast.makeText(this, "Ocorreu um problema ", Toast.LENGTH_SHORT).show();

            Log.e("errrooooo","-------------------->"+e);

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.bycomp, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bycomp);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }


//metodo para achar o endereço do usuario

    private Address BuscaEndereco(double lat, double lon) throws IOException {

        Geocoder geocoder;
        Address endereco= null ;

        List<Address> enderecos;

        geocoder = new Geocoder(this);


        //pega o endereço e coloca na lista de endereços
        enderecos = geocoder.getFromLocation(lat,lon,1);


        if(enderecos.size()>0){

            endereco = enderecos.get(0);

        }
        //retorna o primeiro endereço
        return endereco;
    }

    //método que devolve todos os mercados do banco
    private void extrairMercadosBanco(){
        //pegar a localização do usuário para buscar apenas os mercados próximos do usuario
        SharedPreferences preferences = getSharedPreferences("LocalizacaoUsuario", Context.MODE_PRIVATE);
        String cidadeU = preferences.getString("cidadeU","");
        String bairroU = preferences.getString("bairroU","");
        try {
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("Mercados").whereEqualTo("cidade",cidadeU.toUpperCase())
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
                                extrairAvaliacoesBanco();
                            }
                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //devolver a avaliação de um mercado
    private void extrairAvaliacoesBanco(){
        try {
            //buscar todas as avaliações do mercado no banco
            FirebaseFirestore firestore = FirebaseFirestore.getInstance();
            firestore.collection("Avaliacao")
                    .get()
                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                        @Override
                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
                            if(task.isSuccessful()){
                                for (QueryDocumentSnapshot document : task.getResult()) {
                                    avaliacoesBanco.add(new Avaliacao(Double.parseDouble(document.getString("avaliacao")),document.getString("cnpj")));
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