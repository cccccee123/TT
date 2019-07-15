package com.example.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class Insert extends AppCompatActivity {
    EditText topic,content,email;
    Button send;
    DatabaseReference reference;
    Contact contact;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_insert);



        Toast.makeText(Insert.this,"Connect Success",Toast.LENGTH_LONG).show();

        email=findViewById(R.id.email);
        topic=findViewById(R.id.topic);
        content=findViewById(R.id.content);
        send=findViewById(R.id.send);
        reference= FirebaseDatabase.getInstance().getReference().child("Contact");
        contact=new Contact();

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contact.setEmail(email.getText().toString().trim());
               contact.setTopic(topic.getText().toString().trim());
                contact.setContent(content.getText().toString().trim());

               reference.child("contact").setValue(contact);
                Toast.makeText(Insert.this,"Data Insert Success",Toast.LENGTH_LONG).show();


            }
        });

    }
}
