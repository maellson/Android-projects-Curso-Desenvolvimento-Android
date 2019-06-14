package com.cgparking.zonaazul.activity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import com.cgparking.zonaazul.R;
import com.cgparking.zonaazul.control.ConfigFirebase;
import com.cgparking.zonaazul.model.Requisicao;
import com.cgparking.zonaazul.model.Usuario;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

public class CorridaActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private Button btnAceitarCorrida;
    //private FirebaseAuth autenticacao;
    private DatabaseReference firebaseRef;


    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng localCondutor;

    //variaveis dos dados do condutor
    private Usuario condutor;
    private String idRequisicao;

    //variavel para controle de requisicoes
    private Requisicao requisicao;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_corrida);

       inicializarComponentes();

       //Recuperar os dados do Usuario
        if(getIntent().getExtras().containsKey("idRequisicao")
                && getIntent().getExtras().containsKey("condutor")){
            Bundle extras = getIntent().getExtras();
            condutor = (Usuario) extras.getSerializable("condutor");
            idRequisicao = extras.getString("idRequisicao");
            verificaStatusRequisicao();
        }


    }

    private void verificaStatusRequisicao() {
        DatabaseReference requisicoesRef = firebaseRef.child("requisicoes")
                .child(idRequisicao);
        requisicoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Recuperar a requisicao inteira do jeito que esta no firebase
                requisicao = dataSnapshot.getValue(Requisicao.class);

                switch (requisicao.getStatus()){
                    case Requisicao.STATUS_AGUARDANDO:
                        requisicaoAguardando();
                        break;
                    case Requisicao.STATUS_A_CAMINHO:
                        requisicaoACaminho();
                        break;

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void requisicaoACaminho() {
        btnAceitarCorrida.setText("A caminho");
    }

    private void requisicaoAguardando(){
        btnAceitarCorrida.setText("Aceitar Corrida");

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //recupera localizazao do usuario
        recuperarLocalizacaoUsuario();


    }
    private void recuperarLocalizacaoUsuario() {
        locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {

                //recuperar a latitude e longitude
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                // Adiciona um marcador
                localCondutor = new LatLng(latitude, longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(localCondutor)
                        .title("Meu Local: lat: "+latitude + "long: "+longitude)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.carro))
                );
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(localCondutor, 17)
                );


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
                    0,
                    10,
                    locationListener

            );
        }
    }

    public void aceitarCorrida(View view){
        //Configurar a requisicao
        requisicao = new Requisicao();
        requisicao.setIdRequisicao(idRequisicao);
        requisicao.setCondutor(condutor);
        requisicao.setStatus(Requisicao.STATUS_A_CAMINHO);

        requisicao.atualizar();



    }

    public void inicializarComponentes(){

        //3 linhas abairo viram metodo
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        //getSupportActionBar().setTitle("Confirmar Corrida"); //mudar titulo

        btnAceitarCorrida = findViewById(R.id.buttonAceitarCorrida);
        //config de fireBase
        //criar uma referencia ao fireBase
        firebaseRef = ConfigFirebase.getFirebaseDatabase();
        //autenticacao = ConfigFirebase.getFirebaseAutenticacao();


        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



    }


}
