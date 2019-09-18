package cs.dal.krush.tutorFragments;

import android.database.Cursor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.location.Address;
import android.location.Geocoder;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cs.dal.krush.R;
import cs.dal.krush.models.DBHelper;

/**
 * TutorLocationFragment allows a user to set their preferred based on a location.
 *
 */
public class TutorLocationFragment extends Fragment implements OnMapReadyCallback {

    static int USER_ID;
    private GoogleMap mMap;
    private DBHelper mydb;
    private Cursor cursor;
    private String address;
    private String locationId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tutor_location, container, false);
        USER_ID = getArguments().getInt("USER_ID");

        //initialize database connection
        mydb = new DBHelper(getContext());

        //fetch UI elements:
        TextView pageTitle = (TextView)view.findViewById(R.id.tutorLocationHeader);
        final EditText editTextAddress = (EditText)view.findViewById(R.id.editTextAddress);
        SupportMapFragment mapFrag = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.tutorMap);
        Button setTutorLocation = (Button)view.findViewById(R.id.setTutorLocation);
        mapFrag.getMapAsync(this);


        //fetch custom app font:
        final Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(),"fonts/FredokaOne-Regular.ttf");

        //set font style:
        pageTitle.setTypeface(typeFace);


        //set location based on user input
        setTutorLocation.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                // Validate to a minimum a postal code for an address
                if(editTextAddress.length() <= 6) {
                    editTextAddress.setError("Invalid address");
                } else {
                    address = editTextAddress.getText().toString();
                    setAddress(address);
                    //add location to DB
                    mydb.location.insert(address);
                    //associate locations with current tutor
                    cursor = mydb.location.getDataByLocation(address+"");
                    cursor.moveToFirst();
                    locationId = cursor.getString(cursor.getColumnIndex("id"));
                    mydb.tutor.updateTutorLocation(USER_ID, Integer.parseInt(locationId));
                    //confirm completion
                    Toast toast = Toast.makeText(getContext(), "Location saved", Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });

        return view;
    }

    /**
     * setUpMap prepares the map onCreate() this fragment
     */
    private void setUpMap() {
        // Add a marker in Halifax and move the camera
        LatLng halifax = new LatLng(-0.3921,36.9597);
        mMap.addMarker(new MarkerOptions().position(halifax)
                                          .title("Dedan Kimathi University of Techonology")
                                          .icon(getMarkerIcon("#2ecc71")));
        // Zoom in, animating the camera.
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(halifax,15));
    }

    /**
     * setAddress takes an address string and sets the map to that location
     *
     * [10]"how to Search Address by Name on Google Map Android", Stackoverflow.com, 2017. [Online].
     * Available: http://stackoverflow.com/questions/17160508/how-to-search-address-by-name-on-google-map-android.
     * [Accessed: 26- Mar- 2017].
     * @param address
     */
    private void setAddress(String address) {
        Geocoder geoCoder = new Geocoder(getActivity(), Locale.getDefault());
        try {
            List<Address> addresses = geoCoder.getFromLocationName(address, 5);
            if (addresses.size() > 0) {
                Double lat = (double) (addresses.get(0).getLatitude());
                Double lon = (double) (addresses.get(0).getLongitude());
                //Log Long and Lat
                final LatLng user = new LatLng(lat, lon);
                //used marker for show the location
                Marker location = mMap.addMarker(new MarkerOptions()
                        .position(user)
                        .title(address)
                        .icon(BitmapDescriptorFactory.fromResource(R.drawable.marker))
                        // .icon(getMarkerIcon("#2ecc71")) - google marker
                );
                // Move the camera instantly to address with a zoom of 15.
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(user, 15));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        setUpMap();
    }

    /**
     * Takes a color and returns hue format for Google Maps Marker
     * @param color
     * @return hue color
     */
    public BitmapDescriptor getMarkerIcon(String color) {
        float[] hsv = new float[3];
        Color.colorToHSV(Color.parseColor(color), hsv);
        return BitmapDescriptorFactory.defaultMarker(hsv[0]);
    }
}
