package com.example.drumsrecognition;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    SensorManager sensorManager;
    SensorEventListener sensorEventListener;
    Sensor sensor;
    List<Double[]> sensorDataCollected = new ArrayList<>();
    List<Double[][]> ListTreino = new ArrayList<>();
    List<Double[][]> ListCompara = new ArrayList<>();
    TextView resul;
    Button btntreino,btncompara;
    Boolean Treino = false, Compara = false;
    long timeStart = 0;
    Boolean dtw = false;
    MDDTW mddtw;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d("OLA", "onSensorChanged: BBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBB");
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        resul = findViewById(R.id.txtResultado);
        btncompara = findViewById(R.id.btnCompara);
        btntreino = findViewById(R.id.btnTreino);

        btntreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OLA", "onSensorChanged: Clicou Treino");
                Treino = true;
                timeStart = System.currentTimeMillis();

                if(Treino){
                    Double[][] oneAccelData = new Double[3][1];

                    oneAccelData[0][0] = 0.5;
                    oneAccelData[1][0] = 3.5;
                    oneAccelData[2][0] = 15.3;

                    Log.d("OLA", "onSensorChanged: Treino Start");

                    ListTreino.add(oneAccelData);
                    Treino = false;
                }

            }
        });

        btncompara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OLA", "onSensorChanged: Clicou Compara");
                Compara = true;
                timeStart = System.currentTimeMillis();

                if(Compara){
                    Double[][] oneAccelData = new Double[3][1];

                    oneAccelData[0][0] = 2.5;
                    oneAccelData[1][0] = 3.5;
                    oneAccelData[2][0] = 7.3;

                    Log.d("OLA", "onSensorChanged: Compara Start");

                    ListCompara.add(oneAccelData);
                    Compara = false;
                    dtw = true;

                    if(dtw){
                        SensorData sensorData1 = new SensorData(ListTreino);
                        SensorData sensorData2 = new SensorData(ListCompara);

                        mddtw = new MDDTW(sensorData1,sensorData2);
                        resul.setText(mddtw.getDistancia()+"");
                        dtw = false;
                    }
                }
            }
        });




        if(sensor == null){
            Log.d("OLA", "onSensorChanged: TTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTTT");
            finish();
       }
        start();
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        Double[] oneAccelDat = new Double[3];
        oneAccelDat[0] = Double.valueOf(event.values[0]);
        oneAccelDat[1] = Double.valueOf(event.values[1]);
        oneAccelDat[2] = Double.valueOf(event.values[2]);
        oneAccelDat[0] = 2.5;
        oneAccelDat[1] = 3.5;
        oneAccelDat[2] = 7.3;

        if(Treino){
            Log.d("OLA", "onSensorChanged: Treino Start");
            if(System.currentTimeMillis() - timeStart > 1200){
                //ListTreino.clear();
                Log.d("OLA", "onSensorChanged: Treino FIM");
                Treino = false;
            }
            ListTreino.add(oneAccelDat);


        }
        if(Compara){
            Log.d("OLA", "onSensorChanged: Compara Start");
            if(System.currentTimeMillis() - timeStart > 1200){
                //ListTreino.clear();
                Log.d("OLA", "onSensorChanged: Compara FIM");
                Compara = false;
                dtw = true;
            }
            ListCompara.add(oneAccelDat);

            if(dtw){
               SensorData sensorData1 = new SensorData(ListTreino);
               SensorData sensorData2 = new SensorData(ListCompara);

               mddtw = new MDDTW(sensorData1,sensorData2);
               resul.setText(mddtw.getDistancia()+"");
                dtw = false;
            }
        }

        sensorDataCollected.add(oneAccelDat);

    }

   @Override
   public void onAccuracyChanged(Sensor sensor, int i) {

    }


   private void start(){
        sensorManager.registerListener(sensorEventListener,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){

        sensorManager.unregisterListener(sensorEventListener);
    }


    public MainActivity() {

        super();
    }

    @Override
    protected void onPause() {
        stop();
        super.onPause();
    }

    @Override
    protected void onResume() {
        start();
        super.onResume();
    }
    /*
        if(Compara){
                    Double[] oneAccelData = new Double[3];

                    oneAccelData[0] = 2.5;
                    oneAccelData[1] = 3.5;
                    oneAccelData[2] = 7.3;

                    Log.d("OLA", "onSensorChanged: Compara Start");
                    if(System.currentTimeMillis() - timeStart > 1200){
                        //ListTreino.clear();
                        Log.d("OLA", "onSensorChanged: Compara FIM");
                        Compara = false;
                        dtw = true;
                    }
                    ListCompara.add(oneAccelData);
                    Compara = false;
                    dtw = true;

                    if(dtw){
                        SensorData sensorData1 = new SensorData(ListTreino);
                        SensorData sensorData2 = new SensorData(ListCompara);

                        mddtw = new MDDTW(sensorData1,sensorData2);
                        resul.setText(mddtw.getDistancia()+"");
                        dtw = false;
                    }
                }


                if(Treino){
                    Double[] oneAccelData = new Double[3];

                    oneAccelData[0] = 2.5;
                    oneAccelData[1] = 3.5;
                    oneAccelData[2] = 7.3;

                    Log.d("OLA", "onSensorChanged: Treino Start");
                    if(System.currentTimeMillis() - timeStart > 1200){
                        //ListTreino.clear();
                        Log.d("OLA", "onSensorChanged: Treino FIM");
                        Treino = false;
                    }
                    ListTreino.add(oneAccelData);
                    Treino = false;
                }
          */
}