package net.devwiki.viewdemo;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private Button lineViewBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lineViewBtn = (Button) findViewById(R.id.line_view_btn);
        lineViewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LineActivity.class));
//                testTimer();
            }
        });
    }

    private void testTimer() {
        CountDownTimer timer = new CountDownTimer(3000, 200) {
            @Override
            public void onTick(long millisUntilFinished) {
                Log.i(TAG, "onTick: " + millisUntilFinished);
            }

            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: ");
            }
        };
        timer.start();
    }
}
