package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_ID_PLAYER;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_IMAGE;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_ID_TEAM;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_FIRST_NAME;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_LAST_NAME;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_KIT;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_POSITION;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_COUNTRY;

public class PlayerDetail extends AppCompatActivity {

    Button btn_update_player;
    Button btn_delete_player;
    public static final String EXTRA_ID_PLAYER = "id_player";
    public static final String EXTRA_IMAGE = "photo";
    public static final String EXTRA_ID_TEAM = "id_team";
    public static final String EXTRA_FIRST_NAME = "first_name";
    public static final String EXTRA_LAST_NAME = "last_name";
    public static final String EXTRA_KIT = "kit";
    public static final String EXTRA_POSITION = "position";
    public static final String EXTRA_COUNTRY = "country";
    private static String URL_DELETE =  "https://chestersports.000webhostapp.com/delete_player.php";
    String id_player_to_delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_detail);



        Intent intent = getIntent();
        String imageUrl = intent.getStringExtra(EXTRA_IMAGE);
        String id_team = intent.getStringExtra(EXTRA_ID_TEAM);
        String first_name = intent.getStringExtra(EXTRA_FIRST_NAME);
        String last_name = intent.getStringExtra(EXTRA_LAST_NAME);
        String kit = intent.getStringExtra(EXTRA_KIT);
        String position = intent.getStringExtra(EXTRA_POSITION);
        String country = intent.getStringExtra(EXTRA_COUNTRY);
        String id_player = intent.getStringExtra(EXTRA_ID_PLAYER);
        id_player_to_delete = id_player;

        Toast.makeText(PlayerDetail.this,"ID_PLAYER:" +id_player,Toast.LENGTH_SHORT).show();

        btn_update_player = findViewById(R.id.btn_update_players);
        btn_delete_player = findViewById(R.id.btn_delete_players);

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewIdTeam = findViewById(R.id.text_view_id_team_detail);
        TextView textViewFirstName = findViewById(R.id.text_view_first_name_detail);
        TextView textViewLastName = findViewById(R.id.text_view_last_name_detail);
        TextView textViewKit = findViewById(R.id.text_view_kit_detail);
        TextView textViewPosition = findViewById(R.id.text_view_position_detail);
        TextView textViewCountry = findViewById(R.id.text_view_country_detail);

        Picasso.with(this).load(imageUrl).fit().centerInside().into(imageView);
        textViewIdTeam.setText("ID_TEAM:"+id_team);
        textViewFirstName.setText("First_Name:"+first_name);
        textViewLastName.setText("Last_Name:"+last_name);
        textViewKit.setText("Kit:"+kit);
        textViewPosition.setText(position);
        textViewCountry.setText(country);


        btn_update_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent(PlayerDetail.this, EditPlayersActivity.class);
                intent1.putExtra(EXTRA_ID_PLAYER, id_player);
                intent1.putExtra(EXTRA_IMAGE, imageUrl);
                intent1.putExtra(EXTRA_ID_TEAM, id_team);
                intent1.putExtra(EXTRA_FIRST_NAME, first_name);
                intent1.putExtra(EXTRA_LAST_NAME, last_name);
                intent1.putExtra(EXTRA_KIT, kit);
                intent1.putExtra(EXTRA_POSITION, position);
                intent1.putExtra(EXTRA_COUNTRY, country);
                startActivity(intent1);
                finish();
            }
        });

        btn_delete_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deletePlayer();
            }
        });

    }

    public void deletePlayer(){


        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Deleting...");
        progressDialog.show();

        final String id_player = id_player_to_delete;

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_DELETE, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(PlayerDetail.this,"DELETE Success!",Toast.LENGTH_SHORT).show();

                    }else if(success.equals("0")){
                        Toast.makeText(PlayerDetail.this,"PHP FAIL!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(PlayerDetail.this,"ERROR!"+e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(PlayerDetail.this,"ERROR!"+error.toString(),Toast.LENGTH_SHORT).show();

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_player",id_player);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }
}