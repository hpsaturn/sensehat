package hpsaturn.sensehat.matrixled;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;

import com.google.android.things.contrib.driver.sensehat.LedMatrix;
import com.google.android.things.contrib.driver.sensehat.SenseHat;

import java.io.IOException;
import java.util.Random;

import static android.graphics.Paint.ANTI_ALIAS_FLAG;

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
        mHandler.post(mRandomCharacter);

    }

    private Runnable mRandomCharacter = new Runnable() {

        private static final long DELAY_MS = 100L;

        @Override
        public void run() {
            if(display==null)return;
            try {
                Random r = new Random();
                char c = (char)(r.nextInt(26) + 'a');
                display.draw(textAsBitmap(String.valueOf(c),11,Color.BLUE));
                mHandler.postDelayed(this, DELAY_MS);
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
    };

    private Runnable mRandomColorRunnable = new Runnable() {

        private static final long DELAY_MS = 1500L;

        @Override
        public void run() {
            if (display == null) {
                return;
            }
            try {
                Random rnd = new Random();
                int color = Color.argb(
                        20, // alpha
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

    public Bitmap textAsBitmap(String text, float textSize, int textColor) {

        Paint paint = new Paint();
        paint.setTextSize(textSize);
        paint.setTextScaleX(0.9f);
        paint.setColor(textColor);
        paint.setTextAlign(Paint.Align.LEFT);
        float baseline = -paint.ascent(); // ascent() is negative
        int width = (int) (paint.measureText(text) + 0.5f); // round
        int height = (int) (baseline + paint.descent() + 0.5f);
        Bitmap image = Bitmap.createBitmap(8, 8, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
        Log.d(TAG,"bitmap=>text:"+text+" baseline:"+baseline+" "+width+"x"+height);
        canvas.drawText(text, 0, baseline-2.0f, paint);
        return image;
    }


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
