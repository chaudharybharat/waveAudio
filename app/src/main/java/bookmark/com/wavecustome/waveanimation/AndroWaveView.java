package bookmark.com.wavecustome.waveanimation;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;


import java.util.Random;

import bookmark.com.wavecustome.R;

/**
 * Created by mind on 18/2/17.
 */
public class AndroWaveView extends View {

    private Path mPath, mPath2, mPath3;
    private Paint mPaint, mPaint2, mPaint3;

    private float frequency = 1.5f;
    private float IdleAmplitude = 0.00f;
    private int waveNumber = 2;
    private float phaseShift = 0.15f;
    private float initialPhaseOffset = 0.0f;
    private float waveHeight;
    private float waveVerticalPosition = 2;
    private int waveColor;
    private int waveFillColor;
    private int waveFillColor2;
    private float phase;
    private float amplitude;
    private float level = 1.0f;

    ObjectAnimator objectAnimator;
    DisplayMetrics metrics;

    public AndroWaveView(Context context) {
        super(context);
        if (!isInEditMode())
            init(context, null);
    }

    public AndroWaveView(Context context, AttributeSet attrs) {
        super(context, attrs);
        if (!isInEditMode())
            init(context, attrs);
    }

    public AndroWaveView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        if (!isInEditMode())
            init(context, attrs);
    }

    public void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.AndroWaveView);
        frequency = a.getFloat(R.styleable.AndroWaveView_waveFrequency, frequency);
        IdleAmplitude = a.getFloat(R.styleable.AndroWaveView_waveIdleAmplitude, IdleAmplitude);
        phaseShift = a.getFloat(R.styleable.AndroWaveView_wavePhaseShift, phaseShift);
        initialPhaseOffset = a.getFloat(R.styleable.AndroWaveView_waveInitialPhaseOffset, initialPhaseOffset);
        waveHeight = a.getDimension(R.styleable.AndroWaveView_waveHeight, waveHeight);
        waveColor = a.getColor(R.styleable.AndroWaveView_waveColor, waveColor);
        waveFillColor = a.getColor(R.styleable.AndroWaveView_waveFillColor, waveFillColor);
        waveFillColor2 = a.getColor(R.styleable.AndroWaveView_waveFillColor2, waveFillColor);
        waveVerticalPosition = a.getFloat(R.styleable.AndroWaveView_waveVerticalPosition, waveVerticalPosition);
        waveNumber = a.getInteger(R.styleable.AndroWaveView_waveAmount, waveNumber);

        metrics = getContext().getResources().getDisplayMetrics();

        mPath = new Path();
        mPath2 = new Path();
        mPath3 = new Path();
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint2 = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint3 = new Paint(Paint.ANTI_ALIAS_FLAG);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(waveColor);

        mPaint2.setStyle(Paint.Style.FILL);
        mPaint2.setColor(waveFillColor);

        mPaint3.setStyle(Paint.Style.FILL);
        mPaint3.setColor(waveFillColor2);
        a.recycle();
        initAnimation();
    }

    private void initAnimation() {
        if (objectAnimator == null) {
            objectAnimator = ObjectAnimator.ofFloat(this, "amplitude", 1f);
            objectAnimator.setRepeatCount(ObjectAnimator.INFINITE);
        }
        if (!objectAnimator.isRunning()) {
            objectAnimator.start();
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
        canvas.drawPath(mPath2, mPaint2);
        //canvas.drawPath(mPath3, mPaint3);
        updatePath1();
        updatePath2();
        //updatePath3();
    }

    private void updatePath1() {
        mPath.reset();

        phase += phaseShift;
        amplitude = Math.max(level, IdleAmplitude);
        for (int i = 0; i < waveNumber; i++) {
            float halfHeight = getHeight() / waveVerticalPosition;
            float width = getWidth();
            float mid = width / 2.0f;
            float maxAmplitude = halfHeight - (halfHeight - waveHeight);
            float progress = 1.0f - (float) i / waveNumber;
            float normedAmplitude = (1.5f * progress - 0.5f) * amplitude;

            float multiplier = (float) Math.min(1.0, (progress / 3.0f * 2.0f) + (1.0f / 3.0f));

            for (int x = 0; x < width; x++) {
                float scaling = (float) (-Math.pow(1 / mid * (x - mid), 2) + 1);
                float y = (float) (scaling * maxAmplitude * normedAmplitude * Math.sin(2 * Math.PI * (x / width) * frequency + (phase + 1) + initialPhaseOffset) + halfHeight);
                if (x == 0) {
                    mPath.moveTo(x, y);
                } else {
                    mPath.lineTo(x, y);
                }
            }
        }
    }

    private void updatePath2() {
        mPath2.reset();
        System.out.println("updatePath2() called");


        float minX = 0.7f;
        float maxX = 2.4f;

        Random rand = new Random();

        float finalX = rand.nextFloat() * (maxX - minX) + minX;

        phase += phaseShift;
        amplitude = Math.max(level, IdleAmplitude);

        float dp = 2f;
        float initOffset = metrics.density * dp;

        for (int i = 0; i < waveNumber; i++) {
            float halfHeight = getHeight() / waveVerticalPosition;
            float width = getWidth();
            float mid = width / 2.0f;
            float maxAmplitude = halfHeight - (halfHeight - waveHeight);
            float progress = 1.0f - (float) i / waveNumber;
            float normedAmplitude = (finalX * progress - (finalX-0.5f)) * amplitude;


            float multiplier = (float) Math.min(1.0, (progress / 3.0f * 2.0f) + (1.0f / 3.0f));

            for (int x = 0; x < width; x++) {
                float scaling = (float) (-Math.pow(1 / (mid-finalX) * (x - (mid-finalX)), 2) + 1);
                float y = (float) (scaling * maxAmplitude * (normedAmplitude) * Math.sin(2 * Math.PI * (x / width) * (frequency-0.7f) + phase + initOffset) + (halfHeight+finalX));
                if (x == 0) {
                    mPath2.moveTo(x, y);
                } else {
                    mPath2.lineTo(x, y);
                }
            }
        }
    }

    private void updatePath3() {
        mPath3.reset();

        phase += phaseShift;
        amplitude = Math.max(level, IdleAmplitude);

        float dp = 15f;
        float initOffset = metrics.density * dp;


        for (int i = 0; i < waveNumber; i++) {
            float halfHeight = getHeight() / waveVerticalPosition;
            float width = getWidth();
            float mid = width / 2.0f;
            float maxAmplitude = halfHeight - (halfHeight - waveHeight);
            float progress = 1.0f - (float) i / waveNumber;
            float normedAmplitude = (1.4f * progress - 0.5f) * amplitude;

            float multiplier = (float) Math.min(1.0, (progress / 3.0f * 2.0f) + (1.0f / 3.0f));

            for (int x = 0; x < width; x++) {
                float scaling = (float) (-Math.pow(1 / mid * (x - mid), 2) + 1);
                float y = (float) (scaling * maxAmplitude * normedAmplitude * Math.sin(2 * Math.PI * (x / width) * frequency + phase + initOffset) + halfHeight);
                if (x == 0) {
                    mPath3.moveTo(x, y);
                } else {
                    mPath3.lineTo(x, y);
                }
            }
        }
    }

    private void setAmplitude(float amplitude) {
        this.amplitude = amplitude;
        invalidate();
    }

    private float getAmplitude() {
        return this.amplitude;
    }

    public void stopAnimation() {
        if (objectAnimator != null) {
            objectAnimator.removeAllListeners();
            objectAnimator.end();
            objectAnimator.cancel();
        }
    }

    public void startAnimation() {
        if (objectAnimator != null) {
            objectAnimator.start();
        }
    }

    public void setWaveColor(int waveColor) {
        mPaint.setColor(waveColor);
        invalidate();
    }

    public void setWaveFillColor(int waveFillColor) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(waveFillColor);
    }

    public void setStrokeWidth(float strokeWidth) {
        mPaint.setStrokeWidth(strokeWidth);
        invalidate();
    }
}
