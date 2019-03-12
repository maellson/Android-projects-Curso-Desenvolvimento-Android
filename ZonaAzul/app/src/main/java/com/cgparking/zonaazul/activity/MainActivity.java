package com.cgparking.zonaazul.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.cgparking.zonaazul.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();




    }
    public void abriTelaLogin(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void abriTelaCadastro(View view){
        startActivity(new Intent(this, CadastrarUsuarioActivity.class));
    }

}
