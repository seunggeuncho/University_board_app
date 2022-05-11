package com.example.my_app_project;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

import java.io.File;

public class choose extends AppCompatActivity {
    private TextView tv_result;
    private ImageView iv_profile;
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    String nickName;
    String photoUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);

        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        photoUrl = intent.getStringExtra("photoUrl");

        tv_result = findViewById(R.id.textView);
        tv_result.setText(nickName);
        mAuth = FirebaseAuth.getInstance();

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.question_btn).setOnClickListener(onClickListener);
        findViewById(R.id.information_btn).setOnClickListener(onClickListener);

    }


    private void signOut() {
        mAuth.signOut();
    }


        View.OnClickListener onClickListener = (v) -> {
            switch (v.getId()) {
                case R.id.btn_back:
                    signOut();
                    myStartActivity(MainActivity.class);
                    break;
                case R.id.question_btn:
                    myStartActivity(Question_board.class);
                    break;
            }
        };

        private void myStartActivity (Class c){
            Intent intent = new Intent(this, c);
            intent.putExtra("nickName", nickName);
            intent.putExtra("photoUrl", photoUrl);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
        }
}