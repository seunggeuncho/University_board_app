package com.example.my_app_project;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;

public class WriteQuestion extends AppCompatActivity {
    private static final String TAG = "WriteQuestion";
    private FirebaseUser user;
    String nickName;
    String photoUrl;
    Integer check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write_question);

        findViewById(R.id.check).setOnClickListener(onClickListener);
        findViewById(R.id.back_btn).setOnClickListener(onClickListener);
        check = 0;
        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        photoUrl = intent.getStringExtra("photoUrl");

    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.check:
                    profileUpdate();
                    if (check == 1) {
                        backActivity();
                    }
                    break;
                case R.id.back_btn:
                    backActivity();
            }
        }
    };
    private void profileUpdate() {
        final String title = ((EditText) findViewById(R.id.titleEditText)).getText().toString();
        final String contents = ((EditText) findViewById(R.id.contentsEditeTExt)).getText().toString();

        if (title.length() > 0 && contents.length() > 0) {
            user = FirebaseAuth.getInstance().getCurrentUser();
            WriteInfo writeinfo = new WriteInfo(title, contents, user.getUid(), new Date());
            check = 1;
            uploader(writeinfo);
        } else {
            startToast("내용을 입력해주세요");
        }
    }

    private void uploader(WriteInfo writeInfo) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("posts").add(writeInfo)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }


    private void startToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_LONG).show();
    }
    private void backActivity(){
        Intent intent = new Intent(this,Question_board.class);
        intent.putExtra("nickName", nickName);
        intent.putExtra("photoUrl", photoUrl);
        startActivity(intent);
    }

}