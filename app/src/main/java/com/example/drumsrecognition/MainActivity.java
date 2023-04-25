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

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    TextView dado, dado1, dado2;
    Button btntreino,btncompara;

    SensorManager sensorManager;
    Sensor sensor;

    List<String> dados = new ArrayList<>();
    List<List<String>> lista1 = new ArrayList<List<String>>();
    List<List<String>> lista2 = new ArrayList<List<String>>();

    MDDTW mddtw;

    Boolean Treino = false, Compara = false;
    long timeStart = 0;
    Boolean dtw = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        btncompara = findViewById(R.id.btnCompara);
        btntreino = findViewById(R.id.btnTreino);

        btntreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d("OLA", "onSensorChanged: Clicou Treino");
                Treino = true;
                timeStart = System.currentTimeMillis();

                if(Treino){
                    List<String> dado = new ArrayList<String>();
                    dado.add("1.7");
                    dado.add("2.3");
                    dado.add("4.5");
                    lista2.add(dado);
                    lista2.add(dado);

                    Log.d("OLA", "onSensorChanged: Treino Start");
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
                    List<String> dado = new ArrayList<String>();
                    dado.add("1.6");
                    dado.add("2.2");
                    dado.add("4.6");
                    lista1.add(dado);
                    lista1.add(dado);


                    Log.d("OLA", "onSensorChanged: Compara Start");
                    Compara = false;
                    dtw = true;

                    if(dtw){
                        SensorData sensorData1 = new SensorData(lista1);
                        SensorData sensorData2 = new SensorData(lista2);

                        Log.d("OLA", "onSensorChanged: X, Y e Z : " + "X1: " + sensorData1.getXData() + "X2: " + sensorData2.getXData());
                        mddtw = new MDDTW(sensorData1,sensorData2);
                        Log.d("OLA", "onSensorChanged: Compara Start" + mddtw.getDistancia());
                        dtw = false;
                    }
                }
            }
        });

        if(sensor == null){
            //dado.setText("Aqui");
            finish();

        }
    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        String gravity0 = String.valueOf(sensorEvent.values[0]);
        String gravity1 = String.valueOf(sensorEvent.values[1]);
        String gravity2 = String.valueOf(sensorEvent.values[2]);
        //lista.add(dados);
        //dados.clear();

        if(Treino){
            Log.d("OLA", "onSensorChanged: Treino Start");
            if(System.currentTimeMillis() - timeStart > 1200){
                Log.d("OLA", "onSensorChanged: Treino FIM");
                Treino = false;
            }
            dados.add(gravity0);
            dados.add(gravity1);
            dados.add(gravity2);
        }

        if(Compara){
            Log.d("OLA", "onSensorChanged: Compara Start");
            if(System.currentTimeMillis() - timeStart > 1200){
                Log.d("OLA", "onSensorChanged: Compara FIM");
                Compara = false;
                dtw = true;
            }

        }

        //Log.d("OLA", "onSensorChanged: X, Y e Z : " + "X: " + gravity0 + "Y: " + gravity1 + "Z : " + gravity2);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    private void start(){
        sensorManager.registerListener(this,sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }

    private void stop(){
        sensorManager.unregisterListener(this);
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

}