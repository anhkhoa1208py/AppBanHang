package com.example.erichuynh.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.activity.ChiTietSanPham;
import com.example.erichuynh.myapplication.callback.ClickLoaiSp;
import com.example.erichuynh.myapplication.model.Loaisp;
import com.example.erichuynh.myapplication.model.Sanpham;
import com.example.erichuynh.myapplication.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class LoaispAdapter extends RecyclerView.Adapter<LoaispAdapter.ItemHolder> {
    Context context;
    ArrayList<Loaisp> arrayList;
    ClickLoaiSp clickLoaiSp;
    public LoaispAdapter(Context context, ArrayList<Loaisp> arrayList,ClickLoaiSp clickLoaiSp) {
        this.context = context;
        this.arrayList = arrayList;
        this.clickLoaiSp = clickLoaiSp;
    }

    @NonNull
    @Override
    public LoaispAdapter.ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_listview_loaisp,null);
        LoaispAdapter.ItemHolder itemHolder=new LoaispAdapter.ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull LoaispAdapter.ItemHolder holder, final int position) {
        Loaisp loaisp=arrayList.get(position);
        //Log.e("Tenloaisp=",loaisp.Tenloaisp);
        holder.txttenloaisp.setText(loaisp.getTenloaisp());
        Picasso.with(context).load(loaisp.getHinhanhloaisp())
                .placeholder(R.drawable.anh1)
                .error(R.drawable.anh2)
                .into(holder.imgloaisp);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickLoaiSp.click_Loai_SP(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
        TextView txttenloaisp;
        ImageView imgloaisp;

        public ItemHolder(View itemView) {
            super(itemView);
            txttenloaisp=itemView.findViewById(R.id.textviewloaisp);
            imgloaisp=itemView.findViewById(R.id.imageviewloaisp);
        }
    }
}
