package edu.lwtech.finalproject;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;
    private Button logout;
    Button buttonClick;
    EditText searchItem;
    public static TextView data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        buttonClick = findViewById(R.id.buttonClick);
        searchItem = findViewById(R.id.textSearch);
        //data = findViewById(R.id.textView);


        buttonClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String toSearch = searchItem.getText().toString();
                Intent intent = new Intent(SecondActivity.this, ResultActivity.class);
                intent.putExtra("searchItem",toSearch);
                startActivity(intent);
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();


    }

    private void logout()
    {

        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.logoutMenu:
            {
                logout();
            }
        }
        return super.onOptionsItemSelected(item);
    }

}
