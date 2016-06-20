package net.devwiki.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.os.CountDownTimer;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.View;

/**
 * 仿微信录制小视频的渐变线
 * Created by zyz on 2016/6/16.
 */

public class LineView extends View {

    private static final String TAG = "LineView";
    /**
     * 默认时间: 10s
     */
    private static final int DEFAULT_TIME = 10;
    /**
     * 动画绘制间隔:10ms
     */
    private static final int TIME_PAGE = 10;
    /**
     * 默认颜色蓝色
     */
    private static final int DEFAULT_COLOR = Color.BLUE;

    private Paint paint;
    private int lineColor = DEFAULT_COLOR;
    private int lineTime = DEFAULT_TIME;
    private CountDownTimer timer;
    private Handler handler;
    private OnFinishListener finishListener;
    //当前绘制次数
    private int index;
    //总的绘制次数
    private int totalCount;

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
        totalCount = (lineTime * 1000) / TIME_PAGE;
        super.setBackgroundColor(Color.parseColor("#00000000"));
        handler = new Handler();
    }

    /**
     * 设置线的颜色
     * @param lineColor 要设置的颜色
     */
    public void setLineColor(int lineColor) {
        this.lineColor = lineColor;
        paint.setColor(lineColor);
    }

    /**
     * 设置时间,单位:秒
     * @param lineTime 时长
     */
    public void setLineTime(int lineTime) {
        this.lineTime = lineTime;
        totalCount = (lineTime * 1000) / TIME_PAGE;
    }

    /**
     * 设置完成监听器
     * @param listener 监听器
     */
    public void setOnFinishListener(OnFinishListener listener) {
        this.finishListener = listener;
    }

    /**
     * 设置背景色无效,如要设置线的颜色请调用{@link #setLineColor(int)}
     * @param background 要设置的背景
     */
    @Override
    public void setBackground(Drawable background) {
    }

    /**
     * 设置背景色无效,如要设置线的颜色请调用{@link #setLineColor(int)}
     * @param color 要设置的颜色
     */
    @Override
    public void setBackgroundColor(int color) {
    }

    public void start() {
        if (timer == null) {
            timer = new CountDownTimer(TIME_PAGE * (totalCount - index - 1), TIME_PAGE) {
                @Override
                public void onTick(long millisUntilFinished) {
                    index++;
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            invalidate();
                        }
                    });
                }

                @Override
                public void onFinish() {
                    reset();
                    if (finishListener != null) {
                        handler.post(new Runnable() {
                            @Override
                            public void run() {
                                finishListener.onFinish();
                            }
                        });
                    }
                }
            };
            timer.start();
        }
    }

    public void reset() {
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
        index = 0;
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float left = (getWidth() * index * 1.0F) / (totalCount);
        float top = 0F;
        float right = getWidth() - left;
        float bottom = getMeasuredHeight();
        canvas.drawRect(left, top, right, bottom, paint);
    }

    public interface OnFinishListener {
        /**
         * 完成时回调,回调到主线程
         */
        void onFinish();
    }
}
