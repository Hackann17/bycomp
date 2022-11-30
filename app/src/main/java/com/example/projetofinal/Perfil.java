package com.example.projetofinal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.io.IOException;

import classesmodelos.Produto;
import classesmodelos.Usuario;

public class Perfil extends Fragment {

    FirebaseFirestore db = FirebaseFirestore.getInstance();


    Button btAlterarDados;
    Button btAlterarSenha;
    EditText inputNomeUsuario;
    EditText inputEmailUsuario;
    TextView textView37;
    TextView textView38;

    String nomedocumento;

    View v;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public Perfil() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static Perfil newInstance(String param1, String param2) {
        Perfil fragment = new Perfil();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        v = inflater.inflate(R.layout.fragment_perfil, container, false);

        //puxando informaçoes shared preferences
        SharedPreferences preferences = getContext().getSharedPreferences("Salvar", Context.MODE_PRIVATE);

        nomedocumento = preferences.getString("NomeDocumento","");

        try {
            //declarando os inputs / pegar informações dos inputs
            textView37 = v.findViewById(R.id.textView37);
            textView38 = v.findViewById(R.id.textView38);
            inputEmailUsuario = v.findViewById(R.id.inputEmailUsuario);

            btAlterarDados = v.findViewById(R.id.btAlterarDados);
            btAlterarSenha = v.findViewById(R.id.btAlterarSenha);

            btAlterarSenha.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    startActivity(new Intent(getContext(), Senha.class));

                }
            });

            // pegar as informações do banco local e passar para os inputs

            RecolhendoNome();
        }
        catch (Exception e) {
            Toast.makeText(v.getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
        }


        btAlterarDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //puxar metodo atualizar

                String nomeusuario,emailusuario;

                //lembrar de colocar a verificaçao do input de nomedousuario
                if(TextUtils.isEmpty(inputEmailUsuario.getText().toString().trim())){
                    Toast.makeText(getContext(), "Preencha os campos", Toast.LENGTH_SHORT).show();
                }
                else{
                    emailusuario = inputEmailUsuario.getText().toString();
                    try {

                        //esse metod alterar os dados somente no firestore ,deve se ser implementados as aleraçoes vigentes no authentification
                        Atualizardados(emailusuario);
                        inputEmailUsuario.setText("");

                    }
                    catch (Exception e){
                        Toast.makeText(getContext(), "Erro"+e, Toast.LENGTH_SHORT).show();
                        Log.e("ErrMetodoAtualizar","------------------------->"+e);
                    }
                }
            }


        });

        return v;

    }

    private void Atualizardados(String emailusuario) throws IOException {
        AlterarEmailU(emailusuario);

    }

    private void AlterarEmailU(String emailusuario) throws IOException {

        //atualizaçao de dados no authentification
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        try{
        user.updateEmail(emailusuario)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d("Atualizaçao", "User email address updated.");
                        }
                    }
                });}
        catch (Exception e){

            Log.e("Erro ","------------------>"+e);
        }

        //atualizaçao de dados no firestore
        DocumentReference documentReference = db.collection("Usuarios").document(nomedocumento);

        documentReference.update("email",emailusuario).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(getContext(), "Dados atualizados com sucesso", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(getContext(), "Houve um problema ", Toast.LENGTH_SHORT).show();

                Log.e("Erro","=============>"+e);
            }
        });
    }


    //metodo que desloga o usuario
    private void RecolhendoNome(){
        DocumentReference documentReference = db.collection("Usuarios").document(nomedocumento);

        documentReference.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                if(value != null) {
                    inputEmailUsuario.setHint(value.getString("email"));
                }
            }
        });
    }




}