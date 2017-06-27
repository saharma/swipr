package ie.stepout.swipr;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

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

        // Add a marker in Sydney and move the camera
        LatLng bands = new LatLng(53.636061, -7.024407);
        LatLng ep = new LatLng(53.012847, -7.158805);
        LatLng longitude = new LatLng(53.275941, -6.263501);

        mMap.addMarker(new MarkerOptions().position(bands).title("Body and Soul"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(bands));

        mMap.addMarker(new MarkerOptions().position(ep).title("Electric Picnic"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(ep));

        mMap.addMarker(new MarkerOptions().position(longitude).title("Longitude"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(longitude));

    }
}
