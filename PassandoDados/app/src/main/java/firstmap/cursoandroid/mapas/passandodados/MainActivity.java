package firstmap.cursoandroid.mapas.passandodados;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.io.Serializable;

public class MainActivity extends AppCompatActivity {

    private Button buttonEnviar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        buttonEnviar = findViewById(R.id.btnEnviar);
        buttonEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SegundaActivity.class);
                //instaciando usuario
                Usuario user = new Usuario("maellson", "maellson@hotmail.com");



                //Passar Dados
                intent.putExtra("nome","Maelson");
                intent.putExtra("idade",30);
                intent.putExtra("objeto", user);


                startActivity(intent);


            }
        });
    }
}
