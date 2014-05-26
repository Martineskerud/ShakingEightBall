package hiof.eskerud.graphiceightball.app;

import android.hardware.SensorManager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.app.Activity;


public class MainActivity extends Activity {


    public static final String TAG = "martineskerud";
    public AccelerometerReader accelerometerReader;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        accelerometerReader = new AccelerometerReader(this);
        CanvasTest myCanvas = (CanvasTest) findViewById(R.id.canvasTest);
        myCanvas.setAccelerometerReaderAndStartDrawThread(accelerometerReader);
    }


    @Override
    public void onResume() {
        super.onResume();
    accelerometerReader.getSensorManager().registerListener(accelerometerReader,accelerometerReader.getAccelerometer(),SensorManager.SENSOR_DELAY_NORMAL);
    }

    @Override
    public void onPause() {
        super.onPause();
        accelerometerReader.getSensorManager().unregisterListener(accelerometerReader);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return false;
    }


}



