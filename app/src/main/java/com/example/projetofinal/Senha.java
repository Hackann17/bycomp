package com.example.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Senha extends AppCompatActivity {

    TextView textView39;
    TextView textView40;
    TextView textView41;
    EditText idSenhaAtual;
    EditText idNovaSenha;
    Button btAlterarSenha2;
    Button btVoltarTela;

    FirebaseFirestore db = FirebaseFirestore.getInstance();

    String nomedocumento;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_senha);


        idSenhaAtual = findViewById(R.id.idSenhaAtual);
        idNovaSenha = findViewById(R.id.idNovaSenha);

        btAlterarSenha2 = findViewById(R.id.btAlterarSenha2);


        idSenhaAtual.setHint("Digite aqui sua senha atual ");
        idNovaSenha.setHint("Digite aqui sua nova senha");

        //puxando informaçoes shared preferences
        SharedPreferences preferences = getSharedPreferences("Salvar",MODE_PRIVATE);

        nomedocumento = preferences.getString("NomeDocumento","");

        //metodo para alterar a senha no banco
        btAlterarSenha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String senhaAt, senhaNv;

                senhaAt = idSenhaAtual.getText().toString();
                senhaNv = idNovaSenha.getText().toString();

                if (senhaNv != null && senhaAt != null) {
                    //método para alterar a senha
                    AlterarSenha(senhaNv);


                } else {

                    Toast.makeText(Senha.this, "Preencha todos os campos ", Toast.LENGTH_SHORT).show();
                }


            }
        });


    }

    private void AlterarSenha(String senhaNv) {

        //alteraçao de dados na altentificaçao
        FirebaseUser fu = FirebaseAuth.getInstance().getCurrentUser();

        try {
            fu.updatePassword(senhaNv).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {
                    if (task.isSuccessful()) {
                        Toast.makeText(Senha.this, "Alteraçao realizada com sucesso ", Toast.LENGTH_SHORT).show();
                    }
                    Toast.makeText(Senha.this, "", Toast.LENGTH_SHORT).show();


                }
            });

            //alteraçao de dados no firestore

            FirebaseAuth.getInstance().getCurrentUser().getEmail();
            DocumentReference dr = db.collection("Usuarios").document(nomedocumento);

            dr.update("senha",senhaNv).addOnSuccessListener(new OnSuccessListener<Void>() {
                @Override
                public void onSuccess(Void unused) {
                    Log.e("Seha","Sucesso");
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {


                    Log.e("Seha","----------------->"+"Derrota"+ e);

                }
            });

        }

        catch (Exception e){
            Log.e("ErroSenha","------------------->"+ e);
        }





    }

}