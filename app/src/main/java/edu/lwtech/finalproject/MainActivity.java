package edu.lwtech.finalproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {

    private EditText name;
    private EditText password;
    private TextView info;
    private Button login;
    private TextView register;
    private int counter =5;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name = findViewById(R.id.etName);
        password = findViewById(R.id.etPassword);
        info = findViewById(R.id.tvInfo);
        login = findViewById(R.id.btnLogin);
        register = findViewById(R.id.tvRegister);

        info.setText("No of attempts remaining: 5");
        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();



        if(user != null)
        {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        login.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {
                validate(name.getText().toString(),password.getText().toString());
            }
        });

        register.setOnClickListener(new View.OnClickListener()
        {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

    }

    private void validate(String userName, String userPassword)
    {

        progressDialog.setMessage("Processing");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
           if(task.isSuccessful())
           {
               progressDialog.dismiss();
               Toast.makeText(MainActivity.this,"Login Successful",Toast.LENGTH_SHORT).show();
               Intent intent = new Intent(MainActivity.this, SecondActivity.class);
               startActivity(intent);
           }
           else{
               Toast.makeText(MainActivity.this,"Login Failed",Toast.LENGTH_SHORT).show();
               counter--;
               info.setText("No of attempts remaining"+counter);
               progressDialog.dismiss();
               if(counter == 0)
               {
                   login.setEnabled(false);
               }
           }
            }
        });




    }
}
