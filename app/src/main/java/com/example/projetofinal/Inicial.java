package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Inicial extends AppCompatActivity {

    Button butcomecar;

    @Override
    protected void onStart() {
        super.onStart();
        //verificando se o ususao ruio é null
        SharedPreferences editor = getSharedPreferences("Salvar",MODE_PRIVATE);

        if(ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)!= PackageManager.PERMISSION_GRANTED)
        {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},120);
        }
        else
        if(editor.getString("log", "").equals("true")){
            startActivity(new Intent( Inicial.this,Bycomp.class));
            finish();
        }



    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inicial);
        butcomecar = findViewById(R.id.butComecar);

        butcomecar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Inicial.this, Login.class));
            }
        });

    }


}