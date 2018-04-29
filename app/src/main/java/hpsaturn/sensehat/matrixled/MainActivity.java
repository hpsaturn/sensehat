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
            Log.d(TAG, "start display..");
            display = SenseHat.openDisplay();
            display.draw(Color.TRANSPARENT);
        } catch (IOException e) {
            e.printStackTrace();
        }

        mHandler = new Handler();
        mHandler.post(mRandomColorRunnable);

    }

    private Runnable mRandomColorRunnable = new Runnable() {

        private static final long DELAY_MS = 1000L;

        @Override
        public void run() {
            if (display == null) {
                return;
            }
            try {
                Random rnd = new Random();
                int color = Color.argb(
                        rnd.nextInt(128), // alpha
                        rnd.nextInt(256), // R
                        rnd.nextInt(256), // G
                        rnd.nextInt(256)  // B
                );
                Log.d(TAG,"setting color "+ String.format("#%08X", color));
                display.draw(color);
                mHandler.postDelayed(this, DELAY_MS);

            } catch (IOException e) {
                Log.e(TAG, "error setting color");
                e.printStackTrace();
            }
        }
    };


    private void clearDisplay() {
        try {
            Log.d(TAG, "stop display..");
            display.draw(Color.TRANSPARENT);
        } catch (IOException e) {
            Log.e(TAG, "error setting color");
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        clearDisplay();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        try {
            Log.d(TAG, "stop display..");
            clearDisplay();
            display.close();
        } catch (IOException e) {
            Log.e(TAG, "error on display close");
            e.printStackTrace();
        }
        super.onDestroy();
    }

}
