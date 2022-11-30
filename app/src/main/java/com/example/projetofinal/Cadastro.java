package com.example.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import classesmodelos.Mercado;

public class Cadastro extends AppCompatActivity {

    private FirebaseFirestore firestore;

    TextView txtentrar;
    Button btCadastrar;
    View v;

    EditText inputUser;
    EditText inputSenha;
    EditText inputEmail;

    String usuario, senha, email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        inputUser = findViewById(R.id.inputUser);
        inputSenha = findViewById(R.id.inputSenha);
        inputEmail = findViewById(R.id.inputEmail);

        txtentrar = findViewById(R.id.txtEntreAqui);
        btCadastrar = findViewById(R.id.btCadastrar);


        txtentrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Cadastro.this, Login.class));
            }
        });

        //metodo de cadastrar
        btCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            //implementando firebase

                usuario = inputUser.getText().toString().trim();
                senha= inputSenha.getText().toString().trim();
                email = inputEmail.getText().toString().trim();


                //pegando as strigs e as colocando como parametro, é criado um metodo do firebase
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //Gera um codigo de recuperação para o usuario
                            int codigo;
                            Random r = new Random();
                            codigo = r.nextInt(899999)+100000;
                            Log.e("Codigo ", codigo+"");

                            AlertDialog.Builder builder = new AlertDialog.Builder(Cadastro.this);

                            builder.setMessage("Esse é o seu código de recuperação: " + codigo + ", caso esqueça sua senha. Você poderá vê-lo outra vez indo em Home > Perfil").setPositiveButton("Entendi", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            });
                            AlertDialog alertDialog = builder.create();
                            alertDialog.show();

                            //pega uma instancia do usuario em questao ao salvar
                            FirebaseFirestore ff = FirebaseFirestore.getInstance();

                            //usado com lista definidora para salvamentod de várias informações
                            Map<String, String> mp = new HashMap<>();

                            mp.put("senha", senha);
                            mp.put("email", email);
                            mp.put("codigo", codigo+"");

                            //o qeu sera colocado no firebase, expecificando as coleçoes (usuarios no caso)
                            DocumentReference documentReference = ff.collection("Usuarios").document(email);

                            //aplica no firebase as informaçoes montadas antes
                            try {
                                documentReference.set(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        limparCampos();

                                        //adicionando os dados no shered preferences

                                        SharedPreferences.Editor editor = getSharedPreferences("Salvar",MODE_PRIVATE).edit();
                                        editor.putString("NomeDocumento",email);
                                        editor.putString("log","false");

                                        editor.commit();

                                        Toast.makeText(Cadastro.this,"Cadastro realizado com sucesso", Toast.LENGTH_SHORT);


                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(Cadastro.this, "A realização do seu cadastro falhou", Toast.LENGTH_SHORT).show();
                                        Log.e("TAGGGGG", "---->" + e);
                                    }
                                });

                            } catch (Exception e){
                                Log.e("ERRO", e.toString());
                            }
                        } else {
                            //significa que o email já existe no banco
                            Toast.makeText(Cadastro.this,"Esse e-mail já está cadastrado. Por favor, escolha outro", Toast.LENGTH_LONG);
                            inputEmail.setTextColor(Color.parseColor("#B10101"));

                        }
                    }
                });
            }
        });


    }

    private void limparCampos() {
        //Criar um objeto do ConstraintLayout que é aonde todos os EditTexts estão
        ConstraintLayout telaCadastro = findViewById(R.id.actyCadastro);

        //Laço para percorrer todos os componentes dentro do ConstraintLayout
        for (int i = 0; i < telaCadastro.getChildCount(); i++) {
            //Recupera o primeiro componente encontrado
            View view = telaCadastro.getChildAt(i);
            //Verifica se esse componente é um EditText
            if (view instanceof EditText) {
                //Limpa o texto
                ((EditText) view).setText("");
            }
        }
    }



}

