package com.example.projetofinal.ui.home;

import static androidx.core.content.ContextCompat.getSystemService;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageSwitcher;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.example.projetofinal.R;
import com.example.projetofinal.databinding.FragmentHomeBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class HomeFragment extends Fragment {

    private FragmentHomeBinding binding;

    View view;
    Button IdLista;
    Button Idpromocoes;
    FloatingActionButton btLerNota;
    TextView txtview;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        HomeViewModel homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        view = binding.getRoot();

        final TextView textView = binding.textHome;
        homeViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);


        IdLista = view.findViewById(R.id.IdLista);
        Idpromocoes = view.findViewById(R.id.Idpromocoes);
        btLerNota = view.findViewById(R.id.btLerNota);
        txtview = view.findViewById(R.id.txtCronometro);






/*
//referente ao antigo codigo de localizaçao
        if (ActivityCompat.checkSelfPermission(binding.home.getContext(), Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {

            //solicitar a permição do usuario, funciona caso seja permitido


           requestPermissions(new String[]{Manifest.permission.ACCESS_FINE_LOCATION},120);

        }

        //esse aki é o problema
        locationManager = (LocationManager) getSystemService(view.getContext().LOCATION_SERVICE);
        location=locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
       // Log.e("TAGAPPEDRO", "----------->" + locationManager);




       if (location != null){

            longitude = location.getLongitude();
            latitude = location.getLatitude();
        }
*/

        /*
        //retornando um toast para ver os dados adquirido ,VOLTADO PARA TESTES ESSE TOAST
        try {
            endereco = BuscaEndereco(latitude,longitude);

           Toast.makeText(view.getContext(), " Latitude "+latitude+
                   "Logitude"+longitude+
                           "Cidade"+endereco.getLocality()+
                            "Estado"+endereco.getCountryName()
                    , Toast.LENGTH_SHORT).show();


        } catch (IOException e) {
         e.printStackTrace();
        }

*/

        IdLista.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.lista2);
            }
        });

        Idpromocoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Navigation.findNavController(view).navigate(R.id.promocao);
            }
        });

        btLerNota.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //inicializa o timer
                long duracao = TimeUnit.SECONDS.toMillis(1);

                //inicializa o contador
                new CountDownTimer(duracao, 1000) {
                    @Override
                    public void onTick(long l) {
                        //quando comecar
                        String sDuracao = String.format(Locale.ENGLISH, "%02d : %02d",
                                TimeUnit.MILLISECONDS. toMinutes(1),
                                TimeUnit.MILLISECONDS.toSeconds(1),
                                TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(1)));

                        //muda a string na tela
                        txtview.setText(sDuracao);
                    }

                    @Override
                    public void onFinish() {
                        //quando acabar
                        //esconde txtview
                        Intent open_camera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        startActivityForResult(open_camera, 100);

                        txtview.setVisibility(View.GONE);

                        //display toast
                        Toast.makeText(view.getContext(), "AFDADAFAF", Toast.LENGTH_SHORT).show();
                    }
                }.start();

            }


        });

        return view;
    }

    //ESSE AKI É PERIGOSO PRESTAR ATENÇAO POIS É GAMBIARRA
    private Object getSystemService(String locationService) {
        Object o = "Object";
        return o;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
/*
//referente ao antigo codigo de localizaçao

    //metodo para achar o endereço do usuario

    public Address BuscaEndereco(double lat, double lon) throws IOException {

        Geocoder geocoder;
        Address endereco= null ;

        List<Address> enderecos;

        geocoder = new Geocoder(getContext());


        //pega o endereço e coloca na lista de endereços
        enderecos = geocoder.getFromLocation(lat,lon,1);


        if(enderecos.size()>0){

            endereco = enderecos.get(0);

        }
        //retorna o primeiro endereço
        return endereco;
    }

*/

}