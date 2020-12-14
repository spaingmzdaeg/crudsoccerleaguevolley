package com.example.crudsoccerleaguevolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.example.crudsoccerleaguevolley.PlayerDetail.EXTRA_COUNTRY;
import static com.example.crudsoccerleaguevolley.PlayerDetail.EXTRA_FIRST_NAME;
import static com.example.crudsoccerleaguevolley.PlayerDetail.EXTRA_ID_PLAYER;
import static com.example.crudsoccerleaguevolley.PlayerDetail.EXTRA_KIT;
import static com.example.crudsoccerleaguevolley.PlayerDetail.EXTRA_LAST_NAME;
import static com.example.crudsoccerleaguevolley.PlayerDetail.EXTRA_POSITION;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_ID_TEAM;
import static com.example.crudsoccerleaguevolley.RecyclerViewPlayers.EXTRA_IMAGE;

public class EditPlayersActivity extends AppCompatActivity {

    Spinner id_team, position, country;
    EditText first_name,last_name,kit;
    ArrayList<String> teamList = new ArrayList<>();
    ArrayAdapter<String> teamAdapter;
    RequestQueue requestQueueTeams;
    private static String URL_EDIT =  "https://chestersports.000webhostapp.com/edit_players.php";
    private Bitmap bitmap;
    ImageView player_image;
    Button btn_photo,btn_update;
    int count_images;
    String getId_Player;
    String getId_Team;
    String getFirst_Name;
    String getLast_Name;
    String getKit;
    String getPosition;
    String getCountry;
    String getImageUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_players);

        Intent intent = getIntent();
         getId_Player = intent.getStringExtra(EXTRA_ID_PLAYER);
         getId_Team = intent.getStringExtra(EXTRA_ID_TEAM);
         getFirst_Name = intent.getStringExtra(EXTRA_FIRST_NAME);
         getLast_Name = intent.getStringExtra(EXTRA_LAST_NAME);
         getKit = intent.getStringExtra(EXTRA_KIT);
         getPosition = intent.getStringExtra(EXTRA_POSITION);
         getCountry = intent.getStringExtra(EXTRA_COUNTRY);
         getImageUrl = intent.getStringExtra(EXTRA_IMAGE);

        Toast.makeText(EditPlayersActivity.this,"ID_PLAYER:" +getId_Player,Toast.LENGTH_SHORT).show();



        String[] arrayCountries = new String[]{"Afghanistan", "Albania", "Algeria", "American Samoa", "Andorra", "Angola", "Anguilla",

                "Antarctica", "Antigua and Barbuda", "Argentina", "Armenia", "Aruba", "Australia", "Austria",

                "Azerbaijan", "Bahamas", "Bahrain", "Bangladesh", "Barbados", "Belarus", "Belgium",

                "Belize", "Benin", "Bermuda", "Bhutan", "Bolivia", "Bosnia and Herzegovina", "Botswana",

                "Brazil", "British Indian Ocean Territory", "British Virgin Islands", "Brunei", "Bulgaria",

                "Burkina Faso", "Burma (Myanmar)", "Burundi", "Cambodia", "Cameroon", "Canada", "Cape Verde",

                "Cayman Islands", "Central African Republic", "Chad", "Chile", "China", "Christmas Island",

                "Cocos (Keeling) Islands", "Colombia", "Comoros", "Cook Islands", "Costa Rica",

                "Croatia", "Cuba", "Cyprus", "Czech Republic", "Democratic Republic of the Congo",

                "Denmark", "Djibouti", "Dominica", "Dominican Republic",

                "Ecuador", "Egypt", "El Salvador", "Equatorial Guinea", "Eritrea", "Estonia",

                "Ethiopia", "Falkland Islands", "Faroe Islands", "Fiji", "Finland", "France", "French Polynesia",

                "Gabon", "Gambia", "Gaza Strip", "Georgia", "Germany", "Ghana", "Gibraltar", "Greece",

                "Greenland", "Grenada", "Guam", "Guatemala", "Guinea", "Guinea-Bissau", "Guyana",

                "Haiti", "Holy See (Vatican City)", "Honduras", "Hong Kong", "Hungary", "Iceland", "India",

                "Indonesia", "Iran", "Iraq", "Ireland", "Isle of Man", "Israel", "Italy", "Ivory Coast", "Jamaica",

                "Japan", "Jersey", "Jordan", "Kazakhstan", "Kenya", "Kiribati", "Kosovo", "Kuwait",

                "Kyrgyzstan", "Laos", "Latvia", "Lebanon", "Lesotho", "Liberia", "Libya", "Liechtenstein",

                "Lithuania", "Luxembourg", "Macau", "Macedonia", "Madagascar", "Malawi", "Malaysia",

                "Maldives", "Mali", "Malta", "Marshall Islands", "Mauritania", "Mauritius", "Mayotte", "Mexico",

                "Micronesia", "Moldova", "Monaco", "Mongolia", "Montenegro", "Montserrat", "Morocco",

                "Mozambique", "Namibia", "Nauru", "Nepal", "Netherlands", "Netherlands Antilles", "New Caledonia",

                "New Zealand", "Nicaragua", "Niger", "Nigeria", "Niue", "Norfolk Island", "North Korea",

                "Northern Mariana Islands", "Norway", "Oman", "Pakistan", "Palau", "Panama",

                "Papua New Guinea", "Paraguay", "Peru", "Philippines", "Pitcairn Islands", "Poland",

                "Portugal", "Puerto Rico", "Qatar", "Republic of the Congo", "Romania", "Russia", "Rwanda",

                "Saint Barthelemy", "Saint Helena", "Saint Kitts and Nevis", "Saint Lucia", "Saint Martin",

                "Saint Pierre and Miquelon", "Saint Vincent and the Grenadines", "Samoa", "San Marino",

                "Sao Tome and Principe", "Saudi Arabia", "Senegal", "Serbia", "Seychelles", "Sierra Leone",

                "Singapore", "Slovakia", "Slovenia", "Solomon Islands", "Somalia", "South Africa", "South Korea",

                "Spain", "Sri Lanka", "Sudan", "Suriname", "Swaziland", "Sweden", "Switzerland",

                "Syria", "Taiwan", "Tajikistan", "Tanzania", "Thailand", "Timor-Leste", "Togo", "Tokelau",

                "Tonga", "Trinidad and Tobago", "Tunisia", "Turkey", "Turkmenistan", "Turks and Caicos Islands",

                "Tuvalu", "Uganda", "Ukraine", "United Arab Emirates", "United Kingdom", "United States", "Uruguay", "US Virgin Islands", "Uzbekistan", "Vanuatu", "Venezuela", "Vietnam",

                "Wallis and Futuna", "West Bank", "Yemen", "Zambia", "Zimbabwe"};

        String[] arraySpinner = new String[]{
                "Goalkeeper", "Defender", "Midfielder", "Forward"
        };
        position = (Spinner) findViewById(R.id.position);
        ArrayAdapter<String> adapterPosition = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapterPosition.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(adapterPosition);

        country = findViewById(R.id.country);
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCountries);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapterCountry);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        kit = findViewById(R.id.kit);
        btn_update = findViewById(R.id.btn_update);
        btn_photo = findViewById(R.id.btn_photo);
        player_image = findViewById(R.id.imgPhoto);



        requestQueueTeams = Volley.newRequestQueue(this);
        id_team = (Spinner) findViewById(R.id.id_team);
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
                        teamAdapter = new ArrayAdapter<>(EditPlayersActivity.this,android.R.layout.simple_spinner_item,teamList);
                        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        id_team.setAdapter(teamAdapter);}
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(EditPlayersActivity.this,"Error Reading Detail "+e.toString(),Toast.LENGTH_SHORT).show();

                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                Toast.makeText(EditPlayersActivity.this,"CHANGE PICTURE AGAIN",Toast.LENGTH_SHORT).show();
            }
        });
        requestQueueTeams.add(jsonObjectRequest);

        first_name.setText(getFirst_Name);
        last_name.setText(getLast_Name);
        kit.setText(getKit);
        int spinnerPosition = adapterPosition.getPosition(getPosition);
        position.setSelection(spinnerPosition);
        int spinnerCountry = adapterCountry.getPosition(getCountry);
        country.setSelection(spinnerCountry);
       // int spinnerTeam = teamAdapter.getPosition(getId_Team);
       //id_team.setSelection(spinnerTeam);

        Picasso.with(this).load(getImageUrl).fit().centerInside().into(player_image);

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validator()){
                    Intent detailIntent = new Intent(EditPlayersActivity.this, RecyclerViewPlayers.class);
                saveEditPlayer();
                    startActivity(detailIntent);
                }


            }
        });


    }


    private boolean validator(){
        final String first_name = this.first_name.getText().toString().trim();
        final String last_name = this.last_name.getText().toString().trim();
        final String kit = this.kit.getText().toString().trim();


        if(first_name.length() < 4){
            Toast.makeText(EditPlayersActivity.this,"FIRSTNAME MIN 4 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(first_name.length() > 20){
            Toast.makeText(EditPlayersActivity.this,"FISRTNAME MAX 20 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(last_name.length() < 4){
            Toast.makeText(EditPlayersActivity.this,"LASTNAME MIN 4 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(last_name.length() > 20){
            Toast.makeText(EditPlayersActivity.this,"LASTNAME MAX 20 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(kit.length() > 2){
            Toast.makeText(EditPlayersActivity.this,"ONLY RANGE 1 TO 100 Permited !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(kit.equals("")){
            Toast.makeText(EditPlayersActivity.this,"kit required !",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }

    private void saveEditPlayer(){
        int numero = (int)(Math.random()*100000+1);;

        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Saving...");
        progressDialog.show();

        final String id_team = this.id_team.getSelectedItem().toString().substring(0,this.id_team.getSelectedItem().toString().indexOf("-"));
        final String first_name = this.first_name.getText().toString().trim();
        final String last_name = this.last_name.getText().toString().trim();
        final String kit = this.kit.getText().toString().trim();
        final String position = this.position.getSelectedItem().toString();
        final String country = this.country.getSelectedItem().toString();
        final String namePhoto = String.valueOf(numero);

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_EDIT, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");

                    if(success.equals("1")){
                        Toast.makeText(EditPlayersActivity.this,"UPDATE Success!",Toast.LENGTH_LONG).show();

                    }else if(success.equals("0")){
                        Toast.makeText(EditPlayersActivity.this,"PHP FAIL!",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(EditPlayersActivity.this,"ERROR!"+e.toString(),Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                progressDialog.dismiss();
                Toast.makeText(EditPlayersActivity.this,"ERROR!"+error.toString(),Toast.LENGTH_SHORT).show();

            }
        })

        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<>();
                params.put("id_team",id_team);
                params.put("first_name",first_name);
                params.put("last_name",last_name);
                params.put("kit",kit);
                params.put("position",position);
                params.put("country",country);
                params.put("photo",getStringImage(bitmap));
                params.put("namePhoto",namePhoto);
                params.put("id_player",getId_Player);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private void chooseFile(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"),1);

    }

    public String getStringImage(Bitmap bitmap){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);

        byte[] imageByteArray = byteArrayOutputStream.toByteArray();
        String encodedImage = Base64.encodeToString(imageByteArray,Base64.DEFAULT);
        return encodedImage;
    }

    public static boolean hasNullOrEmptyDrawable(ImageView iv)
    {
        Drawable drawable = iv.getDrawable();
        BitmapDrawable bitmapDrawable = drawable instanceof BitmapDrawable ? (BitmapDrawable)drawable : null;

        return bitmapDrawable == null || bitmapDrawable.getBitmap() == null;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {


                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                player_image.setImageBitmap(bitmap);

                Toast.makeText(EditPlayersActivity.this,filePath.toString(),Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(EditPlayersActivity.this,"EMPTY PICTURE FAIL",Toast.LENGTH_LONG).show();
            }
        }
    }


}