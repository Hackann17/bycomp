package com.example.projetofinal;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.widget.SearchView;
import android.widget.TextView;
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
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import classesmodelos.Mercado;
import classesmodelos.Produto;
import classesmodelos.Usuario;

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
    List<Produto> produtos = new ArrayList<Produto>(); //lista que vai armazenar os dados vindos do firebase

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

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

                                        for (Produto p : produtos) {
                                            Log.e("ordem de mercado: ", p.getNome()+" "+p.getPreco()+" "+p.getCnpj());
                                        }
                                    }
                                }
                            });

                    if(produtos != null){
                        //leva as informaçoes para outra tela de pesquisa
                        //gerando a intent
                        Intent it = new Intent(Bycomp.this,PesquisaProduto.class);
                        it.putExtra("produtos",(ArrayList<Produto>) produtos);
                    }else{
                        Toast.makeText(Bycomp.this, "Não temos esse produto", Toast.LENGTH_SHORT).show();
                    }



                    startActivity(new Intent(Bycomp.this,PesquisaProduto.class));
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
                R.id.nav_perfil)
                .setOpenableLayout(drawer)
                .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_bycomp);
        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        /*navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                if(item.getItemId()==R.id.nav_sair){
                  FirebaseAuth.getInstance().signOut();

                    startActivity(new Intent(Bycomp.this, Login.class));

                    finish();
                }
                return false;
            }
        });*/
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


            Log.e("Endereço","--------------------------->" + endereco);


            //adicionar a localizaçao do usuario para ser pega a qualquer momento



            //DAR UM JEITO DE PEGAR A O NOME DA CIDADE
           Log.e("localização: ",
                    "Latitude"+latitude+", "+
                            "Logitude"+longitude+", "+
                            "Bairro"+endereco.getSubLocality()+", "+//esse aki pega o bairro
                            "Cidade"+ endereco.getSubAdminArea());

        }

        catch (IOException e) {
            //Toast.makeText(this,  e.getMessage(), Toast.LENGTH_SHORT).show();
            Log.e("TAGCATCH", "---------------->" + e);
        }

        catch (Exception e ){

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



    private List<Mercado> separarProdutosPorMercado(){
        return null;
    }


//metodo para achar o endereço do usuario

    public Address BuscaEndereco(double lat, double lon) throws IOException {

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
//adicionando nome ao navheader

    /*public void texto() {

        SharedPreferences preferences = getSharedPreferences("Salvar", Context.MODE_PRIVATE);
        String nomedocumento = preferences.getString("NomeDocumento","");

        DocumentReference documentReference = db.collection("Usuarios").document(nomedocumento);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                if (value != null) {

                    navEmail.setText(value.getString("email"));


                }

            }


        });
    }
*/


}