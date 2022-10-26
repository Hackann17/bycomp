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
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.projetofinal.ui.home.HomeFragment;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONException;
import org.json.JSONObject;

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
    TextView Criarconta;
    Button btLogar;
    String email, senha;
    Usuario u;

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
                                    startActivity(new Intent( Login.this, HomeFragment.class));
                                    Toast.makeText(Login.this, "Bem-vindo", Toast.LENGTH_SHORT).show();
                                    // Sign in success, update UI with the signed-in user's information
                                    //FirebaseUser user = mAuth.getCurrentUser();
                                    //updateUI(user);
                                } else {
                                    Toast.makeText(Login.this, "Verifique se o email e a senha estão corretos", Toast.LENGTH_SHORT).show();
                                    // If sign in fails, display a message to the user.

                                    //updateUI(null);
                                }

                            }
                        });

                    }

                } catch (Exception e){
                    e.getStackTrace();

                }

            }
        });
    }
}