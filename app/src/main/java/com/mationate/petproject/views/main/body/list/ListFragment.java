package com.mationate.petproject.views.main.body.list;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.Query;
import com.mationate.petproject.R;
import com.mationate.petproject.adapter.main.PetAdapter;
import com.mationate.petproject.adapter.main.PetListener;
import com.mationate.petproject.data.Nodes;
import com.mationate.petproject.models.Pet;
import com.mationate.petproject.views.details.DetailsActivity;

import static com.mationate.petproject.views.main.MainActivity.PET_KEY;


public class ListFragment extends Fragment implements PetListener {


    private RecyclerView rv;

    public ListFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rv = view.findViewById(R.id.listFragmentRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);

        FirebaseRecyclerOptions<Pet> options = getOptions(new Nodes().petList());
        PetAdapter adapter = new PetAdapter(options,this);
        rv.setAdapter(adapter);

    }

    @Override
    public void clicked(Pet pet) {
        Intent intent = new Intent(getContext(), DetailsActivity.class);
        intent.putExtra(PET_KEY, pet);
        startActivity(intent);
    }

    public void setSelection(String zone) {
        Log.d("SELECTION_FRAG", zone);
        FirebaseRecyclerOptions<Pet> options = getOptions(
                new Nodes().petList().orderByChild("zone").equalTo(zone)
        );
        rv.setAdapter(null);
        rv.setAdapter(new PetAdapter(options, this));


    }

    private FirebaseRecyclerOptions<Pet> getOptions(Query query) {
        return new FirebaseRecyclerOptions.Builder<Pet>()
                .setQuery(query, Pet.class)
                .setLifecycleOwner(this)
                .build();
    }
}
