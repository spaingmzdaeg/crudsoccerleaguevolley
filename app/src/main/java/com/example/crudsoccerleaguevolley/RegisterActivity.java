package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterActivity extends AppCompatActivity {

    private EditText username,email,password,confpassword;
    private Button btn_signup;
    private ProgressBar loading;
    private static String URL_SIGNUP = "https://chestersports.000webhostapp.com/register.php";



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // new NukeSSLCerts().nuke();
        loading = findViewById(R.id.loading);
        username = findViewById(R.id.username);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);
        confpassword = findViewById(R.id.confpassword);
        btn_signup = findViewById(R.id.btn_signup);


        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validator()){
                    Signup();
                }


            }
        });
    }

    private boolean validator(){
        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();
        final String confpassword = this.confpassword.getText().toString().trim();

        if(username.length() < 5){
            Toast.makeText(RegisterActivity.this,"USERNAME MIN 5 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(username.length() > 20){
            Toast.makeText(RegisterActivity.this,"USERNAME MAX 20 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidEmail(email)){
            Toast.makeText(RegisterActivity.this,"Email Invalited !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.length() < 8){
            Toast.makeText(RegisterActivity.this,"Password Min 8 characters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(password.length() > 20){
            Toast.makeText(RegisterActivity.this,"Password Max 20 characters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(!password.equals(confpassword)){
            Toast.makeText(RegisterActivity.this,"Passwords must be the same !",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }

    private boolean isValidEmail(String target) {
        return (!TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches());
    }


    public static void showToast(Context mContext, String message){
        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
    }



    private void Signup(){
        loading.setVisibility(View.VISIBLE);
        btn_signup.setVisibility(View.GONE);

        final String username = this.username.getText().toString().trim();
        final String email = this.email.getText().toString().trim();
        final String password = this.password.getText().toString().trim();


        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_SIGNUP, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    //JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");


                    if (success.equals("1")){
                        Toast.makeText(RegisterActivity.this,"SIGNUP SUCCESS!",Toast.LENGTH_SHORT).show();

                        Intent intent1 = new Intent(RegisterActivity.this, HomeActivity.class);
                        startActivity(intent1);
                        finish();
                        //System.out.println("deberia funcionar");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RegisterActivity.this,"SIGNUP Exception!" + e.toString(),Toast.LENGTH_SHORT).show();
                    loading.setVisibility(View.GONE);
                    btn_signup.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(RegisterActivity.this,"SIGNUP ERROR!" + error.toString(),Toast.LENGTH_SHORT).show();
                loading.setVisibility(View.GONE);
                btn_signup.setVisibility(View.VISIBLE);
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("username",username);
                params.put("email",email);
                params.put("password",password);
                return params;
            }
        };


        //HttpsTrustManager.allowAllSSL();
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}