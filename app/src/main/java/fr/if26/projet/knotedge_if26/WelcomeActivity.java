package fr.if26.projet.knotedge_if26;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import java.util.Timer;
import java.util.TimerTask;

public class WelcomeActivity extends Activity {

    private ImageView imgLogo;
    private Animation animation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        //impor the animation
        animation = AnimationUtils.loadAnimation(WelcomeActivity.this, R.anim.alpha);

        //Add animation for the logo
        imgLogo = findViewById(R.id.splash_logo);
        imgLogo.startAnimation(animation);

        //Timer to go to the Main Activity
        final Intent intent = new Intent(this, MainActivity.class);
        Timer timer = new Timer();
        TimerTask timerTask = new TimerTask() {
            @Override
            public void run() {
                startActivity(intent);
            }
        };
        timer.schedule(timerTask, 2000);

    }
}
