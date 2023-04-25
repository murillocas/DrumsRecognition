package com.example.drumsrecognition;

import java.util.ArrayList;
import java.util.List;

public class SensorData {

    private List<List<String>> Accel;

    SensorData( List<List<String>> dados) {
        this.Accel = dados;
    }

    public List<Double> getXData(){

        List<Double> XData = new ArrayList<Double>();

        for (int i = 0; i < Accel.size(); i++) {
            XData.add(i, (Double)Double.valueOf(Accel.get(i).get(0)));
        }
        return XData;
    }
    public List<Double> getYData(){

        List<Double> YData = new ArrayList<Double>();

        for (int i = 0; i < Accel.size(); i++) {
            YData.add(i, (Double)Double.valueOf(Accel.get(i).get(1)));
        }
        return YData;
    }
    public List<Double> getZData(){

        List<Double> ZData = new ArrayList<Double>();

        for (int i = 0; i < Accel.size(); i++) {
            ZData.add(i, (Double)Double.valueOf(Accel.get(i).get(2)));
        }
        return ZData;
    }

    public int getDataSize() {
        return this.Accel.size();
    }
}
