package com.example.quicknotes;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class AddActivity extends AppCompatActivity {
    EditText category_input,title_input,content_input;
    Button button_add;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        category_input=findViewById(R.id.category_input);
        title_input=findViewById(R.id.title_input);
        content_input=findViewById(R.id.content_input);

        button_add=(Button)findViewById(R.id.add_button);
        button_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MyDatabaseHelper myDB=new MyDatabaseHelper(AddActivity.this);
                myDB.addNote(category_input.getText().toString().trim(),
                        title_input.getText().toString().trim(),
                        content_input.getText().toString().trim());
            }
        });

    }
}
