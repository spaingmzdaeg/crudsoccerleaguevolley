package com.example.crudsoccerleaguevolley;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class AddPlayersActivity extends AppCompatActivity {
    Spinner id_team, position, country;
    EditText first_name,last_name,kit;
    ArrayList<String> teamList = new ArrayList<>();
    ArrayAdapter<String> teamAdapter;
    RequestQueue requestQueueTeams;
    private static String URL_PLAYERS = "https://chestersports.000webhostapp.com/register_player.php";
    private Bitmap bitmap;
    ImageView player_image;
    Button btn_photo,btn_add_player;
    int count_images;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
       getSupportActionBar().setDisplayHomeAsUpEnabled(true);

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
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arraySpinner);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        position.setAdapter(adapter);

        country = findViewById(R.id.country);
        ArrayAdapter<String> adapterCountry = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, arrayCountries);
        adapterCountry.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        country.setAdapter(adapterCountry);

        first_name = findViewById(R.id.first_name);
        last_name = findViewById(R.id.last_name);
        kit = findViewById(R.id.kit);
        btn_add_player = findViewById(R.id.btn_add_players);
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
                        teamAdapter = new ArrayAdapter<>(AddPlayersActivity.this,android.R.layout.simple_spinner_item,teamList);
                        teamAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                        id_team.setAdapter(teamAdapter);}
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

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseFile();
            }
        });

        btn_add_player.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(validator()) {
                    register();
                }
            }
        });

    }

    private boolean validator(){
        final String first_name = this.first_name.getText().toString().trim();
        final String last_name = this.last_name.getText().toString().trim();
        final String kit = this.kit.getText().toString().trim();


        if(first_name.length() < 4){
            Toast.makeText(AddPlayersActivity.this,"FIRSTNAME MIN 4 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(first_name.length() > 20){
            Toast.makeText(AddPlayersActivity.this,"FISRTNAME MAX 20 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(last_name.length() < 4){
            Toast.makeText(AddPlayersActivity.this,"LASTNAME MIN 4 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(last_name.length() > 20){
            Toast.makeText(AddPlayersActivity.this,"LASTNAME MAX 20 chararcters !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(kit.length() > 2){
            Toast.makeText(AddPlayersActivity.this,"ONLY RANGE 1 TO 100 Permited !",Toast.LENGTH_SHORT).show();
            return false;
        }else if(kit.equals("")){
            Toast.makeText(AddPlayersActivity.this,"kit required !",Toast.LENGTH_SHORT).show();
            return false;
        }else {
            return true;
        }

    }

    private void register(){
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

        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL_PLAYERS, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressDialog.dismiss();

                try {
                    //JSONObject jsonObject = new JSONObject(response.substring(response.indexOf("{"),response.lastIndexOf("}")+1));

                    JSONObject jsonObject = new JSONObject(response);
                    String success = jsonObject.getString("success");


                    if (success.equals("1")){
                        Toast.makeText(AddPlayersActivity.this,"Player Register SUCCESS!",Toast.LENGTH_SHORT).show();
                    }if (success.equals("0")){
                        Toast.makeText(AddPlayersActivity.this,"PHP FAIL!",Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(AddPlayersActivity.this,"Player Register Exception!" + e.toString(),Toast.LENGTH_SHORT).show();

                    btn_add_player.setVisibility(View.VISIBLE);
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AddPlayersActivity.this,"ADD PICTURE!",Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();

                btn_add_player.setVisibility(View.VISIBLE);
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


                return params;
            }
        };


        //HttpsTrustManager.allowAllSSL();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),filePath);
                player_image.setImageBitmap(bitmap);
                Toast.makeText(AddPlayersActivity.this,filePath.toString(),Toast.LENGTH_LONG).show();

            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AddPlayersActivity.this,"EMPTY PICTURE FAIL",Toast.LENGTH_LONG).show();
            }



        }
    }

}