package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final String TAG = HomeActivity.class.getSimpleName();
    private TextView email,username;
    private Button btn_logout;
    SessionManager sessionManager;
    String getId_User;
    private static String URL_READ =  "https://chestersports.000webhostapp.com/read_detail.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        sessionManager = new SessionManager(this);
        sessionManager.checkLogin();

        email = findViewById(R.id.email);
        username = findViewById(R.id.username);
        btn_logout = findViewById(R.id.btn_logout);

        HashMap<String,String> user = sessionManager.getUserDetail();
        getId_User = user.get(sessionManager.ID_USER);
       // String mEmail = user.get(sessionManager.EMAIL);
        //String mUsername = user.get(sessionManager.USERNAME);

       // email.setText(mEmail);
        //username.setText(mUsername);

       /* Intent intent = getIntent();
        String extraEmail = intent.getStringExtra("email");
        String extraUsername = intent.getStringExtra("username");*/

        //email.setText(extraEmail);
        //username.setText(extraUsername);

        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sessionManager.logout();
            }
        });
    }

    public void getUserDetail(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_READ, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                progressDialog.dismiss();
                Log.i(TAG,response.toString());
                try {
                    JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));
                    String success = jsonObject.getString("success");
                    JSONArray jsonArray = jsonObject.getJSONArray("read");

                    if(success.equals("1")){
                        for(int i = 0; i < jsonArray.length(); i++){
                            JSONObject object = jsonArray.getJSONObject(i);

                            String strEmail = object.getString("email").trim();
                            String strUsername = object.getString("username").trim();

                            email.setText(strEmail);
                            username.setText(strUsername);

                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(HomeActivity.this,"Error Reading Detail "+e.toString(),Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                error.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(HomeActivity.this,"Error Reading Detail "+error.toString(),Toast.LENGTH_SHORT).show();

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_user",getId_User);
                return params;
            }
        };


        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getUserDetail();
    }
}