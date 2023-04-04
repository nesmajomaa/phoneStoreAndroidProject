package com.example.phone_project.Activites;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.phone_project.Adapter.PhoneAdapter;
import com.example.phone_project.App.AppController;
import com.example.phone_project.DB.DataAccess;
import com.example.phone_project.Model.Phone;
import com.example.phone_project.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ProgressDialog progressDialog;
    ProgressBar progressBar;
    RecyclerView recyclerView;
    final String URL = "https://Mobile-Store-01.user10694.repl.co/api/get";

    ArrayList<Phone> data;

    PhoneAdapter phoneAdapter;
    DataAccess dataAccess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar1);
        setSupportActionBar(toolbar);

        // add back arrow to toolbar
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }

        progressBar = findViewById(R.id.progress);
        recyclerView = findViewById(R.id.recyclerView);

        dataAccess = new DataAccess(this);

        data = new ArrayList<>();
        if (isNetworkConnected()) {
            getJSONArray(false);
            phoneAdapter = new PhoneAdapter(this, data);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(phoneAdapter);
        } else {
            ArrayList<Phone> DBphones = new ArrayList<>();
            DBphones = dataAccess.getAllPhones();

            phoneAdapter = new PhoneAdapter(this, DBphones);
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(phoneAdapter);

            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }



    public void getJSONArray(boolean type) {

//        showDialog();
        recyclerView.setVisibility(View.GONE);
        progressBar.setVisibility(View.VISIBLE);
        JsonArrayRequest arrayRequest = new JsonArrayRequest(Request.Method.GET, URL,
                null, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                Log.e("hzm", response.toString());
                dataAccess.deleteStudent();
                for (int i = 0; i < response.length(); i++) {
                    try {
                        JSONObject jsonObject = response.getJSONObject(i);
                        int id = jsonObject.getInt("id");
                        String name = jsonObject.getString("name");
                        String description = jsonObject.getString("description");
                        int quantity = jsonObject.getInt("quantity");
                        double price = jsonObject.getDouble("price");
                        String img = jsonObject.getString("img");
                        data.add(new Phone(id, name, description, quantity, price, img));

                        dataAccess.insertStudent(id, name, description, quantity,price ,img);


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
                if (type) {
                    Toast.makeText(getApplicationContext(), "synchronous data Successfully", Toast.LENGTH_SHORT).show();

                }


                progressBar.setVisibility(View.INVISIBLE);
                recyclerView.setVisibility(View.VISIBLE);

                Log.e("hzm", String.valueOf(data.size()));
//                hideDialog();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(), error.getMessage()+"", Toast.LENGTH_SHORT).show();

                Log.e("hzm", error.getMessage()+"");
//                hideDialog();
                progressBar.setVisibility(View.INVISIBLE);
            }
        });

        AppController.getInstance().addToRequestQueue(arrayRequest);
    }

    @Override
    protected void onResume() {
        super.onResume();
        data = new ArrayList<>();
        if (isNetworkConnected()) {
            getJSONArray(false);
            phoneAdapter = new PhoneAdapter(this, data);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(phoneAdapter);
        } else {
            ArrayList<Phone> DBphones = new ArrayList<>();
            DBphones = dataAccess.getAllPhones();

            phoneAdapter = new PhoneAdapter(this, DBphones);
            progressBar.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(linearLayoutManager);
            recyclerView.setAdapter(phoneAdapter);

            Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
        }

    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    public void showDialog() {
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading ......");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    public void hideDialog() {
        if (progressDialog.isShowing())
            progressDialog.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.sync:
                data = new ArrayList<>();
                if (isNetworkConnected()) {
                    getJSONArray(true);
                    phoneAdapter = new PhoneAdapter(this, data);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(phoneAdapter);
                } else {
                    ArrayList<Phone> DBphones = new ArrayList<>();
                    DBphones = dataAccess.getAllPhones();

                    phoneAdapter = new PhoneAdapter(this, DBphones);
                    progressBar.setVisibility(View.INVISIBLE);
                    recyclerView.setVisibility(View.VISIBLE);
                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
                    recyclerView.setLayoutManager(linearLayoutManager);
                    recyclerView.setAdapter(phoneAdapter);

                    Toast.makeText(getApplicationContext(), "No Internet Connection !", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
}