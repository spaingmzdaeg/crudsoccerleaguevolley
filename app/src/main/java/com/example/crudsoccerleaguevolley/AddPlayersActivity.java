package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AddPlayersActivity extends AppCompatActivity {
    Spinner team, position, country;
    ArrayList<String> teamList = new ArrayList<>();
    ArrayAdapter<String> teamAdapter;
    RequestQueue requestQueueTeams;
    private static String URL_TEAMS = "https://chestersports.000webhostapp.com/teams.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);

        String[] arraySpinner = new String[]{
                "1", "2", "3", "4", "5", "6", "7"
        };
        position = (Spinner) findViewById(R.id.position);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(adapter);
        requestQueueTeams = Volley.newRequestQueue(this);
        team = (Spinner) findViewById(R.id.team);
        String url = "https://chestersports.000webhostapp.com/teams.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("teams");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String teamClub = jsonObject.optString("club");
                        teamList.add(teamClub);
                        teamAdapter = new ArrayAdapter<>(AddPlayersActivity.this,android.R.layout.simple_spinner_item,teamList);
                        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        team.setAdapter(teamAdapter);}
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(AddPlayersActivity.this,"Error Reading Detail "+e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(AddPlayersActivity.this,"Error Reading Detail "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueueTeams.add(jsonObjectRequest);

    }

}