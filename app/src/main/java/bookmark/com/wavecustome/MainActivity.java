package bookmark.com.wavecustome;

import android.os.Bundle;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Chronometer;
import android.widget.ImageView;


import bookmark.com.wavecustome.waveanimation.AndroWaveView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    AndroWaveView aw_wave;
    ImageView btn_animationSwitch, btn_favorite, btn_stopSwitch;
    Chronometer timer;
    boolean shouldPlayAnimation = true;
    boolean shouldFav = true;
    boolean shouldStop = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        aw_wave = (AndroWaveView) findViewById(R.id.aw_wave);
        btn_animationSwitch = (ImageView) findViewById(R.id.btn_animationSwitch);
        btn_favorite = (ImageView) findViewById(R.id.btn_favorite);
        btn_stopSwitch = (ImageView) findViewById(R.id.btn_stopSwitch);
        timer = (Chronometer) findViewById(R.id.timer);
        timer.setFormat("%s");
        aw_wave.stopAnimation();
        btn_animationSwitch.setOnClickListener(this);
        btn_stopSwitch.setOnClickListener(this);
        btn_favorite.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_animationSwitch:
                if (shouldPlayAnimation) {
                    shouldPlayAnimation = false;
                    shouldStop = true;
                    btn_stopSwitch.setImageResource(R.drawable.stop_button);
                    btn_animationSwitch.setImageResource(R.drawable.pause);
                    timer.start();
                    aw_wave.startAnimation();
                } else {
                    btn_animationSwitch.setImageResource(R.drawable.play);
                    shouldPlayAnimation = true;
                    timer.stop();
                    aw_wave.stopAnimation();
                }
                break;
            case R.id.btn_favorite:
                if (shouldFav) {
                    shouldFav = false;
                    btn_favorite.setImageResource(R.drawable.favorite);
                } else {
                    shouldFav = true;
                    btn_favorite.setImageResource(R.drawable.unfavorite_button);
                }
                break;
            case R.id.btn_stopSwitch:
                if (shouldStop) {
                    shouldStop = false;
                    shouldPlayAnimation = true;
                    btn_animationSwitch.setImageResource(R.drawable.play);
                    btn_stopSwitch.setImageResource(R.drawable.stop_fill);
                    timer.setBase(SystemClock.elapsedRealtime());
                    aw_wave.stopAnimation();
                } else {
                    shouldStop = true;
                    timer.setBase(SystemClock.elapsedRealtime());
                    btn_stopSwitch.setImageResource(R.drawable.stop_button);
                }
                break;
        }
    }
}
