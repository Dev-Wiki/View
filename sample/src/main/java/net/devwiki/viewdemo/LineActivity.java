package net.devwiki.viewdemo;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import net.devwiki.view.LineView;

public class LineActivity extends AppCompatActivity {

    private static final String TAG = "LineActivity";

    private Button startBtn;
    private Button cancelBtn;
    private LineView lineView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_line);

        lineView = (LineView) findViewById(R.id.line_view);
        lineView.setOnFinishListener(new LineView.OnFinishListener() {
            @Override
            public void onFinish() {
                Log.i(TAG, "onFinish: 绘制完成");
            }
        });
        lineView.setLineTime(5);
        lineView.setLineColor(Color.RED);
        startBtn = (Button) findViewById(R.id.start_btn);
        startBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i(TAG, "onClick: 开始绘制");
                lineView.start();
            }
        });
        cancelBtn = (Button) findViewById(R.id.cancel_btn);
        cancelBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lineView.reset();
            }
        });
    }

}
