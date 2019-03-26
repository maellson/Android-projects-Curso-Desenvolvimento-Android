package com.example.firebaseapp.Activitys;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.firebaseapp.Classes.Produto;
import com.example.firebaseapp.Classes.Usuario;
import com.example.firebaseapp.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.UUID;


public class MainActivity extends AppCompatActivity {

    //pega a referencia e vai  para o no raiz do firebase
    //private DatabaseReference reference = FirebaseDatabase.getInstance().getReference();
    //pega a referencia e vai  para um no especifico do firebase
    //private DatabaseReference reference_2 = FirebaseDatabase.getInstance().getReference("usuarios");


    //Login FireBase
    //private FirebaseAuth mAuth= FirebaseAuth.getInstance();

    /*//envio de imagem*/
    private ImageView imageView;
    private Button btnUpload;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        //private
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //FirebaseUser currentUser = mAuth.getCurrentUser();
        //DatabaseReference usuarios = reference.child("usuarios");






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

        /*//verificando se o usuario está logado!
        if(currentUser!= null){
            Log.i("CurrentUser", "Usuario Logado");
        }else{
            Log.i("CurrentUser", "Usuario Deslogado");

        }*/



        /*//Deslogar usuario

        mAuth.signOut();

        */


        /*//LOGAR USUARIO
        mAuth.signInWithEmailAndPassword("maellson@hotmail.com","qwe123")
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.i("SignInUser", "Usuario Logado com Sucesso");


                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w("SignInUser", "Erro ao Logar Usuario", task.getException());
                            Toast.makeText(MainActivity.this, "Falha ao logar.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
                */

        /*//criando hash com identificador unico no firebase

        DatabaseReference usuarios = reference.child("usuarios");
        Usuario usuario = new Usuario();
        usuario.setIdade(25);
        usuario.setNome("Jersica");
        usuario.setSobrenome("Daiane");
        usuario.setSexo("Feminino");
        usuarios.push().setValue(usuario);*/


        //fazendo buscas na base
        //DatabaseReference usuarios = reference.child("usuarios");// especificando o nó
        //DatabaseReference usuarioSearch = usuarios.child("-LacFJ_q5S4S3Fjpf5ec"); //filtro por hash
        //Query usuarioSearch = usuarios.orderByChild("nome").equalTo("Maelson");//query  buscando valor especifico
        //Query usuarioSearch = usuarios.orderByKey().limitToFirst(2);//query buscando com limite de retorno na  pesquisa para os primeiros x
        //Query usuarioSearch = usuarios.orderByKey().limitToLast(3);//query  buscando com limite de retorno na  pesquisa para os ultimos y


        /*//Maior ou igual
        Query usuarioSearch = usuarios.orderByChild("idade").startAt(35);//query buscando usuario com idade maior ou igal a 35*/

        /*//Menor ou igual
        Query usuarioSearch = usuarios.orderByChild("idade").endAt(35);//query buscando usuario com idade menor ou igal a 35 */

        /*//Entre doi valores
        Query usuarioSearch = usuarios.orderByChild("idade")
                .startAt(27)//query buscando usuario com idade maior ou igal a 35
                .endAt(35);//query buscando usuario com idade menor ou igal a 35 */

        /*//filtro baseado em campo String
        Query usuarioSearch = usuarios.orderByChild("sexo").equalTo("Feminino");





        usuarioSearch.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Usuario userData = dataSnapshot.getValue(Usuario.class);
                if(userData != null){
                    Log.i("Dadosusuario:", dataSnapshot.getValue().toString());
                } else{
                //Log.i("Dadosusuario:", "Nome: "+userData.getNome()+", \n Idade: "+userData.getIdade());
                    Log.i("Dadosusuario:", "Usuario Inexistente");}
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                System.out.println("Falha na Leitura: " + databaseError.getCode());
            }
        });*/



        /*// ENVIO DE FOTOS*/
        btnUpload = findViewById(R.id.btnUpload);
        imageView = findViewById(R.id.imagePhoto);

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Configura para imagem ser salva em memoria
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();


                FirebaseStorage storage = FirebaseStorage.getInstance();

                // Create a storage reference from our app
                StorageReference storageRef = storage.getReference();

                // Create a reference to "mountains.jpg"
                StorageReference mountainsRef = storageRef.child("mountains.jpg");//nao usado

                // Create a reference to 'images/mountains.jpg'
                final StorageReference mountainImagesRef1 = storageRef.child("images/mountains.jpg");

                //uuid
                String nomeArquivo = UUID.randomUUID().toString();
                final StorageReference mountainImagesRef = storageRef.child("images/"+nomeArquivo+".jpg");


                // While the file names are the same, the references point to different files
                mountainsRef.getName().equals(mountainImagesRef.getName());    // true
                mountainsRef.getPath().equals(mountainImagesRef.getPath());    // false


                // Get the data from an ImageView as bytes
                imageView.setDrawingCacheEnabled(true);
                imageView.buildDrawingCache();

                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 25, baos);
                byte[] data = baos.toByteArray();

                UploadTask uploadTask = mountainImagesRef.putBytes(data);
                uploadTask.addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        // Handle unsuccessful uploads
                    }
                }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        Toast.makeText(MainActivity.this,
                                "Sucess nesse kraio!!",
                                Toast.LENGTH_LONG).show();

                    }
                }).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                    @Override
                    public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                        if (!task.isSuccessful()) {
                            throw task.getException();
                        }

                        // Continue with the task to get the download URL
                        return mountainImagesRef.getDownloadUrl();
                    }
                }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                    @Override
                    public void onComplete(@NonNull Task<Uri> task) {
                        if (task.isSuccessful()) {
                            Uri downloadUri = task.getResult();
                            System.out.println(downloadUri.toString());
                            Toast.makeText(MainActivity.this,
                                    "URL do para Download: "+downloadUri.toString(),
                                    Toast.LENGTH_LONG).show();

                        } else {
                            Toast.makeText(MainActivity.this,
                                    "ERRO",
                                    Toast.LENGTH_LONG).show();
                        }
                    }
                });


            }
        });



    }
}
