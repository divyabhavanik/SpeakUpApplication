package com.bhavani.divya.sharadhacomplaints;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class NewComplaint extends AppCompatActivity {

    EditText name,roomno,complaint;
    Button submit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_complaint);
        name=findViewById(R.id.name);
        roomno=findViewById(R.id.roomno);
        complaint=findViewById(R.id.complaint);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("complaints").push();
                Map<String, Object> map = new HashMap<>();
                map.put("id", databaseReference.getKey());
                map.put("name", name.getText().toString());
                map.put("roomno",roomno.getText().toString());
                map.put("complaint", complaint.getText().toString());
                map.put("solved",false);

                databaseReference.setValue(map);
                Intent i=new Intent(NewComplaint.this,HomeActivity.class);
                i.putExtra("user","Student");
                startActivity(i);
            }
        });

    }
}
