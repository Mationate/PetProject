package com.mationate.petproject.views.main;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mationate.petproject.R;
import com.mationate.petproject.views.login.LoginActivity;
import com.mationate.petproject.views.main.body.list.ListFragment;
import com.mationate.petproject.views.main.body.menu.MenuFragment;
import com.mationate.petproject.views.main.form.StepperActivity;


public class MainActivity extends AppCompatActivity implements MenuFragment.Callback {

public final static String PET_KEY = "com.mationate.petproject.views.main.MainActivity.key.PET_KEY";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(MainActivity.this, StepperActivity.class);
                startActivity(intent);

            }
        });

/*
        RecyclerView rv = findViewById(R.id.listFragmentRv);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, true);
        linearLayoutManager.setStackFromEnd(true);
        rv.setLayoutManager(linearLayoutManager);
        rv.setHasFixedSize(true);

        FirebaseRecyclerOptions<Pet> options = new FirebaseRecyclerOptions.Builder<Pet>()
                .setQuery(new Nodes().petList(), Pet.class)
                .setLifecycleOwner(this)
                .build();
        PetAdapter adapter = new PetAdapter(options, this);
        rv.setAdapter(adapter);
*/

        //rv.setAdapter(null);
        //new Nodes().petList().orderByChild("zone").equalTo(zone)
        //TODO remove previous adapter, set new adapter with updated zone

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_tabbed, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_logout:

                AuthUI.getInstance()
                        .signOut(this)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void selection(String zone) {
        Log.d("SELECTION_MAIN", zone);
        ListFragment fragment = (ListFragment) getSupportFragmentManager().findFragmentById(R.id.listFragment);
        fragment.setSelection(zone);

    }
}
