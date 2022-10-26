package com.example.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
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
//import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class Cadastro extends AppCompatActivity {

    private FirebaseAuth mAuth;

    TextView txtentrar;
    Button btCadastrar;
    View v;

    EditText inputUser;
    EditText inputSenha;
    EditText inputEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        mAuth = FirebaseAuth.getInstance();

        inputUser = findViewById(R.id.inputUser);
        inputSenha = findViewById(R.id.inputSenha);
        inputEmail = findViewById(R.id.inputEmail);


        txtentrar = v.findViewById(R.id.txtEntreAqui);
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

                String usuario, senha, email;

                usuario = inputUser.getText().toString();
                senha= inputSenha.getText().toString();
                email= inputEmail.getText().toString();

                //pegando as strigs e as colocando como parametro, é criado um metodo do firebase
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(email,senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            //pega uma instancia do usuario em questao ao salvar
                            FirebaseFirestore ff = FirebaseFirestore.getInstance();

                            //usado com lista definidora para salvamentod de várias informações
                            Map<String, Object> mp = new HashMap<>();

                            mp.put("nome usuario", usuario);
                            mp.put("senha", senha);
                            mp.put("email", email);

                            //o qeu sera colocado no firebase, expecificando as coleçoes (usuarios no caso)
                            DocumentReference documentReference = ff.collection("Usuarios").document(email);

                            //aplica no firebase as informaçoes montadas antes

                            try {
                                documentReference.set(mp).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        limparCampos();
                                        Toast.makeText(Cadastro.this,"Cadastro realizado com sucesso", Toast.LENGTH_SHORT);
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("TAGGGGG", "---->" + e);
                                    }
                                });

                            }
                            catch (Exception e){
                                Log.e("ERRO", e.toString());
                            }
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

