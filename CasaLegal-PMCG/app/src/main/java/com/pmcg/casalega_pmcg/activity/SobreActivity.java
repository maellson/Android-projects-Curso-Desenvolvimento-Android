package com.pmcg.casalega_pmcg.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.pmcg.casalega_pmcg.R;

import mehdi.sakout.aboutpage.AboutPage;

public class SobreActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_sobre);
        String descr = "O Programa Municiapl de Regularização Fundiária visa viabilizar os "+
                "títulos de propriedade às famílias e por seguinte, uma moradia digna.\n \n"
                +"Alé de fomentar o crescimento urbano ordenado e a melhoria da infraestrutura, "
                +"o programa pretende formalizar títulos de propriedades até então irregulares e"+
                " garantir o desenvolvimento das funções sociais. \n\n"
                + "A Lei Municipal tem como base a Lei Federal 13,465, de 11 de julho de 2017, "
                +"poderão ser regularizadas as ocupações ordenadas e desordenadas, cladestinas e"
                +" irregulares como condomínios, loteamentos e incorporações ilegais situados em "
                +"núcleos urbanos informais com uso e características urbanas, mesmo que situados em zona rural"
                ;

        View sobre = new AboutPage(this)
            .isRTL(false)
            .setImage(R.drawable.logo)
            .setDescription(descr)
            .addGroup("Fale Conosco")
            .addEmail("observacampina@gmail.com","Envie um E-mail")
            .addWebsite("http://www.observacampina.com.br","Conheça o Observatório")
            .addGroup("Redes Sociais")
                .addInstagram("observacampina","Instagram")
            .create();
        setContentView(sobre);
    }
}
