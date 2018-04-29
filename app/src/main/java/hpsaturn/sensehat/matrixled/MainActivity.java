package hpsaturn.sensehat.matrixled;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import com.google.android.things.contrib.driver.sensehat.LedMatrix;
import com.google.android.things.contrib.driver.sensehat.SenseHat;

import java.io.IOException;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Color the LED matrix.
        try {
            LedMatrix display = SenseHat.openDisplay();
            display.draw(Color.MAGENTA);
            display.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
