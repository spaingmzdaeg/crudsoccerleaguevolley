package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class RecyclerViewPlayers extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private PlayerAdapter mPlayerAdapter;
    private ArrayList<PlayersItem> mPlayersList;
    private RequestQueue mRequestQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_players);

        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mPlayersList = new ArrayList<>();
        mRequestQueue = Volley.newRequestQueue(this);
        parseJSON();
    }

    private void parseJSON() {
        String url = "https://chestersports.000webhostapp.com/players.php";
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jsonArray = response.getJSONArray("players");
                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject player = jsonArray.getJSONObject(i);
                                String imageUrl = player.getString("photo");
                                String id_team = player.getString("id_player");
                                String first_name = player.getString("first_name");
                                String last_name = player.getString("last_name");
                                String kit = player.getString("kit");
                                String position = player.getString("position");
                                String country = player.getString("country");
                                mPlayersList.add(new PlayersItem(imageUrl, id_team, first_name,last_name,kit,position,country));
                            }
                            mPlayerAdapter = new PlayerAdapter(RecyclerViewPlayers.this, mPlayersList);
                            mRecyclerView.setAdapter(mPlayerAdapter);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(RecyclerViewPlayers.this,"ERROR!"+e.toString(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RecyclerViewPlayers.this,"ERROR!"+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        mRequestQueue.add(request);
    }
}