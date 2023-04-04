package com.example.phone_project.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.phone_project.Activites.DetailsActivity;
import com.example.phone_project.Model.Phone;
import com.example.phone_project.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder> {
    Activity activity;
    ArrayList<Phone> phones;

    public PhoneAdapter(Activity activity, ArrayList<Phone> phones) {
        this.activity = activity;
        this.phones = phones;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(activity).inflate(R.layout.phone_item, parent, false);
        return new MyViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {


        Picasso.get().load(phones.get(position).getImg()).into(holder.imageView);
        holder.title.setText(phones.get(position).getName());
        holder.description.setText(phones.get(position).getDescription());
        holder.quantity.setText(String.valueOf(phones.get(position).getQuantity()));
        holder.price.setText(String.valueOf(phones.get(position).getPrice()) + "$");
        Log.e("HZM", phones.get(position).getImg());


        holder.itemView.setOnClickListener(v -> {
            if (isNetworkConnected()) {

                Intent i = new Intent(activity.getApplicationContext(), DetailsActivity.class);
                i.putExtra("id",phones.get(position).getId());
                activity.startActivity(i);


            }

        });


    }

    @Override
    public int getItemCount() {
        return phones.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView title;
        public TextView description;
        public TextView price;
        public TextView quantity;


        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            imageView = itemView.findViewById(R.id.imageView);
            title = itemView.findViewById(R.id.title);
            description = itemView.findViewById(R.id.description);
            price = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);


        }
    }


    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

}

