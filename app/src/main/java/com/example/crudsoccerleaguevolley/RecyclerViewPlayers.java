package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
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
    private RecyclerView.LayoutManager mLayoutManager;


    private RecyclerView mRecyclerView;
    private PlayerAdapter mPlayerAdapter;
    private ArrayList<PlayersItem> mPlayersList;
    private RequestQueue mRequestQueue;
    private Spinner id_team;ArrayList<String> teamList = new ArrayList<>();
    ArrayAdapter<String> teamAdapter;
    RequestQueue requestQueueTeams;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_view_players);

        requestQueueTeams = Volley.newRequestQueue(this);
        id_team = (Spinner) findViewById(R.id.spinner_id_team);
        String url = "https://chestersports.000webhostapp.com/teams.php";

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("teams");
                    for(int i=0; i<jsonArray.length(); i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String teamClub = jsonObject.optString("id_team");//aqui es club para que muestre nombre
                        String teamClubName = jsonObject.optString("club");
                        teamList.add(teamClub+"-"+teamClubName);
                        teamAdapter = new ArrayAdapter<>(RecyclerViewPlayers.this,android.R.layout.simple_spinner_item,teamList);
                        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        id_team.setAdapter(teamAdapter);}
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(RecyclerViewPlayers.this,"Error Reading Detail "+e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(RecyclerViewPlayers.this,"Error Reading Detail "+error.toString(),Toast.LENGTH_SHORT).show();
            }
        });
        requestQueueTeams.add(jsonObjectRequest);

        EditText search = findViewById(R.id.searching_first_name);
        search.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter_first_name(s.toString());

            }
        });

        EditText search2 = findViewById(R.id.searching_last_name);
        search2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter_last_name(s.toString());
            }
        });

        EditText search3 = findViewById(R.id.searching_country);
        search3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter_country(s.toString());
            }
        });


        EditText search4 = findViewById(R.id.searching_team);
        search4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                filter_team(s.toString());
            }
        });

        id_team.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
              //id_team.getSelectedItem().toString().substring(0,id_team.getSelectedItem().toString().indexOf("-"));

               // String selectedItem = parent.getItemAtPosition(position).toString().substring(0,2);
               // Toast.makeText(RecyclerViewPlayers.this,selectedItem,Toast.LENGTH_LONG).show();
              //  filter_team(selectedItem);
                //Toast.makeText(RecyclerViewPlayers.this,id_team.getSelectedItem().toString().substring(0,id_team.getSelectedItem().toString().indexOf("-")),Toast.LENGTH_SHORT).show();
             // filter_team(id_team.getSelectedItem().toString().substring(0,id_team.getSelectedItem().toString().indexOf("-")));


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


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
        Toast.makeText(RecyclerViewPlayers.this,String.valueOf(position),Toast.LENGTH_SHORT).show();
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

    private void filter_first_name(String text) {
        ArrayList<PlayersItem> filteredList = new ArrayList<>();
        for (PlayersItem item : mPlayersList) {
            if (item.getmFirstName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }


        mPlayersList = filteredList;
        mPlayerAdapter.filterList(mPlayersList);


    }

    private void filter_last_name(String text) {
        ArrayList<PlayersItem> filteredList = new ArrayList<>();
        for (PlayersItem item : mPlayersList) {
            if (item.getmLastName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mPlayersList = filteredList;
        mPlayerAdapter.filterList(mPlayersList);

    }

    private void filter_country(String text) {
        ArrayList<PlayersItem> filteredList = new ArrayList<>();
        for (PlayersItem item : mPlayersList) {
            if (item.getmCountry().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mPlayersList = filteredList;
        mPlayerAdapter.filterList(mPlayersList);

    }

    private void filter_team(String text) {
        ArrayList<PlayersItem> filteredList = new ArrayList<>();
        for (PlayersItem item : mPlayersList) {
            if (item.getmIdTeam().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        mPlayersList = filteredList;
        mPlayerAdapter.filterList(mPlayersList);

    }


    private void buildRecyclerView() {
        mRecyclerView = findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(this);
        mPlayerAdapter = new PlayerAdapter(getApplicationContext(),mPlayersList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(mPlayerAdapter);
    }
}