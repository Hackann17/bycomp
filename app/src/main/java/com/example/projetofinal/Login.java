package com.example.projetofinal;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
//import androidx.constraintlayout.widget.ConstraintLayout;

import android.content.Intent;
//import android.graphics.Bitmap;
import android.content.SharedPreferences;
import android.os.Bundle;
//import android.util.Base64;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import classesmodelos.Usuario;

//import java.io.ByteArrayOutputStream;
//import java.text.DateFormat;
//import java.text.SimpleDateFormat;
//import java.util.Date;

public class Login extends AppCompatActivity {

    //criando chaves constantes para o shared preferences
    public static final String SHARED_PREFS = "shared_prefs";

    //chave para guardar o user
    public static final String USUARIO_KEY = "usuario_key";

    //chave para guardar a senha
    public static final String SENHA_KEY = "senha_key";

    // variaveis para o shared preferences
    SharedPreferences sharedpreferences;

    FirebaseAuth mAuth;
    EditText emailEd, senhaEd;
    TextView Criarconta, esqueceuSenha;
    Button btLogar;
    String email, senha;
    Usuario u;

    boolean modoTeste = false;
    /*//puxando informaçoes shared preferences
    SharedPreferences preferences = getSharedPreferences("Salvar",MODE_PRIVATE);
    boolean logado=preferences.getBoolean("log",false);*/

    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //pegando os ids
        Criarconta= findViewById(R.id.Criarconta);
        btLogar = findViewById(R.id.btLogar);
        emailEd = findViewById(R.id.edtEmail);
        senhaEd = findViewById(R.id.inputSenhaL);
        esqueceuSenha = findViewById(R.id.esqueceuLoginTxt);
        mAuth = FirebaseAuth.getInstance();

       // sharedpreferences = getSharedPreferences(SHARED_PREFS, Cadastro.MODE_PRIVATE);
        //email = sharedpreferences.getString(USUARIO_KEY, null);
        //senha = sharedpreferences.getString(SENHA_KEY, null);


        //ir para tela de cadastro
        Criarconta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( Login.this,Cadastro.class)
                );
            }
        });

        esqueceuSenha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent( Login.this, RedefinirSenha.class));
            }
        });

        //entrar na tela do aplicativo
        btLogar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                email = emailEd.getText().toString();
                senha = senhaEd.getText().toString();

                try{
                    //verifica se os campos estão preenchidos
                    if(TextUtils.isEmpty(emailEd.getText().toString().trim()) && TextUtils.isEmpty(senhaEd.getText().toString().trim())){

                            Toast.makeText(Login.this, "Preencha todos os campos", Toast.LENGTH_SHORT).show();

                    } else {
                        mAuth.signInWithEmailAndPassword(email, senha).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){

                                    SharedPreferences.Editor editor = getSharedPreferences("Salvar",MODE_PRIVATE).edit();

                                    //caso a autentificação seja bem sucedida, as informaçoes do usuario serao acssadas no banco de dados
                                    // e serao guardadas em um shared
                                    FirebaseFirestore firestore = FirebaseFirestore.getInstance();
                                    firestore.collection("Usuarios").whereEqualTo("email",email).get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                                        @Override
                                        public void onComplete(@NonNull Task<QuerySnapshot> task) {

                                            if (task.isSuccessful()){
                                                Log.e("Login","--------------------------------->"+"entrou if");
                                                DocumentSnapshot document = task.getResult().getDocuments().get(0);

                                                if (task.getResult().getDocuments().size()<=0){
                                                    Toast.makeText(Login.this, "Não foi possível encontrar suas informações", Toast.LENGTH_SHORT).show();

                                                }
                                                    //caso os dados sejam encontrados
                                                    editor.putString("NomeDocumento", document.getId());
                                                    editor.putString("log","true");
                                                    editor.putString("codigo",document.getString("codigo"));

                                                editor.apply();
                                                SharedPreferences edito = getSharedPreferences("Salvar",MODE_PRIVATE);
                                                Log.e("NOmedocumento","--------------------------------->"+document.getId());
                                                Log.e("Codigo","--------------------------------->"+document.getString("codigo"));
                                                Log.e("Codigo","--------------------------------->"+edito.getString("codigo",""));



                                                startActivity(new Intent( Login.this, Bycomp.class));
                                                finish();
                                            }

                                        }
                                    });

                                } else {
                                    Toast.makeText(Login.this, "Verifique se o email e a senha estão corretos", Toast.LENGTH_SHORT).show();
                                }

                            }
                        });

                    }

                }
                catch (Exception e){
                    Toast.makeText(Login.this, "Verifique sua conexão com a internet", Toast.LENGTH_SHORT).show();
                    e.getStackTrace();

                }

            }
        });


    }

}