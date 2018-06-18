package cybertech.moestrackerv2;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;

public class splash extends AppCompatActivity {

    ProgressBar loadingProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // setting screen to full screen.
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        // loadingProgress = ProgressDialog.show(splash.this,null,null,true,false);
        loadingProgress = (ProgressBar) findViewById(R.id.progressloader);
        loadingProgress.setIndeterminate(true);

        Thread welcomethread = new Thread() {
            public void run() {
                try {
                    sleep(5000);
                    // start welcome Activity and finish(); the splash screen.
                    startActivity(new Intent(splash.this, activation.class));
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    finish();
                }
            }
        };
        welcomethread.start();
    }
}
