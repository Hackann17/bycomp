package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Senha extends AppCompatActivity {

    TextView textView39;
    TextView textView40;
    TextView textView41;
    EditText idSenhaAtual;
    EditText idNovaSenha;
    Button btAlterarSenha2;
    Button btVoltarTela;

     View p;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);
    }
}

