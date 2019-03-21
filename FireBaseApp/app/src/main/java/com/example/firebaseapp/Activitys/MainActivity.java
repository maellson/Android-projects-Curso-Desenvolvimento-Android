package com.example.firebaseapp.Activitys;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.example.firebaseapp.Classes.Produto;
import com.example.firebaseapp.Classes.Usuario;
import com.example.firebaseapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {

    //pega a referencia e vai  para o no raiz do firebase
    private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    //pega a referencia e vai  para um no especifico do firebase
    //private DatabaseReference reference_2 = FirebaseDatabase.getInstance().getReference("usuarios");


    //Login FireBase
    private FirebaseAuth mAuth= FirebaseAuth.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //private
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

// ...

        //adiciona um no child e seta o valor
        //reference.child("pontos").setValue("200");

        //adiciona dois nos child e seta o valor
        /*reference.child("usuarios2").child("001").child("nome").setValue("Maelson");*/

        //removendo childs
        /*reference.child("usuarios2").removeValue();*/

        //testes usando referencia direta para o no usuario
        /*reference_2.child("003").child("Nome").setValue("Meu nome_test");
        reference_2.child("003").child("Sobrenome").setValue("Meu sobrenome_test");*/
/*
        //trabalhando direto com uma referencia child com nome do nó.
        DatabaseReference usuarios = reference.child("usuarios");
        //DatabaseReference usuarios = reference.child("usuarios").child("001"); para observancia do listner em apenas 01 no
        DatabaseReference produtos = reference.child("produtos");

        //assim que houver mudanca nos usuario esse listner sera notificado
        usuarios.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.i("FIREBASE", dataSnapshot.getValue().toString());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        Usuario usuario = new Usuario();
        usuario.setIdade(27);
        usuario.setNome("Jérsica");
        usuario.setSobrenome("Silva");
        usuarios.child("004").setValue(usuario);

        Produto produto = new Produto();
        produto.setDescricao("Dell Gamer");
        produto.setMarca("Dell");
        produto.setPreco(5499.00);
        produtos.child("001").setValue(produto);
    */


    /*//cadastro
        mAuth.createUserWithEmailAndPassword("maellson@hotmail.com","qwe123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("CreateUser", "createUserWithEmail:success");


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("CreateUser", "createUserWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    */

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        if(mAuth.getCurrentUser()!= null){
            Log.i("CreateUser", "createUserWithEmail:success");
        }else{
            Log.i("CreateUser", "createUserWithEmail:failure");


        }



    }
}
