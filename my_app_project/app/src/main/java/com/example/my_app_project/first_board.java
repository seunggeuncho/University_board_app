package com.example.my_app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;

public class first_board extends AppCompatActivity {
    private TextView tv_result;
    private ImageView iv_profile;
    private FirebaseAuth mAuth;
    private GoogleApiClient mActivity;
    private boolean isLoggingOut = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_board);


        Intent intent = getIntent();
        String nickName = intent.getStringExtra("nickName");
        String photoUrl = intent.getStringExtra("photoUrl");

        tv_result = findViewById(R.id.textView);
        tv_result.setText(nickName);
        mAuth = FirebaseAuth.getInstance();

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile);
        findViewById(R.id.btn_logout).setOnClickListener(onClickListener);
        findViewById(R.id.button7).setOnClickListener(onClickListener);
    }
    private void signOut() {
        if (mActivity.isConnected()) {
            // Google sign out
            Auth.GoogleSignInApi.signOut(mActivity);
        }
    }


    View.OnClickListener onClickListener = (v) -> {
        switch(v.getId()){
            case R.id.btn_logout:
                signOut();
                break;
            case R.id.button7:
                myStartActivity(WriteQuestion.class);
                break;
        }
    };

   private void myStartActivity(Class c){
       Intent intent = new Intent(this,c);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent);
   }
}