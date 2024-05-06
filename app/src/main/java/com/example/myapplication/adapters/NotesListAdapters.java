package com.example.myapplication.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.myapplication.NotesClickListener;
import com.example.myapplication.R;
import com.example.myapplication.models.Notes;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class NotesListAdapters extends RecyclerView.Adapter<NotesViewHolder> {

Context context;
List<Notes> list;
NotesClickListener listener;

    public NotesListAdapters(Context context, List<Notes> list, NotesClickListener listener) {
        this.context = context;
        this.list = list;
        this.listener = listener;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new NotesViewHolder(LayoutInflater.from(context).inflate(R.layout.note_list,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder holder, int position) {
            holder.textview_title.setText(list.get(position).getTitle());
            holder.textview_title.setSelected(true);

            holder.textview_notes.setText(list.get(position).getNotes());

            holder.textview_date.setText(list.get(position).getDate());
            holder.textview_date.setSelected(true);

            if(list.get(position).isPinned()){
                holder.imageview_pin.setImageResource(R.drawable.ic_pin);
            }
            else {
                if(holder.imageview_pin != null) {
                    // Now you can safely set the image resource
                    holder.imageview_pin.setImageResource(0);
                }
            }

            int color_code =getRandomColor();
            holder.note_container.setCardBackgroundColor(holder.itemView.getResources().getColor(color_code,null));


            holder.note_container.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onClick(list.get(holder.getAdapterPosition()));
                }
            });

            holder.note_container.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    listener.onLongClick(list.get(holder.getAdapterPosition()),holder.note_container);
                    return true;
                }
            });
    }
    private int getRandomColor(){
        List<Integer> colorCode = new ArrayList<>();
        colorCode.add(R.color.light_blue);
        colorCode.add(R.color.aqua);
        colorCode.add(R.color.olive);
        colorCode.add(R.color.yellow);
        colorCode.add(R.color.khaki);
        colorCode.add(R.color.gray);

        Random random = new Random();
        int random_colors = random.nextInt(colorCode.size());
        return colorCode.get(random_colors);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public void filterList(List<Notes> filteredList){
        list = filteredList;
        notifyDataSetChanged();
    }
}

class NotesViewHolder extends RecyclerView.ViewHolder{

    CardView note_container;
    TextView textview_title,textview_date,textview_notes;
    ImageView imageview_pin;

    public NotesViewHolder(@NonNull View itemView) {
        super(itemView);

        note_container = itemView.findViewById(R.id.note_container);
        textview_title = itemView.findViewById(R.id.textview_title);
        textview_date = itemView.findViewById(R.id.textview_date);
        textview_notes = itemView.findViewById(R.id.textview_notes);

    }
}
