package com.example.said.pollingzone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.graphics.Color;

import com.bumptech.glide.Glide;

import org.w3c.dom.Text;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";
    private ArrayList<String> mImageNames = new ArrayList<>();
    private ArrayList<String> mImages = new ArrayList<>();
    private Context mContent;
    public static int lastClicked = -1;

    public RecyclerViewAdapter(ArrayList<String> mImages, ArrayList<String> mImagesNames, Context mContent) {
        this.mImages = mImages;
        this.mContent = mContent;
        this.mImageNames = mImagesNames;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_listitem, viewGroup, false );
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull  ViewHolder viewHolder, final int i) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContent).asBitmap().load(mImages.get(i)).into(viewHolder.image);

        viewHolder.imageName.setText(mImageNames.get(i));

        if(lastClicked == i)
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#008577"));
        else
            viewHolder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        viewHolder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d(TAG,"onClick: clicked on: " + mImageNames.get(i));
                lastClicked = i;
                notifyDataSetChanged();
                Toast.makeText(mContent, mImageNames.get(i), Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return mImageNames.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView image;
        TextView imageName;
        RelativeLayout parentLayout;

        public ViewHolder(View itemView){
            super(itemView);
            image = itemView.findViewById(R.id.image);
            imageName = itemView.findViewById(R.id.image_name);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }

    }
}
