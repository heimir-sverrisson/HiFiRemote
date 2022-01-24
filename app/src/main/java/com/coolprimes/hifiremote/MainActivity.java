package com.coolprimes.hifiremote;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private static final String TAG="MainActivity";

    private MessageTransmitter messageTransmitter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        messageTransmitter = new MessageTransmitter(this);
        // Call the http post class
        View.OnClickListener buttonListener = v -> {
            Log.d(TAG, "got: " + v.getId());
            messageTransmitter.add(v.getId());
        };
        Button btn_on = findViewById(R.id.button_on);
        btn_on.setOnClickListener(buttonListener);
        Button btn_off = findViewById(R.id.button_off);
        btn_off.setOnClickListener(buttonListener);
        Button btn_source_up = findViewById(R.id.button_source_up);
        btn_source_up.setOnClickListener(buttonListener);
        Button btn_source_down = findViewById(R.id.button_source_down);
        btn_source_down.setOnClickListener(buttonListener);
        Button btn_volume_up = findViewById(R.id.button_volume_up);
        btn_volume_up.setOnClickListener(buttonListener);
        Button btn_volume_down = findViewById(R.id.button_volume_down);
        btn_volume_down.setOnClickListener(buttonListener);
        Button btn_previous_track = findViewById(R.id.button_previous_track);
        btn_previous_track.setOnClickListener(buttonListener);
        Button btn_next_track = findViewById(R.id.button_next_track);
        btn_next_track.setOnClickListener(buttonListener);
        Button btn_play_pause = findViewById(R.id.button_play_pause);
        btn_play_pause.setOnClickListener(buttonListener);
        Button btn_stop = findViewById(R.id.button_stop);
        btn_stop.setOnClickListener(buttonListener);
    }

    protected void onDestroy(){
        super.onDestroy();
        messageTransmitter.shutdown();
    }

}