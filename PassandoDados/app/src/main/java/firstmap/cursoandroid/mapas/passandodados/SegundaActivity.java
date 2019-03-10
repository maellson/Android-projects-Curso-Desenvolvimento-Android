package firstmap.cursoandroid.mapas.passandodados;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SegundaActivity extends AppCompatActivity {
    private TextView textNome;
    private TextView textIdade;
    private TextView textLogin;
    private TextView textMail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_segunda);

        textNome = findViewById(R.id.textNome);
        textIdade = findViewById(R.id.textIdade);
        textLogin = findViewById(R.id.textLogin);
        textMail = findViewById(R.id.textMail);

        //Recuperar dados enviados

        Bundle dados = getIntent().getExtras();
        String nome = dados.getString("nome");
        int idade = dados.getInt("idade");
        Usuario usuario = (Usuario)dados.getSerializable("objeto");

        textNome.setText(nome);
        textIdade.setText(String.valueOf(idade));
        textLogin.setText(usuario.getLogin());
        textMail.setText(usuario.getMail());

    }
}
