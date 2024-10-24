package com.example.gestionnutricionfr;
import  android.os.Bundle;
import  android.preference.PreferenceManager;
import  android.view.View;
import  android.widget.AdapterView;
import  android.widget.ArrayAdapter;
import  android.widget.Spinner;
import  org.osmdroid.config.Configuration;
import  org.osmdroid.tileprovider.tilesource.TileSourceFactory;
import  org.osmdroid.tileprovider.tilesource.XYTileSource;
import  org.osmdroid.util.GeoPoint;
import  org.osmdroid.views.MapView;
import  org.osmdroid.views.overlay.Marker;
import  org.osmdroid.views.overlay.Polyline;

import androidx.appcompat.app.AppCompatActivity;

public class MapaActivity extends AppCompatActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa);
        //Carca la configuracion del mapa usando las preferencias predeterminadas.
        Configuration.getInstance().load(getApplicationContext(),PreferenceManager.getDefaultSharedPreferences(getApplicationContext()));
        //obtiene la referencia al componente de MApView del layout.
        MapView mapView = findViewById(R.id.mapView);
        mapView.setTileSource(TileSourceFactory.MAPNIK);
        mapView.setBuiltInZoomControls((true));
        mapView.setMultiTouchControls(true);

        double ipSantoTomasLatitud = -33.4493141;
        double ipSantoTomasLongitud = -70.6635069;

        GeoPoint IpsantoTomasPoint = new GeoPoint(ipSantoTomasLatitud, ipSantoTomasLongitud);
        mapView.getController().setZoom(15.0);
        mapView.getController().setCenter(IpsantoTomasPoint);

        Marker marcadorSantoTomas = new Marker(mapView);
        marcadorSantoTomas.setPosition(IpsantoTomasPoint);
        marcadorSantoTomas.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM);
        marcadorSantoTomas.setTitle("Instituto Santotomas");
        marcadorSantoTomas.setSnippet("Educacion Tomasina");
        mapView.getOverlays().add(marcadorSantoTomas);

        double consultaLatitud = -33.4625076;
        double consultaLongitud = -70.6600286;
        GeoPoint consultaPoint = new GeoPoint(consultaLatitud,consultaLongitud);
        Marker marcadorConsulta = new Marker(mapView);
        marcadorConsulta.setPosition(consultaPoint);
        marcadorConsulta.setAnchor(0.2f, 0.4f);
        marcadorConsulta.setInfoWindowAnchor(0.2f, 0.0f);
        marcadorConsulta.setTitle("Consulta NutricionRYF");
        marcadorConsulta.setSnippet("Nutricion para una mejor salud");
        marcadorConsulta.setIcon(getResources().getDrawable(R.drawable.aguacate));
        mapView.getOverlays().add(marcadorConsulta);

        Polyline linea = new Polyline();
        linea.addPoint(IpsantoTomasPoint);
        linea.addPoint(consultaPoint);
        linea.setColor(0xFF0000FF);
        linea.setWidth(5);
        mapView.getOverlayManager().add(linea);

        Spinner mapTypeSpinner = findViewById(R.id.mapTypeSpinner);
        String[] mapTypes = {"Mapa Normal", "Mapa de Transporte", "Mapa Topografico"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, mapTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mapTypeSpinner.setAdapter(adapter);

        mapTypeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                switch (position){
                    case 0:
                        mapView.setTileSource(TileSourceFactory.MAPNIK);
                        break;

                    case 1:
                        mapView.setTileSource(new XYTileSource(
                                "PublicTransport",0,18,256,".png",new String[]{
                                        "https://tile.memomaps.de/tilegen/"
                        }
                        ));
                        break;
                    case 2:
                        mapView.setTileSource(new XYTileSource(
                                "USGS_Satellite", 0, 18, 256, ".png", new String[]{
                                        "https://a.tile.opentopomap.org/","https://b.tile.opentopomap.org/","https://c.tile.opentopomap.org/"


                        }
                        ));
                        break;

                }

            }
            @Override
            public void  onNothingSelected(AdapterView<?> parent){

            }
        });


    }
}
