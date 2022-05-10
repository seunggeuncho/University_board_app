package com.example.my_app_project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.my_app_project.adapter.QuestionAdapter;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Date;

public class first_board extends AppCompatActivity {
    private static final String TAG = "first_board";
    private TextView tv_result;
    private ImageView iv_profile;
    private FirebaseAuth mAuth;
    private GoogleApiClient mActivity;
    private boolean isLoggingOut = false;
    FirebaseFirestore db;
    String nickName;
    String photoUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_board);


        Intent intent = getIntent();
        nickName = intent.getStringExtra("nickName");
        photoUrl = intent.getStringExtra("photoUrl");

        tv_result = findViewById(R.id.textView);
        tv_result.setText(nickName);
        mAuth = FirebaseAuth.getInstance();

        iv_profile = findViewById(R.id.iv_profile);
        Glide.with(this).load(photoUrl).into(iv_profile);
        findViewById(R.id.btn_back).setOnClickListener(onClickListener);
        findViewById(R.id.floatingActionButton2).setOnClickListener(onClickListener);
        FirebaseFirestore db = FirebaseFirestore.getInstance();




        db.collection("posts")
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if(task.isSuccessful()) {
                            ArrayList<WriteInfo>writeInfo = new ArrayList<>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + "=> " + document.getData());
                                writeInfo.add(new WriteInfo(
                                        document.getData().get("title").toString(),
                                        (String) document.getData().get("contents"),
                                        document.getData().get("publisher").toString(),
                                        new Date(document.getDate("createdAt").getTime())));
                            }
                            RecyclerView recyclerView = findViewById(R.id.recycleView);
                            recyclerView.setHasFixedSize(true);
                            recyclerView.setLayoutManager(new LinearLayoutManager(first_board.this));

                            RecyclerView.Adapter mAdapter = new QuestionAdapter(first_board.this,writeInfo);
                            recyclerView.setAdapter(mAdapter);
                        }else {
                            Log.d(TAG, "Error getting documents: ", task.getException());
                        }
                    }
                });


    }

    View.OnClickListener onClickListener = (v) -> {
        switch(v.getId()){
            case R.id.btn_back:
                myStartActivity(choose.class);
                break;
            case R.id.floatingActionButton2:
                myStartActivity(WriteQuestion.class);
                break;
        }
    };

   private void myStartActivity(Class c){
       Intent intent = new Intent(this,c);
       intent.putExtra("nickName", nickName);
       intent.putExtra("photoUrl", photoUrl);
       intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
       startActivity(intent);
   }
}