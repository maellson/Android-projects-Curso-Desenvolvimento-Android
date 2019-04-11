package com.cgparking.zonaazul.helper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.cgparking.zonaazul.control.ConfigFirebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
        return auth.getCurrentUser();

    }

    public static boolean atualizarNomeUsuario(String nome){
         try{
             FirebaseUser user = getUsuarioAtual();
             UserProfileChangeRequest profile = new UserProfileChangeRequest.Builder()
                     .setDisplayName(nome)
                     .build();
             user.updateProfile(profile).addOnCompleteListener(new OnCompleteListener<Void>() {
                 @Override
                 public void onComplete(@NonNull Task<Void> task) {
                     if(!task.isSuccessful()){
                         Log.d("Perfil", "Erro ao atualizar perfil!");
                     }
                 }
             });
             return true;

         }//fim do try
         catch (Exception e){
             e.printStackTrace();
             return false;

         } //fim do catch


    } //fim do atualizarNomeUsuario

}//fim da classe
