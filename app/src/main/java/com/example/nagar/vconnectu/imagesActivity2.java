package com.example.nagar.vconnectu;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class imagesActivity2 extends AppCompatActivity {

    private RecyclerView mrecyclerView;
    private imageAdapter1 adapter;

    private ProgressBar mProgressCricle;

    private DatabaseReference mDatabaseRef;
    private List<Upload1> muploads;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images2);

        mrecyclerView = findViewById(R.id.recyclerView);
        mProgressCricle = findViewById(R.id.progress_circle);
        mrecyclerView.setHasFixedSize(true);
        mrecyclerView.setLayoutManager(new LinearLayoutManager(this));

        muploads= new ArrayList<>();

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("lost");
        mDatabaseRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                for(DataSnapshot postSnapshot : dataSnapshot.getChildren())
                {
                    Upload1 upload = postSnapshot.getValue(Upload1.class);
                    muploads.add(upload);

                }

                adapter = new imageAdapter1(imagesActivity2.this,muploads);
                mrecyclerView.setAdapter(adapter);
                mProgressCricle.setVisibility(View.INVISIBLE);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

                Toast.makeText(imagesActivity2.this, databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                mProgressCricle.setVisibility(View.INVISIBLE);
            }
        });


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
