package com.example.public_art;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class WorkRecyclerView extends ConstraintLayout {
    public WorkRecyclerView(@NonNull Context context) {
        super(context);
        init(context);
    }

    public WorkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public WorkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    public WorkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context context) {
        inflate(context, R.layout.work_recycler, this);
        // 開啟檔案

        try {
            InputStream inputStream = context.getAssets().open("1館.json");
            // 讀取檔案的全部位元組
            byte[] bytes = new byte[inputStream.available()];
            inputStream.read(bytes);

            // 將位元組陣列轉換為字串
            String text = new String(bytes);

            WorkData[] workData;

            workData = new Gson().fromJson(text, WorkData[].class);
            WorkRecyclerAdapter workRecyclerAdapter = new WorkRecyclerAdapter(context, workData);
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
            linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

            RecyclerView workRv = (RecyclerView)findViewById(R.id.work_rv);
            workRv.setLayoutManager(linearLayoutManager);
            workRv.setAdapter(workRecyclerAdapter);
            workRv.setOverScrollMode(View.OVER_SCROLL_NEVER);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }



    }
}

class WorkRecyclerAdapter extends RecyclerView.Adapter<WorkRecyclerAdapter.WorkViewHolder>{
    Context context;
    WorkData[] workDatas;
    public WorkRecyclerAdapter(Context context, WorkData[] workDatas) {
        this.context = context;
        this.workDatas = workDatas;
    }

    @NonNull
    @Override
    public WorkViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.worklist_item, parent, false);
        return new WorkViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WorkViewHolder holder, int position) {
        holder.title.setText(workDatas[position].title);
        holder.content.setText(workDatas[position].content);
        try {
            // 開啟 JPEG 檔案
            InputStream inputStream = context.getAssets().open(workDatas[position].image);

            // 將 JPEG 檔案解碼為 Bitmap 物件
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);

            // 將 Bitmap 物件設定到 ImageView 上面
            holder.image.setImageBitmap(bitmap);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int getItemCount() {
        return workDatas.length;
    }

    public static class WorkViewHolder extends RecyclerView.ViewHolder{
        private TextView title;
        private TextView content;
        private ImageView image;
        public WorkViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            content = itemView.findViewById(R.id.tv_content);
            image = itemView.findViewById(R.id.iv_image);
        }
    }
}

class WorkDataArrayList{
    ArrayList<WorkData> workDataArrayList;
}

class WorkData{
    String title;
    String content;
    String image;
    public WorkData(String title, String content, String image) {
        this.title = title;
        this.content = content;
        this.image = image;
    }
}