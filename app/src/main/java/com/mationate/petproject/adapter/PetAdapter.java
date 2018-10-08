package com.mationate.petproject.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mationate.petproject.R;
import com.mationate.petproject.models.Pet;
import com.squareup.picasso.Picasso;


public class PetAdapter extends FirebaseRecyclerAdapter<Pet, PetAdapter.ViewHolder>{

    private PetListener listener;

    public PetAdapter(@NonNull FirebaseRecyclerOptions<Pet> options, PetListener listener ) {
        super(options);
        this.listener = listener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_card_item_pets, parent, false);
        return new ViewHolder (view);
    }

    @Override
    protected void onBindViewHolder(@NonNull final ViewHolder holder, int position, @NonNull Pet model) {

        holder.nameTv.setText(model.getName());
        Picasso.with(holder.itemView.getContext())
                .load(model.getPreviews()).into(holder.photoIv);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pet auxPet = getItem(holder.getAdapterPosition());
                listener.clicked(auxPet);
            }
        });


    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView photoIv;
        private TextView nameTv;

        public ViewHolder(View itemView) {
            super(itemView);

            photoIv = itemView.findViewById(R.id.photoIv);
            nameTv = itemView.findViewById(R.id.nameTv);
        }
    }
}
