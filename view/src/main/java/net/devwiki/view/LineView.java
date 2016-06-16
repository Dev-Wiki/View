package net.devwiki.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.CountDownTimer;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * 仿微信录制小视频的渐变线
 * Created by zyz on 2016/6/16.
 */

public class LineView extends View {

    /**
     * 默认时间: 10s
     */
    private static final int DEFAULT_TIME = 10;
    /**
     * 默认颜色蓝色
     */
    private static final int DEFAULT_COLOR = Color.BLUE;

    private Paint paint;
    private int lineColor = DEFAULT_COLOR;
    private int lineTime = DEFAULT_TIME;
    private CountDownTimer timer;
    private int width;
    private int countDown;

    public LineView(Context context) {
        super(context);
        init();
    }

    public LineView(Context context, AttributeSet attrs) {
        super(context, attrs);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LineView);
        lineColor = typedArray.getColor(R.styleable.LineView_lineColor, Color.BLUE);
        lineTime = typedArray.getInteger(R.styleable.LineView_lineTime, 10);
        typedArray.recycle();

        init();
    }

    private void init() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(lineColor);
        width = getMeasuredWidth();
    }

    public void start() {
        if (timer == null) {
            timer = new CountDownTimer(1000, (timer - countDown) * 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    countDown = countDown + 1;
                    invalidate();
                }

                @Override
                public void onFinish() {

                }
            };
        }
        timer.start();
    }

    public void pause() {
        timer.cancel();
        timer = null;
    }

    public void cancel() {
        timer.cancel();
        timer = null;
    }

    public void reset() {
        countDown = 0;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        float startX = countDown * getX()/(2*lineTime);
        float startY = getY();
        float stopX = getX() - startX;
        float stopY = getY();
        canvas.drawLine(startX, startY, stopX, stopY, paint);
        canvas.save();
    }
}
