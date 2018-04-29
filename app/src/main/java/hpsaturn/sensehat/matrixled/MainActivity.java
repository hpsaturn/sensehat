package hpsaturn.sensehat.matrixled;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.sensehat.LedMatrix;
import com.google.android.things.contrib.driver.sensehat.SenseHat;

import java.io.IOException;
import java.util.Random;

public class MainActivity extends Activity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private LedMatrix display;
    private Handler mHandler;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Color the LED matrix.
        try {
            display = SenseHat.openDisplay();
            display.draw(Color.RED);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mHandler = new Handler();
        mHandler.post(mRandomColorRunnable);

    }

    private Runnable mRandomColorRunnable = new Runnable() {

        private static final long DELAY_MS = 100L;

        @Override
        public void run() {
            if (display == null) {
                return;
            }
            try {
                Random rnd = new Random();
                int color = Color.argb(128, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                display.draw(color);
                mHandler.postDelayed(this, DELAY_MS);

            } catch (IOException e) {
                Log.e(TAG,"error setting color");
                e.printStackTrace();
            }
        }
    };


    @Override
    protected void onDestroy() {
        try {
            display.close();
        } catch (IOException e) {
            Log.e(TAG,"error on display close");
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
