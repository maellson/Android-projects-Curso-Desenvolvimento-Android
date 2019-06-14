package com.cgparking.zonaazul.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.TextView;

import com.cgparking.zonaazul.R;
import com.cgparking.zonaazul.adapter.RequisicoesAdapter;
import com.cgparking.zonaazul.control.ConfigFirebase;
import com.cgparking.zonaazul.helper.RecyclerItemClickListener;
import com.cgparking.zonaazul.helper.UsuarioFirebase;
import com.cgparking.zonaazul.model.Requisicao;
import com.cgparking.zonaazul.model.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RequisicoesActivity extends AppCompatActivity {

    //componentes
    private RecyclerView recyclerRequisicoes;
    private TextView textResultado;

    //variaveis
    private FirebaseAuth autenticacao;
    private DatabaseReference firebaseRef;


    private List<Requisicao>listaReq = new ArrayList<>();
    private RequisicoesAdapter adapter;
    private Usuario condutor;

    //veio do FiscalActivity
    private LocationManager locationManager;
    private LocationListener locationListener;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_requisicoes);


        //iniciando chamadas
        inicializarComponentes();

        //recuperar Localizacao do Usuario
        recuperarLocalizacaoUsuario();

    }

    private void recuperarLocalizacaoUsuario()
    {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //recuperar a latitude e longitude
                String latitude = String.valueOf(location.getLatitude());
                String longitude = String.valueOf(location.getLongitude());

                if(!latitude.isEmpty() && !longitude.isEmpty()){
                    condutor.setLatitude(latitude);
                    condutor.setLongitude(longitude);
                    //desabilitado o processo de receber a atualizacao da localizacao do usuario,
                    // pq so preciso saber a primeira localiacao
                    //ja temos a Lat e Long
                    locationManager.removeUpdates(locationListener);
                    adapter.notifyDataSetChanged();


                }


            }

            @Override
            public void onStatusChanged(String provider, int status, Bundle extras) {

            }

            @Override
            public void onProviderEnabled(String provider) {

            }

            @Override
            public void onProviderDisabled(String provider) {

            }
        };//fim NewlocationListner()

        //Solicitar atualizacoes de localizacao
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED ) {
            locationManager.requestLocationUpdates(
                    LocationManager.GPS_PROVIDER,
                    0,//pra receber apenas a primeira localizacao.
                    0,
                    locationListener

            );
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.navigation,menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_sair:
                autenticacao.signOut();
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void inicializarComponentes(){
        getSupportActionBar().setTitle("Requisições");


        //configurar componentes
        recyclerRequisicoes = findViewById(R.id.recyclerRequisicoes);
        textResultado = findViewById(R.id.textResultado);

        //config iniciais
        condutor = UsuarioFirebase.getDadosUsuarioLogado();

        //criar uma referencia ao fireBase
        firebaseRef = ConfigFirebase.getFirebaseDatabase();
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        //Configurar o Recycler View
        adapter = new RequisicoesAdapter(listaReq,getApplicationContext(),condutor );
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerRequisicoes.setLayoutManager(layoutManager);
        recyclerRequisicoes.setHasFixedSize(true);
        recyclerRequisicoes.setAdapter(adapter);

        //Adiciona evento de clique no recyclerView
        recyclerRequisicoes.addOnItemTouchListener(
                new RecyclerItemClickListener(
                        getApplicationContext(),
                        recyclerRequisicoes,
                        new RecyclerItemClickListener.OnItemClickListener() {
                            @Override
                            public void onItemClick(View view, int position) {
                                Requisicao requisicao = listaReq.get(position);
                                Intent intent = new Intent(RequisicoesActivity.this,CorridaActivity.class);
                                intent.putExtra("idRequisicao", requisicao.getIdRequisicao());
                                intent.putExtra("condutor",condutor);
                                startActivity(intent);
                            }

                            @Override
                            public void onLongItemClick(View view, int position) {

                            }

                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                            }
                        }
                )
        );

        recuperarRequisicoes();

    }

    private void recuperarRequisicoes() {
        DatabaseReference requisicoes = firebaseRef.child("requisicoes");
        Query requisicaoPesquisa = requisicoes.orderByChild("status")
                .equalTo(Requisicao.STATUS_AGUARDANDO);

        requisicaoPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.getChildrenCount()>0){
                    textResultado.setVisibility(View.GONE);
                    recyclerRequisicoes.setVisibility(View.VISIBLE);
                }else{
                    textResultado.setVisibility(View.VISIBLE);
                    recyclerRequisicoes.setVisibility(View.GONE);
                }

                listaReq.clear();
                for(DataSnapshot ds: dataSnapshot.getChildren()){
                    Requisicao requisicao = ds.getValue(Requisicao.class);
                    listaReq.add(requisicao);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

}
