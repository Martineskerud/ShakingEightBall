package hiof.eskerud.graphiceightball.app;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

import java.util.Random;


/**
 * Created by Martin on 19.05.2014.
 */
public class AccelerometerReader implements SensorEventListener {

    Context context;
    public SensorManager sensorManager;
    public Sensor accelerometer;
    public int destiny = 0;
    private long lastUpdate = 0;
    private float lastX, lastY, lastZ;
    private static int SHAKE_THRESHOLD = 700;

    public AccelerometerReader(Context context) {
        this.context = context;
        initAccelerometer();
    }


    private void initAccelerometer() {
        sensorManager = (SensorManager) context.getSystemService(Context.SENSOR_SERVICE);
        accelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sensorManager.registerListener(this, accelerometer, SensorManager.SENSOR_DELAY_NORMAL);
    }


    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        Sensor sensor = sensorEvent.sensor;
        if (sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = sensorEvent.values[0];
            float y = sensorEvent.values[1];
            float z = sensorEvent.values[2];

            long curTime = System.currentTimeMillis();

            if ((curTime - lastUpdate) > 150) {
                long diffTime = (curTime - lastUpdate);
                lastUpdate = curTime;

                float speed = Math.abs(x + y + z - lastX - lastY - lastZ) / diffTime * 10000;

                if (speed > SHAKE_THRESHOLD) {

                    int oldDestiny = destiny;
                    getRandomFortune(oldDestiny);
                }

                lastX = x;
                lastY = y; 
                lastZ = z;
            }
        }
    }


    private void getRandomFortune(int oldDestiny) {
        //assign the random fortune
        while (destiny == oldDestiny) {
            Random rand = new Random();
            destiny = (Math.abs(rand.nextInt()) % 11) + 1;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public int getDestiny() {
        return destiny;
    }

    public float getLastX() {
        return lastX;
    }

    public float getLastY() {
        return lastY;
    }

}
