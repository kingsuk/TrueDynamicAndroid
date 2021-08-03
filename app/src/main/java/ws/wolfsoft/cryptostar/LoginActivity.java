package ws.wolfsoft.cryptostar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import ws.wolfsoft.cryptostar.Helper.StaticEntityHelper;

public class LoginActivity extends AppCompatActivity {

    EditText etEmailID;
    EditText etPassword;
    Button btnLogin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etEmailID = (EditText) findViewById(R.id.etEmailID);
        etPassword = (EditText) findViewById(R.id.etPassword);

        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

    }

    void loginUser()
    {
        String email = etEmailID.getText().toString();
        String password = etPassword.getText().toString();


        volleyPost(email,password);

    }

    public void volleyPost(String userName,String password){
        String postUrl = StaticEntityHelper.BASE_URL+"UserDataAPI/PostUser_Data";
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        JSONObject postData = new JSONObject();
        try {
            postData.put("UserEmail", userName);
            postData.put("Password", password);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, postUrl, postData, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                System.out.println(response);
                Log.i("response",response.toString());
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Login Successful.",
                        Toast.LENGTH_LONG);
                toast.show();

                SharedPreferences.Editor editor = getSharedPreferences("UserData", MODE_PRIVATE).edit();
                editor.putString("userInfo", response.toString());
                editor.apply();

                Intent intent = new Intent(LoginActivity.this,WalletCryptoStarActivity.class);
                startActivity(intent);

                finish();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Login failed.",
                        Toast.LENGTH_LONG);

                toast.show();
            }
        });

        requestQueue.add(jsonObjectRequest);

    }
}
