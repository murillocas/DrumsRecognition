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

public class MainActivity extends AppCompatActivity implements SensorEventListener {

    SensorManager sensorManager;
    SensorEventListener sensorEventListener;
    Sensor aceleSesor;
    List<Double[]> sensorDataCollected = new ArrayList<>();
    List<Double[][]> ListTreino = new ArrayList<>();
    List<Double[][]> ListCompara = new ArrayList<>();
    TextView txtresul,txtSensores;
    Button btntreino,btncompara;
    Boolean Treino = false, Compara = false;
    long timeStart = 0,timeTreinoStart = 0,timeTesteStart =0;
    Boolean dtw = false;
    MDDTW mddtw;
    String tagLog = "LogDebug";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.d(tagLog, "onCreate: Inicio");

        txtresul = findViewById(R.id.txtResultado);
        txtSensores = findViewById(R.id.txtSensores);
        btncompara = findViewById(R.id.btnCompara);
        btntreino = findViewById(R.id.btnTreino);

        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        selectSensors();



        btntreino.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(tagLog, "onclick: Clicou Treino");
                Treino = true;
                timeTreinoStart = System.currentTimeMillis();
                btntreino.setBackgroundColor(0xFFFF0000);
                /*if(Treino){
                    Double[][] oneAccelData = new Double[3][1];
                    oneAccelData[0][0] = 0.5;
                    oneAccelData[1][0] = 3.5;
                    oneAccelData[2][0] = 15.3;
                    Log.d(tagLog, "onSensorChanged: Treino Start");
                    ListTreino.add(oneAccelData);
                    Treino = false;
                }*/

            }
        });

        btncompara.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.d(tagLog, "onclick: Clicou Compara");
                Compara = true;
                timeTesteStart = System.currentTimeMillis();
                btncompara.setBackgroundColor(0xFFFF0000);

                /*if(Compara){
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
                        Log.d(tagLog, "onclick: MDDTW");
                        mddtw = new MDDTW(sensorData1,sensorData2);
                        txtresul.setText(mddtw.getDistancia()+"");
                        dtw = false;
                    }
                }*/
            }
        });




    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        int nowX = Math.round(sensorEvent.values[0]);
        int nowY = Math.round(sensorEvent.values[1]);
        int nowZ = Math.round(sensorEvent.values[2]);
        txtSensores.setText("values x " + nowX +" y "+ nowY +" z "+ nowZ);

        if(Treino){
            Log.d(tagLog, "onSensorChanged: Treino Start");
            if(System.currentTimeMillis() - timeTreinoStart > 1200){
                //ListTreino.clear();
                Treino = false;
                btntreino.setBackgroundColor(0xFFFFFFFF);
            }
            //ListTreino.add(oneAccelDat);
        }

        if(Compara){
            Log.d(tagLog, "onSensorChanged: Compara Start");
            if(System.currentTimeMillis() - timeTesteStart > 1200){
                //ListTreino.clear();
                Compara = false;
                btncompara.setBackgroundColor(0xFFFFFFFF);
                //chamarDtw();
            }
            //ListCompara.add(oneAccelDat);
        }
    }

    private void chamarMDDtw() {
        SensorData sensorData1 = new SensorData(ListTreino);
        SensorData sensorData2 = new SensorData(ListCompara);
        mddtw = new MDDTW(sensorData1,sensorData2);
        txtresul.setText(mddtw.getDistancia()+"");
    }

    /*@Override
        public void onSensorChanged(SensorEvent event) {
            Double[] oneAccelDat = new Double[3];
            oneAccelDat[0] = Double.valueOf(event.values[0]);
            oneAccelDat[1] = Double.valueOf(event.values[1]);
            oneAccelDat[2] = Double.valueOf(event.values[2]);
            oneAccelDat[0] = 2.5;
            oneAccelDat[1] = 3.5;
            oneAccelDat[2] = 7.3;

            Log.d(tagLog, "onSensorChanged: x " + event.values[0]);
            Log.d(tagLog, "onSensorChanged: y: "+event.values[1]);
            Log.d(tagLog, "onSensorChanged: z: "+event.values[2]);

            //resul.setText(Math.round(event.values[0])  + "");

            if(Treino){
                Log.d(tagLog, "onSensorChanged: Treino Start");
                if(System.currentTimeMillis() - timeStart > 1200){
                    //ListTreino.clear();
                    Log.d(tagLog, "onSensorChanged: Treino FIM");
                    Treino = false;
                }
                //ListTreino.add(oneAccelDat);


            }
            if(Compara){
                Log.d(tagLog, "onSensorChanged: Compara Start");
                if(System.currentTimeMillis() - timeStart > 1200){
                    //ListTreino.clear();
                    Log.d(tagLog, "onSensorChanged: Compara FIM");
                    Compara = false;
                    dtw = true;
                }
                //ListCompara.add(oneAccelDat);

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
    */
    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(this);
        super.onPause();
    }

    @Override
    protected void onResume() {
        sensorManager.registerListener(this,aceleSesor,SensorManager.SENSOR_DELAY_NORMAL);
        super.onResume();
    }

    public void selectSensors(){
        sensorManager = (SensorManager) getSystemService(this.SENSOR_SERVICE);
        aceleSesor = null;
        if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
            List<Sensor> aceleListSensors = sensorManager.getSensorList(Sensor.TYPE_ACCELEROMETER);
            for(int i=0; i< aceleListSensors.size(); i++) {
                if ((aceleListSensors.get(i).getVendor().contains("Google LLC")) &&
                        (aceleListSensors.get(i).getVersion() == 3)){
                    // Use the version 3 gravity sensor.
                    aceleSesor = aceleListSensors.get(i);
                }
            }
            if (aceleSesor == null){
                aceleSesor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                Log.d(tagLog, "selectSensors: Sensor " + aceleListSensors);
               /*
               selecionar outo sensor
               if (sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER) != null){
                    mSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
                } else{
                    // Sorry, there are no accelerometers on your device.
                    // You can't play this game.
                }*/
            }else{
                Log.d(tagLog, "selectSensors: Sensor nao encontrado");
            }
        }
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