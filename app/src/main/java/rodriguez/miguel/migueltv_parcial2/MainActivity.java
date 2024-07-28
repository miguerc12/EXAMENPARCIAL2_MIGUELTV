package rodriguez.miguel.migueltv_parcial2;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import android.media.MediaPlayer;


public class MainActivity extends AppCompatActivity {

    private static int SPLASH_TIME_OUT = 3000;
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mediaPlayer = MediaPlayer.create(this, R.raw.intro_sound);


        mediaPlayer.start();

        ImageView logo = findViewById(R.id.logo);
        Animation rotate = AnimationUtils.loadAnimation(this, R.anim.rotate);
        logo.startAnimation(rotate);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                if (mediaPlayer != null) {
                    mediaPlayer.stop();
                    mediaPlayer.release();
                    mediaPlayer = null;
                }

                Intent i = new Intent(MainActivity.this, Welcome_Activity.class);
                startActivity(i);
                finish();
            }
        }, SPLASH_TIME_OUT);
    }
}