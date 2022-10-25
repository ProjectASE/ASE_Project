package com.example.aseproject.events.model;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;


import com.example.aseproject.R;
import com.example.aseproject.events.EventDetails;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    List<String> titles;
    List<String> descriptions;

    public Adapter(List<String> title,List<String> description){
        this.titles = title;
        this.descriptions = description;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.event_view_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.eventTitle.setText(titles.get(position));
        holder.eventDescription.setText(descriptions.get(position));
        final int code = getRandomColor();
        holder.eCardView.setCardBackgroundColor(holder.view.getResources().getColor(code,null));

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(v.getContext(), EventDetails.class);
                i.putExtra("title",titles.get(position));
                i.putExtra("content",descriptions.get(position));
                i.putExtra("code",code);
                v.getContext().startActivity(i);
            }
        });
    }

    private int getRandomColor() {

        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.blue);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.skyblue);
        colorCode.add(R.color.purple);
        colorCode.add(R.color.red);
        colorCode.add(R.color.green);

        Random randomColor = new Random();
        int number = randomColor.nextInt(colorCode.size());
        return colorCode.get(number);

    }

    @Override
    public int getItemCount() {
        return titles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView eventTitle,eventDescription;
        View view;
        CardView eCardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            eventTitle = itemView.findViewById(R.id.titles);
            eventDescription = itemView.findViewById(R.id.description);
            eCardView = itemView.findViewById(R.id.eventCard);
            view = itemView;
        }
    }
}
