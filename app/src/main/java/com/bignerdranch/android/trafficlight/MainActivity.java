package com.bignerdranch.android.trafficlight;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private final int TIMEOUT = 3000;

    private LinearLayout green, yellow, red;
    private Button button;
    private boolean isStart = false;

    private int count = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        green = findViewById(R.id.greenLamp);
        yellow = findViewById(R.id.yellowLamp);
        red = findViewById(R.id.redLamp);
        button = findViewById(R.id.button);
    }

    public void onClickStart(View view) {
        if (isStart) {
            stopTrafficLight();
            button.setText("START");
            return;
        }
        startTrafficLight();
        button.setText("STOP");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        isStart = false;
    }

    private void startTrafficLight() {
        isStart = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                runTrafficLightEngine();
            }
        }).start();
    }

    private void stopTrafficLight() {
        isStart = false;
        count = 0;
        resetTrafficLightColors();
    }

    private void resetTrafficLightColors() {
        green.setBackgroundColor(getResources().getColor(R.color.grey));
        yellow.setBackgroundColor(getResources().getColor(R.color.grey));
        red.setBackgroundColor(getResources().getColor(R.color.grey));
    }

    private void runTrafficLightEngine() {
        while (isStart) {
            count++;
            resetTrafficLightColors();

            switch (count) {
                case 1:
                    red.setBackgroundColor(getResources().getColor(R.color.red));
                    break;
                case 2:
                    yellow.setBackgroundColor(getResources().getColor(R.color.yellow));
                    break;
                case 3:
                    green.setBackgroundColor(getResources().getColor(R.color.green));
                    count = 0;
                    break;
            }

            sleepThread(TIMEOUT);
        }
    }

    private void sleepThread(int timeoutMS) {
        try {
            Thread.sleep(timeoutMS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}