package com.example.projetofinal;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.navigation.NavigationView;

import androidx.core.app.ActivityCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import com.example.projetofinal.databinding.ActivityBycompBinding;

import java.io.IOException;
import java.util.List;

import classesmodelos.Usuario;

public class Bycomp extends AppCompatActivity {

    //A gente precisa resolver para pegar a cidadedo usuario
    private AppBarConfiguration mAppBarConfiguration;
    private ActivityBycompBinding binding;

    //O classe abaixo irá fornecer os métodos para interagir com o GPS bem como recuperar os dados do posicionamento
    private Location location;
    private LocationManager locationManager;
    private Address endereco;

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

        DrawerLayout drawer = binding.drawerLayout;
        NavigationView navigationView = binding.navView;
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_home, R.id.nav_pesquisar,
                R.id.nav_promocoes,R.id.nav_adicionarnota,
                R.id.nav_historico,R.id.nav_avaliacao,
                R.id.nav_perfil)
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

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            //solicitar a permição do usuario, funciona caso seja permitido
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},120);
        }

        Toast.makeText(this, "Localização liberada", Toast.LENGTH_SHORT).show();

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);


        if (location != null){

            longitude = location.getLongitude();
            latitude = location.getLatitude();

        }


        //retornando um toast para ver os dados adquirido ,VOLTADO PARA TESTES ESSE TOAST
        try {
            endereco = BuscaEndereco(latitude,longitude);
            Log.e("Endereço","--------------------------->" +
                    endereco);


            //objeto provisorio , so para deixar claro que a loalizaçao sera armazenada no usuario
          Usuario usuario = new Usuario(endereco.getSubLocality(),"yasy","yaaas@gmail.com","yasy1234");



            //DAR UM JEITO DE PEGAR A O NOME DA CIDADE
           Toast.makeText(this,
                    "Latitude"+latitude+", "+
                            "Logitude"+longitude+", "+
                            "Bairro"+endereco.getSubLocality()+", "+//esse aki pega o bairro
                            "Cidade"+ endereco.getLocality()+", "+
                            "Cidade"+ endereco.getLocality()+", "+
                            "?"+ endereco.getFeatureName()+", "+
                            "?"+ endereco.getLocale()+", "+
                            "País "+endereco.getCountryName()
                    , Toast.LENGTH_SHORT).show();

            Log.e("Mensagem","--------------------------->" +
                            "Latitude"+latitude+", "+
                            "Logitude"+longitude+", "+
                            "Bairro"+endereco.getSubLocality()+", "+//esse aki pega o bairro
                            "Cidade"+ endereco.getLocality()+", "+
                    "Cidade"+ endereco.getLocality()+", "+

                    "País "+endereco.getCountryName()+
                    "?"+ endereco.getFeatureName()+", "+
                            "?"+ endereco.getLocale());

        }

        catch (IOException e) {
            Toast.makeText(this,  e.getMessage(), Toast.LENGTH_SHORT).show();
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



}