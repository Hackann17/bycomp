package com.example.projetofinal;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;

public class Sair extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //criar tela de confirmação para perguntar se o usuario deseja deslogar
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Deseja sair?")
                .setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //deslogar o usuario
                        FirebaseAuth.getInstance().signOut();
                        startActivity(new Intent(getContext(), Login.class));
                    }
                })
                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //cancelar e retornar para a tela principal
                        dialog.cancel();
                        startActivity(new Intent(getContext(), Bycomp.class));
                    }
                });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sair, container, false);
    }
}