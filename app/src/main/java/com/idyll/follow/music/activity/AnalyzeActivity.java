package com.idyll.follow.music.activity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.idyll.follow.R;
import com.idyll.follow.common.libs.ftt.RealDoubleFFT;

public class AnalyzeActivity extends Activity implements View.OnClickListener {
    private boolean STARTED = false;
    private int FREQUENCY = 8000;
    private int CHANNEL_CONFIGURATION = AudioFormat.CHANNEL_CONFIGURATION_MONO;
    private int AUDIO_ENCODING = AudioFormat.ENCODING_PCM_16BIT;
    private int BLOCK_SIZE = 256;

    private Button button;
    private ImageView imageView;
    private Bitmap bitmap;
    private Canvas canvas;
    private Paint paint;

    private RealDoubleFFT realDoubleFFT;
    private RecordAudio recordTask;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_analyze);

        button = (Button)findViewById(R.id.btn);
        button.setOnClickListener(this);

        realDoubleFFT = new RealDoubleFFT(BLOCK_SIZE);

        imageView = (ImageView)findViewById(R.id.imgView);
        bitmap = Bitmap.createBitmap((int)256, (int)100, Bitmap.Config.ARGB_8888);
        canvas = new Canvas(bitmap);
        paint = new Paint();
        paint.setColor(Color.GREEN);
        imageView.setImageBitmap(bitmap);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_analyze, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }



    @Override
    public void onClick(View view) {
        if (STARTED) {
            STARTED = false;
            button.setText("Start");
            recordTask.cancel(true);
        } else {
            STARTED = true;
            button.setText("Stop");
            recordTask = new RecordAudio();
            recordTask.execute();
        }
    }


    private class RecordAudio extends AsyncTask<Void, double[], Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            try {
                int bufferSize = AudioRecord.getMinBufferSize(FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING);

                AudioRecord audioRecord = new AudioRecord(MediaRecorder.AudioSource.MIC, FREQUENCY, CHANNEL_CONFIGURATION, AUDIO_ENCODING, bufferSize);

                short[] buffer = new short[BLOCK_SIZE];
                double[] toTransform = new double[BLOCK_SIZE];

                audioRecord.startRecording();

                while(STARTED) {
                    int bufferReadResult = audioRecord.read(buffer, 0, BLOCK_SIZE);
                    for (int i = 0; i < BLOCK_SIZE && i < bufferReadResult; i++) {
                        toTransform[i] = (double)buffer[i] / Short.MAX_VALUE;
                    }

                    Log.d("log", "running...");
                }

                audioRecord.stop();
            } catch(Throwable t) {
                Log.e("AudioRecord", "Recording Failed");
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(double[]... toTransform) {
            canvas.drawColor(Color.BLACK);

            for (int i = 0, len = toTransform[0].length; i < len; i++) {
                int x = i;
                int downy = (int)(100 - (toTransform[0][i] * 10));
                int upy = 100;

                canvas.drawLine(x, downy, x, upy, paint);
            }

            imageView.invalidate();
        }
    }
}
