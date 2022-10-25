package com.example.aseproject;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {


    private FirebaseAuth mAuth;
    private FirebaseFirestore mFirestore;
    private String mCurrentId, mUserId;
    private String TAG = "TAG";
    private EditText editTextEmail, editTextPassword;
    private TextView signInButton;
    private TextView status;
    private TextView createAccountView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ininUI();
        initObjects();
        initListeners();
        signInTrack();
    }

    private void ininUI() {
        editTextEmail = (EditText) findViewById(R.id.emailField);
        editTextPassword = (EditText) findViewById(R.id.passwordField);
        signInButton = (TextView) findViewById(R.id.signInButton);
        createAccountView = (TextView) findViewById(R.id.createAccountView);
    }

    private void initObjects() {
        mFirestore = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
    }

    private void signInTrack() {
        if (mAuth.getCurrentUser() != null) {
            Toast.makeText(MainActivity.this, "", Toast.LENGTH_LONG);
            startActivity(new Intent(MainActivity.this, UserHomepage.class));
            finish();
        }
    }

    private void initListeners() {
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signUserIn();
            }
        });

        createAccountView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createAccountActivity();
            }
        });
    }

    private void createAccountActivity() {
        Intent myIntent = new Intent(MainActivity.this, CreateAccount.class);
        startActivity(myIntent);
    }

    private void signUserIn() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();
        mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    startActivity(new Intent(MainActivity.this, UserHomepage.class));
                    return;
                } else {
                    Toast.makeText(MainActivity.this, "Signed In Failed", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
