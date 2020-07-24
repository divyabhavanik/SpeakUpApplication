package com.bhavani.divya.sharadhacomplaints;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private LinearLayoutManager linearLayoutManager;
    private FirebaseRecyclerAdapter adapter;
    String person;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Complaints");
        getSupportActionBar().setTitle("Complaints");
        recyclerView = findViewById(R.id.list);
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setReverseLayout(true);
        linearLayoutManager.setStackFromEnd(true);


        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setHasFixedSize(true);
        fetch();
        Intent i = getIntent();
        person = i.getStringExtra("user");

        Toast.makeText(this, person, Toast.LENGTH_SHORT).show();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        if (person.equals("warden")) {
            fab.hide();
        }
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(HomeActivity.this, NewComplaint.class);
                startActivity(i);
            }
        });
    }

    @Override
    public void onBackPressed(){
        startActivity(new Intent(HomeActivity.this,LoginPage.class));
    }


    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }
    private void fetch() {
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("complaints");

        FirebaseRecyclerOptions<Model> options =
                new FirebaseRecyclerOptions.Builder<Model>()
                        .setQuery(query, new SnapshotParser<Model>() {
                            @NonNull
                            @Override
                            public Model parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Model(snapshot.child("id").getValue().toString(), snapshot.child("name").getValue().toString(), snapshot.child("complaint").getValue().toString(),snapshot.child("roomno").getValue().toString(),snapshot.child("solved").getValue().equals(true));
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Model, ViewHolder>(options) {
            @Override
            public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_view, parent, false);

                return new ViewHolder(view);
            }


            @Override
            protected void onBindViewHolder(final ViewHolder holder, final int position, final Model model) {
                holder.setIsRecyclable(false);
                holder.setTxtTitle(model.getmName());
                holder.setTxtDesc(model.getmComplaint());
                holder.setTxtRoom("Room No: "+model.getmRoom());
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Toast.makeText(HomeActivity.this,model.getmName(), Toast.LENGTH_SHORT).show();
                    }
                });

                if (person.equals("Student")) {
                    holder.Solved.setVisibility(View.INVISIBLE);
                }
                final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("complaints").child(model.getmId());
                databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        Boolean solved = (Boolean) dataSnapshot.child("solved").getValue();

                        if(solved){
                            holder.solved_image.setImageDrawable(getDrawable(R.drawable.solved));
                            holder.Solved.setVisibility(View.INVISIBLE);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
                holder.Solved.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {



                            AlertDialog.Builder builder1 = new AlertDialog.Builder(HomeActivity.this);
                            builder1.setMessage("Are you sure the complaint is solved?");
                            builder1.setCancelable(true);

                            builder1.setPositiveButton(
                                    "Yes",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            databaseReference.child("solved").setValue(true);
                                            holder.solved_image.setVisibility(View.VISIBLE);
                                            Toast.makeText(HomeActivity.this, "Complaint Resolved", Toast.LENGTH_SHORT).show();
                                            dialog.cancel();
                                            holder.Solved.setVisibility(View.INVISIBLE);
                                        }
                                    });

                            builder1.setNegativeButton(
                                    "No",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int id) {
                                            dialog.cancel();
                                        }
                                    });

                            AlertDialog alert11 = builder1.create();
                            alert11.show();

                    }
                });



            }

        };
        recyclerView.setAdapter(adapter);
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout root;
        public TextView txtName;
        public TextView txtRoom;
        public TextView txtComplaint;
        public Button Solved;
        public ImageView solved_image;
        public ViewHolder(View itemView) {
            super(itemView);
            root = itemView.findViewById(R.id.list_root);
            txtName = itemView.findViewById(R.id.list_name);
            txtRoom = itemView.findViewById(R.id.list_room);
            txtComplaint = itemView.findViewById(R.id.list_complaint);
            Solved =itemView.findViewById(R.id.checkbox);
            solved_image =itemView.findViewById(R.id.solved);



        }

        public void setTxtTitle(String string) {
            txtName.setText(string);
        }


        public void setTxtDesc(String string) {
            txtComplaint.setText(string);
        }

        public void setTxtRoom(String string) {
            txtRoom.setText(string);
        }

    }
}
