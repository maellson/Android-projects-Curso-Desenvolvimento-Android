package com.cgparking.zonaazul.activity;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.cgparking.zonaazul.R;
import com.cgparking.zonaazul.control.ConfigFirebase;
import com.cgparking.zonaazul.helper.UsuarioFirebase;
import com.cgparking.zonaazul.model.Destino;
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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;


public class FiscalActivity extends AppCompatActivity
        implements OnMapReadyCallback {

    //componentes
    private EditText editDestino;
    private LinearLayout linearLayoutDestinoFiscal;
    private Button buttonChamarUber;

    private GoogleMap mMap;
    private FirebaseAuth autenticacao;
    private LocationManager locationManager;
    private LocationListener locationListener;
    private LatLng localFiscal;

    private boolean uberChamado = false;
    private DatabaseReference firebaseRef;
    private Requisicao requisicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiscal);

        inicializarComponentes();

       //adicionar um listner para o status da requisicao
        verificarStatusRequisicao();


    }

/*melhorar o codigo para fazer busca no firebase na tabela requisicao_atual*/

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        //recupera localizazao do usuario
        recuperarLocalizacaoUsuario();


    }

    public void chamarUber(View view){

        if(!uberChamado){//Uber nao foi chamado


            //inicio
            String enderecoDestino = editDestino.getText().toString();
            if(!enderecoDestino.equals("")|| enderecoDestino!= null ){
                Address addressDestino = recuperarEndereco(enderecoDestino);
                if(addressDestino != null){

                    final Destino destino = new Destino();
                    destino.setCidade(addressDestino.getSubAdminArea());
                    destino.setCep(addressDestino.getPostalCode());
                    destino.setBairro(addressDestino.getSubLocality());
                    destino.setRua(addressDestino.getThoroughfare());
                    destino.setNumero(addressDestino.getFeatureName());
                    destino.setLatitude(String.valueOf(addressDestino.getLatitude()) );
                    destino.setLongitude(String.valueOf(addressDestino.getLongitude()) );

                    StringBuilder mensagem =  new StringBuilder();
                    mensagem.append("Cidade: "+destino.getCidade());
                    mensagem.append("\nRua: "+destino.getRua());
                    mensagem.append("\nBairro: "+destino.getBairro());
                    mensagem.append("\nNúmero: "+destino.getNumero());
                    mensagem.append("\nCEP: "+destino.getCep());

                    AlertDialog.Builder builder = new AlertDialog.Builder(this)
                            .setTitle("Confirme endereco")
                            .setMessage(mensagem)
                            .setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    //salvar a requisicao
                                    salvarRequisicao(destino);
                                    uberChamado = true;
                                }
                            }).setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                    AlertDialog dialog = builder.create();
                    dialog.show();

                }

            }else{
                Toast.makeText(this,
                        "Informe o endereço",
                        Toast.LENGTH_SHORT).show();
            }
            //Fim bloco de codigo validar endereco

        }else{
            //cancelar requisicao

            uberChamado = false;


        }



    }

    private void salvarRequisicao(Destino destino) {

        Requisicao requisicao = new Requisicao();
        requisicao.setDestino(destino);

        Usuario usuarioFiscal = UsuarioFirebase.getDadosUsuarioLogado();
        usuarioFiscal.setLatitude(destino.getLatitude());
        usuarioFiscal.setLongitude(String.valueOf(localFiscal.latitude));
        usuarioFiscal.setLongitude(String.valueOf(localFiscal.longitude));
        requisicao.setFiscal(usuarioFiscal);
        requisicao.setStatus(Requisicao.STATUS_AGUARDANDO);

        requisicao.salvar();


        linearLayoutDestinoFiscal.setVisibility(View.GONE);
        buttonChamarUber.setText("Cancelar Uber");


    }

    private Address recuperarEndereco(String endereco){
        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        try {
            List<Address> listaEnderecos = geocoder.getFromLocationName(endereco,1);
            if(listaEnderecos != null && listaEnderecos.size()>0){
                Address address = listaEnderecos.get(0);
                return address;

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

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
                localFiscal = new LatLng(latitude, longitude);
                mMap.clear();
                mMap.addMarker(new MarkerOptions()
                        .position(localFiscal)
                        .title("Meu Local: lat: "+latitude + "long: "+longitude)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.usuario))
                );
                mMap.moveCamera(
                        CameraUpdateFactory.newLatLngZoom(localFiscal, 17)
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
                    10000,
                    10,
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
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Iniciar Viagem");
        setSupportActionBar(toolbar);

        //Inicializar Componentes
        editDestino = findViewById(R.id.editDestino);
        linearLayoutDestinoFiscal = findViewById(R.id.linearDestinoFiscal);
        buttonChamarUber = findViewById(R.id.buttonChamarUber);

        //config iniciais
        //criar uma referencia ao fireBase
        firebaseRef = ConfigFirebase.getFirebaseDatabase();
        autenticacao = ConfigFirebase.getFirebaseAutenticacao();

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment)getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    private void verificarStatusRequisicao() {
        Usuario usuarioLogado = UsuarioFirebase.getDadosUsuarioLogado();
        DatabaseReference requisicoes = firebaseRef.child("requisicoes");
        Query requisicaoPesquisa = requisicoes.orderByChild( "fiscal/id")
                .equalTo(usuarioLogado.getId());

        requisicaoPesquisa.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                //Log.d("resultado","onDataChange: " + dataSnapshot.toString());
                List<Requisicao>listaReq = new ArrayList<>();
                for (DataSnapshot ds : dataSnapshot.getChildren()){
                    listaReq.add(ds.getValue(Requisicao.class));
                }
                Collections.reverse(listaReq);
                if(listaReq!=null && listaReq.size()>0){

                    requisicao = listaReq.get(0);
                    //Log.d("resultado","onDataChange: " + requisicao.getIdRequisicao());
                    switch (requisicao.getStatus()){
                        case Requisicao.STATUS_AGUARDANDO:
                            linearLayoutDestinoFiscal.setVisibility(View.GONE);
                            buttonChamarUber.setText("Cancelar Uber");
                            uberChamado = true;
                            break;

                    }
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}
