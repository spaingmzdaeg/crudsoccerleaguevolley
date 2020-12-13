package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

public class RecyclerViewPlayers extends AppCompatActivity implements PlayerAdapter.OnItemClickListener {

    public static final String EXTRA_IMAGE = "photo";
    public static final String EXTRA_ID_TEAM = "id_team";
    public static final String EXTRA_FIRST_NAME = "first_name";
    public static final String EXTRA_LAST_NAME = "last_name";
    public static final String EXTRA_KIT = "kit";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_COUNTRY = "country";
    public static final String EXTRA_ID_PLAYER = "id_player";


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
                                String id_player = player.getString("id_player");
                                String id_team = player.getString("id_team");
                                String first_name = player.getString("first_name");
                                String last_name = player.getString("last_name");
                                String kit = player.getString("kit");
                                String position = player.getString("position");
                                String country = player.getString("country");
                                mPlayersList.add(new PlayersItem(imageUrl, id_team, first_name,last_name,kit,position,country,id_player));
                            }
                            mPlayerAdapter = new PlayerAdapter(RecyclerViewPlayers.this, mPlayersList);
                            mRecyclerView.setAdapter(mPlayerAdapter);
                            mPlayerAdapter.setOnItemClickListener(RecyclerViewPlayers.this);
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

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, PlayerDetail.class);
        PlayersItem clickedItem = mPlayersList.get(position);
        detailIntent.putExtra(EXTRA_IMAGE, clickedItem.getmImageUrl());
        detailIntent.putExtra(EXTRA_ID_TEAM, clickedItem.getmIdTeam());
        detailIntent.putExtra(EXTRA_FIRST_NAME, clickedItem.getmFirstName());
        detailIntent.putExtra(EXTRA_LAST_NAME, clickedItem.getmLastName());
        detailIntent.putExtra(EXTRA_KIT, clickedItem.getmKit());
        detailIntent.putExtra(EXTRA_POSITION, clickedItem.getmPosition());
        detailIntent.putExtra(EXTRA_COUNTRY, clickedItem.getmCountry());
        detailIntent.putExtra(EXTRA_ID_PLAYER, clickedItem.getmIdPlayer());
        startActivity(detailIntent);
    }
}