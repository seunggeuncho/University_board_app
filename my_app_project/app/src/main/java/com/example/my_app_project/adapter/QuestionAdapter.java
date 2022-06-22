package com.example.my_app_project.adapter;


import android.app.Activity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.my_app_project.R;
import com.example.my_app_project.WriteInfo;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

import listener.OnPostListener;

public class QuestionAdapter  extends RecyclerView.Adapter<QuestionAdapter.GalleryViewHolder> {
    private ArrayList<WriteInfo> mDataset;
    private Activity activity;
    private FirebaseFirestore firebaseFirestore;
    private OnPostListener onPostListener;


    static class GalleryViewHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        GalleryViewHolder(CardView v) {
            super(v);
            cardView = v;
        }
    }

    public QuestionAdapter(Activity activity, ArrayList<WriteInfo> myDataset) {
        mDataset = myDataset;
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }
    public void setOnPostListener(OnPostListener onPostListener){
        this.onPostListener = onPostListener;
    }

    @NonNull
    @Override
    public QuestionAdapter.GalleryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        final GalleryViewHolder galleryViewHolder = new GalleryViewHolder(cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        cardView.findViewById(R.id.menu).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup(view, galleryViewHolder.getAdapterPosition());
            }
        });
        return galleryViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final GalleryViewHolder holder, int position) {
        CardView cardView = holder.cardView;
        TextView textView = cardView.findViewById(R.id.titleTextView);
        textView.setText(mDataset.get(position).getTitle());

        TextView createdAtTextView = cardView.findViewById(R.id.createdAtTextView);
        createdAtTextView.setText(new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(mDataset.get(position).getCreatedAt()));

        LinearLayout contentsLayout = cardView.findViewById(R.id.contentsLayout);
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        String contents = mDataset.get(position).getContents();
        TextView TextView = new TextView(activity);
        TextView.setLayoutParams(layoutParams);
        TextView.setText(contents);
        contentsLayout.addView(TextView);

    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }

    private void showPopup(View v, int position){
        PopupMenu popup = new PopupMenu(activity, v);
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                switch (menuItem.getItemId()){
                    case R.id.modify:
                        return true;
                    case R.id.delete:

                        firebaseFirestore.collection("posts").document(mDataset.get(position).getId())
                                .delete()
                                .addOnSuccessListener(new OnSuccessListener<Void>(){
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(activity, "게시글을 삭제하였습니다.", Toast.LENGTH_LONG).show();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener(){
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(activity, "게시글을 삭제하지 못하였습니다.", Toast.LENGTH_LONG).show();
                                    }
                                });
                        return true;
                    default:
                        return false;
                }
            }
        });
        MenuInflater inflater = popup.getMenuInflater();
        inflater.inflate(R.menu.post, popup.getMenu());
        popup.show();
    }
}
