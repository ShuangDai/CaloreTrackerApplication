package com.example.a5046assign2;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.location.Address;
import android.location.Geocoder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.google.android.gms.maps.model.LatLng;
import java.util.List;

public class LocationActivity extends Fragment implements View.OnClickListener{
    View vDisplayCalorieTracker;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        // Set Variables and listeners
        vDisplayCalorieTracker = inflater.inflate(R.layout.activity_location, container,
                false);
        Button btnSearch = (Button) vDisplayCalorieTracker.findViewById(R.id.btnSearchAddress);
        EditText editText=(EditText) vDisplayCalorieTracker.findViewById(R.id.editTextAddress) ;

        btnSearch.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                try {
                    String address = editText.getText().toString();
                    LatLng loc = getLocationFromAddress(getActivity().getBaseContext(),address);
                    Intent intent = new Intent(getActivity(),MapsActivity.class);
                    intent.putExtra("location", loc.toString());
                    startActivity(intent);
                }
                catch (Exception e){
                    Toast toast = Toast.makeText(getActivity().getApplicationContext(),
                            "Cannot  find the location, please input valid address",
                            Toast.LENGTH_SHORT);
                    toast.show();
                }
            }
        });
        return vDisplayCalorieTracker;
    }

    @Override
    public void onClick(View v) {
    }

    public LatLng getLocationFromAddress(Context context, String strAddress) {
        Geocoder coder = new Geocoder(context);
        List<Address> address;
        LatLng p1 = null;
        try {
            address = coder.getFromLocationName(strAddress, 5);
            if (address == null) {
                return null;
            }
            Address location = address.get(0);
            location.getLatitude();
            location.getLongitude();
            p1 = new LatLng(location.getLatitude(), location.getLongitude() );
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return p1;
    }
}
