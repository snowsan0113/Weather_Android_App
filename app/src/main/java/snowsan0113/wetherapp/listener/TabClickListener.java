package snowsan0113.wetherapp.listener;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Looper;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.RequiresPermission;
import androidx.core.app.ActivityCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationCallback;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationResult;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResponse;
import com.google.android.gms.location.Priority;
import com.google.android.gms.location.SettingsClient;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import snowsan0113.wetherapp.R;

public class TabClickListener implements View.OnClickListener {

    private Activity activity;
    private FusedLocationProviderClient fusedLocationClient;

    public TabClickListener(Activity activity) {
        this.activity = activity;
        this.fusedLocationClient = LocationServices.getFusedLocationProviderClient(activity);
    }

    @Override
    public void onClick(View view) {
        TabLayout tab = activity.findViewById(R.id.tabLayout);
        TabLayout.Tab current_location_tab = tab.getTabAt(0);

        Log.d("snowsan_weatherapp", view.getId() + "," + R.id.current_location_tab);
        if (view.getId() == current_location_tab.getId()) {
            String tag = (String) current_location_tab.getTag();
            if (tag == null || tag.equalsIgnoreCase("no_location")) {
                Log.d("snowsan_weatherapp", "位置情報を取得中");
                current_location_tab.setText("現在地\n取得中");

                LocationRequest locationRequest = new LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 0)
                        .setMaxUpdates(1)
                        .build();

                if (ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                        && ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    ActivityCompat.requestPermissions(activity,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                            1000);
                    //権限をリクエスト
                }

                fusedLocationClient.requestLocationUpdates(locationRequest,  new LocationCallback() {
                    @Override
                    public void onLocationResult(LocationResult locationResult) {
                        for (Location location : locationResult.getLocations()) {
                            // Update UI with location data
                            // ...
                            Log.d("snowsan_weatherapp", String.valueOf(locationResult.getLocations().size()) + location.hasAccuracy());


                            Log.d("snowsan_weatherapp", location.getProvider());

                            Geocoder geocoder = new Geocoder(activity, Locale.getDefault());
                            try {
                                List<Address> addresses = geocoder.getFromLocation(
                                        location.getLatitude(),
                                        location.getLongitude(),
                                        1
                                );
                                current_location_tab.setText(addresses.get(0).getAddressLine(0));
                            } catch (IOException e) {
                                AlertDialog builder = new AlertDialog.Builder(activity)
                                        .setMessage("位置情報を取得できませんでした")
                                        .setPositiveButton("再取得", (dialogInterface, i) -> {

                                        })
                                        .setNegativeButton("閉じる", (dialogInterface, i) -> {

                                        }).create();
                                builder.show();

                                Log.d("snowsan_weatherapp", "失敗");
                                current_location_tab.setText("失敗");
                                break;
                            }

                        }
                    }
                }, Looper.getMainLooper());
            }
        }
    }

}
