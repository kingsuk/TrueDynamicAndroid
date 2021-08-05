package ws.wolfsoft.cryptostar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);


        Thread background = new Thread() {
            public void run() {

                try {
                    // Thread will sleep for 3 seconds
                    sleep(3000);

                    SharedPreferences preferences = getSharedPreferences("UserData", MODE_PRIVATE);
                    String userInfo = preferences.getString("userInfo", null);
                    if(userInfo==null)
                    {
                        Intent intent = new Intent(SplashScreenActivity.this,LoginActivity.class);
                        startActivity(intent);
                    }
                    else
                    {
                        Intent intent = new Intent(SplashScreenActivity.this,WalletCryptoStarActivity.class);
                        startActivity(intent);
                    }


                    // After 5 seconds redirect to another intent


                    //Remove activity
                    finish();

                } catch (Exception e) {

                }
            }
        };

        // start thread
        background.start();


    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }


}
