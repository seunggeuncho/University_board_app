package com.example.my_app_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class first_board extends AppCompatActivity {
    private TextView tv_result;
    private ImageView iv_profile;
    Button btnLogout;
    private FirebaseAuth mAuth;
    GoogleSignInClient mGoogleSignClient;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_board);


        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName");
        String photoUrl = intent.getStringExtra("photoUrl");

        tv_result = findViewById(R.id.textView);
        tv_result.setText(nickName);
        btnLogout = (Button) findViewById(R.id.btn_logout);
        mAuth = FirebaseAuth.getInstance();

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile);
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signOut();
                finishAffinity();
            }
        });
    }

    private void signOut() {
        mGoogleSignClient.signOut()
                .addOnCompleteListener(this, task -> {
                    mGoogleSignClient.revokeAccess()
                            .addOnCompleteListener(this, task1 -> Log.d("first_board", "onClick:revokeAccess success "));

                });
    }
}