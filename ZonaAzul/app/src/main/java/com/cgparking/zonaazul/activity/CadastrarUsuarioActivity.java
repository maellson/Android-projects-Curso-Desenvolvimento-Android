package com.cgparking.zonaazul.activity;

import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import com.cgparking.zonaazul.R;
import com.cgparking.zonaazul.control.ConfigFirebase;
import com.cgparking.zonaazul.model.Usuario;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CadastrarUsuarioActivity extends AppCompatActivity {

    private TextInputEditText TIeditNome;
    private TextInputEditText TIeditEmail;
    private TextInputEditText TIeditSenha;
    private Switch switchEditCadastro;

    //firebase
    private FirebaseAuth autenticacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        //inicialiaar os componentes
        TIeditNome = findViewById(R.id.TIeditCadastroNome);
        TIeditEmail = findViewById(R.id.TIeditCadastroEmail);
        TIeditSenha = findViewById(R.id.TIeditCadastroSenha);
        switchEditCadastro = findViewById(R.id.switchEditCadastro);
    }

    public void validarCadastroUsuario(View view){

        //recuperar valores dos campos
        String textoNome = TIeditNome.getText().toString();
        String textoEmail = TIeditEmail.getText().toString();
        String textoSenha = TIeditSenha.getText().toString();


        //verifica preenchimento dos campos concatenados

        if(!textoNome.isEmpty()){//verifica nome
            if(!textoEmail.isEmpty()){//verifica email
                if(!textoSenha.isEmpty()){//Verifica Senha
                    Usuario usuario = new Usuario();
                    usuario.setNome(textoNome);
                    usuario.setEmail(textoEmail);
                    usuario.setSenha(textoSenha);
                    usuario.setTipo(verificaTipoUsuario());

                    cadastrarUsuario(usuario);



                }else{
                    Toast.makeText(CadastrarUsuarioActivity.this,
                            "Preencha o Senha!",
                            Toast.LENGTH_LONG).show();
                }

            }else{
                Toast.makeText(CadastrarUsuarioActivity.this,
                        "Preencha o Email!",
                        Toast.LENGTH_LONG).show();
            }

        }else{
            Toast.makeText(CadastrarUsuarioActivity.this,
                    "Preencha o Nome!",
                    Toast.LENGTH_LONG).show();
        }


    }

    public String verificaTipoUsuario(){
        return switchEditCadastro.isChecked() ? "Fiscal" :"Condutor";

    }

    public void cadastrarUsuario(Usuario usuario){

        autenticacao = ConfigFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(
                usuario.getEmail(),
                usuario.getSenha()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    Toast.makeText(CadastrarUsuarioActivity.this,
                            "Usuário cadastrado com Sucesso",
                            Toast.LENGTH_SHORT
                            ).show();
                }
            }
        });


    }


}
