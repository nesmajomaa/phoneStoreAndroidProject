package com.example.phone_project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.phone_project.App.AppController;
import com.example.phone_project.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class DetailsActivity extends AppCompatActivity {

    TextView d_title, d_description, d_price, d_quantity;
    ImageView d_image;
    EditText q_buy;
    Button buy;
    int id;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //What to do on back clicked\
                finish();
            }
        });

        d_title = findViewById(R.id.d_title);
        d_description = findViewById(R.id.d_description);
        d_price = findViewById(R.id.d_price);
        d_quantity = findViewById(R.id.d_quantity);
        d_image = findViewById(R.id.d_image);

        q_buy = findViewById(R.id.q_buy);
        buy = findViewById(R.id.buy);

        Bundle b = getIntent().getExtras();
        if (b != null) {
            id = b.getInt("id", 0);
        }
        getJSONObject();


        buy.setOnClickListener(v->{
            if(q_buy.getText().toString().isEmpty()){

                Toast.makeText(this, "Enter Quantity", Toast.LENGTH_SHORT).show();

            }else {

                Buy(Integer.parseInt(q_buy.getText().toString()));
            }

        });



    }

    public void getJSONObject() {

        final String URL = "https://Mobile-Store-01.user10694.repl.co/api/get?id=" + id;

        JsonObjectRequest objectRequest = new JsonObjectRequest(Request.Method.GET, URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.e("hzm", response.toString());
                        try {
                            int id = response.getInt("id");
                            String name = response.getString("name");
                            String description = response.getString("description");
                            int quantity = response.getInt("quantity");
                            int price = response.getInt("price");
                            String img = response.getString("img");

                            Picasso.get().load(img).into(d_image);
                            d_title.setText(name);
                            d_description.setText(description);
                            d_quantity.setText(String.valueOf(quantity));
                            d_price.setText(String.valueOf(price));


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hzm", error.getMessage());
            }
        });

        AppController.getInstance().addToRequestQueue(objectRequest);
    }


    public void Buy(int quantity) {
showDialog();
        if (quantity > Integer.parseInt(d_quantity.getText().toString())) {

            Toast.makeText(this, "No Enough Quantity", Toast.LENGTH_SHORT).show();
            return;
        }
        if (quantity <= 0) {
            Toast.makeText(this, "Enter Valid Quantity", Toast.LENGTH_SHORT).show();
            return;
        }

      int q =  Integer.parseInt(d_quantity.getText().toString());

        quantity = q-quantity;

        Log.e("hzm", quantity+"");
        final String URL2 = "https://Mobile-Store-01.user10694.repl.co/api/set?id=" + id + "&q=" + quantity;

        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL2,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                        Log.e("HUN", response.toString());
                        getJSONObject();
                        q_buy.setText("");

                hideDialog();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("hzm", error.getMessage());
                hideDialog();
            }
        });

        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(DetailsActivity.this);
        progressDialog.setMessage("Buying ......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }
}