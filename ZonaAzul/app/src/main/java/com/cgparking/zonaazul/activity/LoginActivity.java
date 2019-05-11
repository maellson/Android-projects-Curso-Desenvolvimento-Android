package com.cgparking.zonaazul.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.cgparking.zonaazul.R;
import com.cgparking.zonaazul.control.ConfigFirebase;
import com.cgparking.zonaazul.helper.UsuarioFirebase;
import com.cgparking.zonaazul.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {
    private TextInputEditText inputEmail, inputSenha;
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //Inicializar componentes
        inputEmail = findViewById(R.id.InputEmailLogin);
        inputSenha = findViewById(R.id.InputSenhaLogin);

    }
    public void validarLoginUsuario(View view){

        //Recuperando inputs
        String textoEmail = inputEmail.getText().toString();
        String textoSenha = inputSenha.getText().toString();

        if(!textoEmail.isEmpty()){//verifica email
            if(!textoSenha.isEmpty()){//verifica Senha
                Usuario usuario = new Usuario();
                usuario.setEmail(textoEmail);
                usuario.setSenha(textoSenha);

                logarUsuario(usuario);


            }else {
                Toast.makeText(LoginActivity.this,
                        "Preencha a senha",Toast.LENGTH_SHORT).show();
            }

        }else {
            Toast.makeText(LoginActivity.this,
                    "Preencha o email",Toast.LENGTH_SHORT).show();
        }


    }

    public void logarUsuario(Usuario usuario){
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){

                    //Verifica o tipo de uaurio logado
                    //Condutor ou Fiscal
                    UsuarioFirebase.redirecionaUsuarioLogado(LoginActivity.this);




                }else {

                    String excecao = "";
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "Usuário nao cadastrado!";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Email e senha nao correspondem a um usuario cadastrado";
                    }catch(Exception e){
                        excecao = "Erro ao cadastrar usuario: " + e.getMessage();
                        e.printStackTrace();

                    }
                    Toast.makeText(LoginActivity.this,
                            excecao,Toast.LENGTH_SHORT).show();

                }
            }
        });

    }
}
