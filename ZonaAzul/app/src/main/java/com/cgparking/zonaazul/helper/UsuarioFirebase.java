package com.cgparking.zonaazul.helper;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.cgparking.zonaazul.activity.FiscalActivity;
import com.cgparking.zonaazul.activity.RequisicoesActivity;
import com.cgparking.zonaazul.control.ConfigFirebase;
import com.cgparking.zonaazul.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class UsuarioFirebase {

    public static FirebaseUser getUsuarioAtual(){
        FirebaseAuth auth = ConfigFirebase.getFirebaseAutenticacao();
        return auth.getCurrentUser();
    }

    public static Usuario getDadosUsuarioLogado(){
        FirebaseUser firebaseUser = getUsuarioAtual();
        Usuario usuario = new Usuario();
        usuario.setId(firebaseUser.getUid());
        usuario.setNome(firebaseUser.getDisplayName());
        usuario.setEmail(firebaseUser.getEmail());

        return usuario;


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

    public static void redirecionaUsuarioLogado(final Activity activity){

        FirebaseUser user = getUsuarioAtual();

        if(user !=null){//VERIFICA SE O USUARIO ESTA LOGADO

            DatabaseReference usuariosRef = ConfigFirebase.getFirebaseDatabase()
                    .child("usuarios")
                    .child(getIdUsuario());

            usuariosRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    Usuario usuario = dataSnapshot.getValue(Usuario.class);
                    String tipousuario = usuario.getTipo();

                    if (tipousuario.equals("Fiscal")){

                        Intent intent  = new Intent(activity,
                                FiscalActivity.class);
                        activity.startActivity(intent);

                    } else {

                        Intent intent  = new Intent(activity,
                                RequisicoesActivity.class);
                        activity.startActivity(intent);

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }


    }//Fim redirecionaUsuarioLogado()

    public static String getIdUsuario(){
        return getUsuarioAtual().getUid();
    }
}//fim da classe
