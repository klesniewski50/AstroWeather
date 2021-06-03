package com.example.pogoda;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        TextView textView = (TextView) findViewById(R.id.clockText);
        textView.setText(WeatherWrapper.getTime().toString());

        TextView latitudeView = (TextView) findViewById(R.id.latitudeView);
        latitudeView.setText("Latitude: " + WeatherWrapper.getLocation().getLatitude());

        TextView longitudeView = (TextView) findViewById(R.id.longitudeView);
        longitudeView.setText("Longitude: " + WeatherWrapper.getLocation().getLongitude());

        TextView refreshRatioView = (TextView) findViewById(R.id.refreshRatioView);
        refreshRatioView.setText("Refresh ratio (min. 5s): " + WeatherWrapper.getRefreshRatio());

        EditText refreshInput = (EditText) findViewById(R.id.refreshRatioTextInput);
        EditText latitudeInput = (EditText) findViewById(R.id.latitudeTextinput);
        EditText longitudeInput = (EditText) findViewById(R.id.longitudeTextInput);

        Button submitSettingsButton = (Button) findViewById(R.id.submitSettingsButton);
        submitSettingsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String latitudeString = latitudeInput.getText().toString();
                if(Validator.validateLatitude(latitudeString)){
                    double latitudeValue = Double.parseDouble(latitudeString);
                    //latitudeView.setText("Latitude: " + latitudeValue);
                    WeatherWrapper.setLatitude((int) latitudeValue);
                    latitudeView.setText("Latitude: " + WeatherWrapper.getLocation().getLatitude());
                }
                String longitudeString = longitudeInput.getText().toString();
                if(Validator.validateLongitude(longitudeString)){
                    double longitudeValue = Double.parseDouble(longitudeString);
                    //longitudeView.setText("Longitude: " + longitudeValue);
                    WeatherWrapper.setLongitude((int) longitudeValue);
                    longitudeView.setText("Longitude: " + WeatherWrapper.getLocation().getLongitude());
                }
                String refreshRatioString = refreshInput.getText().toString();
                if(Validator.validateRefreshRatio(refreshRatioString)){
                    double refreshRatioValue = Double.parseDouble(refreshRatioString);
                    //refreshRatioView.setText("Refresh ratio (min. 5s): " + refreshRatioValue);
                    WeatherWrapper.setRefreshRatio((int) refreshRatioValue);
                    refreshRatioView.setText("Refresh ratio (min. 5s): " + WeatherWrapper.getRefreshRatio());
                }
            }
        });

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    while (true) {
                        Thread.sleep(1000);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                WeatherWrapper.setTime();
                                textView.setText(WeatherWrapper.getTime().toString());
                            }
                        });
                    }
                } catch (InterruptedException e) {
                }
            }
        };
        thread.start();
    }
}