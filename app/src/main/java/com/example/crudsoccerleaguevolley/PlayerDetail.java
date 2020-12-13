package com.example.crudsoccerleaguevolley;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

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

        btn_update_player = findViewById(R.id.btn_update_players);

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

    }
}