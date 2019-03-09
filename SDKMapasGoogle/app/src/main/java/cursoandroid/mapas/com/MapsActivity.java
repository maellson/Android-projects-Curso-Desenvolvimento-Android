package cursoandroid.mapas.com;

import android.graphics.Color;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.maps.model.PolygonOptions;
import com.google.android.gms.maps.model.PolylineOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }


    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mMap.setMapType(1);

        // Add um marcador na prefeitura de campina and move the camera
        final LatLng prefeitura = new LatLng(-7.2192037, -35.8828192);
        //-7.2192037, -35.8828192

        /*
        //Construcao dos circulos
        CircleOptions circulo = new CircleOptions();
        circulo.center(prefeitura);
        circulo.radius(50);//definido em metros
        circulo.strokeWidth(6);//Bord do circulo
        circulo.strokeColor(Color.argb(128,255,69,0));//Cor da Borda
        circulo.fillColor(Color.argb(90,255,165,0));

        mMap.addCircle(circulo);
        */

         /*

        */

         /*
        //Adicionando um poligono
        PolygonOptions poligono = new PolygonOptions();

        poligono.add(new LatLng(-7.219126, -35.883137));//1
        poligono.add(new LatLng(-7.219012, -35.882710));//4
        poligono.add(new LatLng(-7.219337, -35.882570));//2
        poligono.add(new LatLng(-7.219403, -35.883067));//3
        poligono.strokeWidth(6);//Bord do circulo
        poligono.strokeColor(Color.argb(128,255,69,0));//Cor da Borda
        poligono.fillColor(Color.argb(90,255,165,0));



        mMap.addPolygon(poligono);
        */

        //setando no mapa um clique
       /* mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                Toast.makeText(MapsActivity.this,
                        "onClick Lat: "+latLng.latitude+ " Long: "+latLng.longitude, Toast.LENGTH_SHORT).show();

                mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title("Local")
                                .snippet("Descrição do que foi Adicionado")

                                .icon(
                                        BitmapDescriptorFactory.fromResource(R.drawable.icone_loja)
                                )

                );

            }
        }); */

       //Setando clique longo

        /*
        mMap.setOnMapLongClickListener(
                new GoogleMap.OnMapLongClickListener() {
                    @Override
                    public void onMapLongClick(LatLng latLng) {

                        Double latitude = latLng.latitude;
                        Double longitude = latLng.longitude;

                        Toast.makeText(MapsActivity.this,
                                "onLong Lat: "+latLng.latitude+ " Long: "+latLng.longitude, Toast.LENGTH_SHORT).show();

                        mMap.addMarker(
                                new MarkerOptions()
                                        .position(latLng)
                                        .title("Local")
                                        .snippet("Lat: "+latLng.latitude+ " Long: "+latLng.longitude)

                                        .icon(
                                                BitmapDescriptorFactory.fromResource(R.drawable.icone_carro_roxo_48px)
                                        )

                        );
                    }
                }

        ); */

        mMap.addMarker(
                new MarkerOptions()
                        .position(prefeitura)
                        .title("Prefeitura")
                        .icon(
                                BitmapDescriptorFactory.fromResource(R.drawable.carro)
                        )

        );
        mMap.moveCamera(//2.0 ate 21.0
                CameraUpdateFactory.newLatLngZoom(prefeitura,18)
        );


    //Adicionando linhas
        mMap.setOnMapClickListener(new GoogleMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {

                Double latitude = latLng.latitude;
                Double longitude = latLng.longitude;

                PolylineOptions polyline = new PolylineOptions();
                polyline.add(prefeitura);
                polyline.add(latLng);
                polyline.color(Color.BLUE);
                polyline.width(20);

                mMap.addPolyline(polyline);

                mMap.addMarker(
                        new MarkerOptions()
                                .position(latLng)
                                .title("Local")
                                .snippet("Descrição do que foi Adicionado")

                                .icon(
                                        BitmapDescriptorFactory.fromResource(R.drawable.icone_loja)
                                )

                );

            }
        });

    }
}
