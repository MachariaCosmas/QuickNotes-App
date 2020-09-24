package com.example.quicknotes;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class UpdateActivity extends AppCompatActivity {
    private static final String CHANNEL_ID = "some_channel_id";
    EditText category_input,title_input,content_input;
    Button update_button,delete_button;
    String id,category,title,content;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        category_input=findViewById(R.id.category_input2);
        title_input=findViewById(R.id.title_input2);
        content_input=findViewById(R.id.content_input2);
        update_button=findViewById(R.id.update_button);
        delete_button=findViewById(R.id.delete_button);

        //first we call this
        getAndSetIntentData();

        //set actionBar title after getAndSetIntentData
        ActionBar ab=getSupportActionBar();
        ab.setTitle(title);

        update_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB=new MyDatabaseHelper(UpdateActivity.this);
                category=category_input.getText().toString().trim();
                title=title_input.getText().toString().trim();
                content=content_input.getText().toString().trim();

                myDB.updateData(id,category,title,content);
            }
        });

        delete_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                confirmDialog();;
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.menu_update,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_Set_reminder:
                //createNotification("Sample Notification","Sample message");

            case R.id.action_delete_menu:
                Toast.makeText(this, " Delete Action", Toast.LENGTH_SHORT).show();


        }
        return super.onOptionsItemSelected(item);
    }


    void getAndSetIntentData(){
        if(
                getIntent().hasExtra("id") &&
                getIntent().hasExtra("category") &&
                getIntent().hasExtra("title") &&
                getIntent().hasExtra("content")){
            //getting data from the intent
            id=getIntent().getStringExtra("id");
            category=getIntent().getStringExtra("category");
            title=getIntent().getStringExtra("title");
            content=getIntent().getStringExtra("content");

            //setting intent data
            category_input.setText(category);
            title_input.setText(title);
            content_input.setText(content);


        }
        else {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }

    }


    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete " + title + "?");
        builder.setMessage("Are you sure you want to delete " +title + "?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB=new MyDatabaseHelper(UpdateActivity.this);
                myDB.deleteOneRow(id);
                finish();
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();
    }
}
