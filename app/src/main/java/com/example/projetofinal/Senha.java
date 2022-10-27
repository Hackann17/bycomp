package com.example.projetofinal;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

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

        idSenhaAtual = findViewById(R.id.idSenhaAtual);
        idNovaSenha = findViewById(R.id.idNovaSenha);

        btAlterarSenha2 = findViewById(R.id.btAlterarSenha2);


        idSenhaAtual.setHint("Digite aqui sua senha atual ");
        idNovaSenha.setHint("Digite aqui sua nova senha");


        Toast.makeText(this, "Usuario"+FirebaseAuth.getInstance().getCurrentUser().getDisplayName(), Toast.LENGTH_SHORT).show();



        //metodo para alterar a senha no banco
        btAlterarSenha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });






    }
}

