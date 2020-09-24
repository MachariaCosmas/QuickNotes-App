package com.example.quicknotes;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    RecyclerView recyclerView;
    FloatingActionButton add_button;
    ImageView empty_imageview;
    TextView no_data;

    MyDatabaseHelper myDB;
    ArrayList<String> note_id,note_category,note_title,note_content;
    CustomAdapter customAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView=findViewById(R.id.recyclerView);
        add_button = findViewById(R.id.note_add);
        empty_imageview=findViewById(R.id.empty_imageview);
        no_data=findViewById(R.id.no_data);

        add_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent=new Intent(MainActivity.this,AddActivity.class);
               startActivity(intent);
            }
        });

        myDB=new MyDatabaseHelper(MainActivity.this);
        note_id=new ArrayList<>();
        note_category=new ArrayList<>();
        note_title=new ArrayList<>();
        note_content=new ArrayList<>();

        storeDataInArrays();

        customAdapter = new CustomAdapter(MainActivity.this,note_id,note_category,note_title,note_content);
        recyclerView.setAdapter(customAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

    }
void storeDataInArrays(){
        Cursor cursor=myDB.readAllData();
        if(cursor.getCount() == 0){
            empty_imageview.setVisibility(View.VISIBLE);
            no_data.setVisibility(View.VISIBLE);
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }else {
            while(cursor.moveToNext()){
                note_id.add(cursor.getString(0));
                note_category.add(cursor.getString(1));
                note_title.add(cursor.getString(2));
                note_content.add(cursor.getString(3));
            }
           empty_imageview.setVisibility(View.GONE);
            no_data.setVisibility(View.GONE);
        }
}


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.action_delete){
            confirmDialog();

        }

        return super.onOptionsItemSelected(item);
    }


    void confirmDialog(){
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Delete All?");
        builder.setMessage("Are you sure you want to delete all data?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                MyDatabaseHelper myDB=new MyDatabaseHelper(MainActivity.this);
                myDB.deleteAllData();
                Intent intent=new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
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


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = -1;
        try {
            position = customAdapter.getPosition();
        } catch (Exception e) {
//            Log.d(TAG, e.getLocalizedMessage(), e);
            //return super.onContextItemSelected(item);
        }
        switch (item.getItemId()) {
            case 121:
                // do your stuff
                Toast.makeText(this, "Set Reminder", Toast.LENGTH_SHORT).show();
                return  true;
            case 122:
                Toast.makeText(this, "Delete", Toast.LENGTH_SHORT).show();

                return true;
                default:
                    return super.onContextItemSelected(item);
        }

    }
}
