package com.example.erichuynh.myapplication.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.activity.ChiTietSanPham;
import com.example.erichuynh.myapplication.model.Sanpham;
import com.example.erichuynh.myapplication.util.CheckConnection;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SanphamAdapter extends RecyclerView.Adapter<SanphamAdapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arrayList;

    public SanphamAdapter(Context context, ArrayList<Sanpham> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoinhat,null);
        ItemHolder itemHolder=new ItemHolder(view);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham=arrayList.get(position);
        holder.txttensanpham.setText(sanpham.getTensanpham());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        holder.txtgiasanpham.setText("Gia : "+decimalFormat.format(sanpham.getGiasanpham())+"D");
        Picasso.with(context).load(sanpham.getHinhanhsanpham())
                        .placeholder(R.drawable.anh1)
                        .error(R.drawable.anh2)
                        .into(holder.imghinhanhsanpham);

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class ItemHolder extends RecyclerView.ViewHolder{
            public ImageView imghinhanhsanpham;
            public TextView txttensanpham,txtgiasanpham;

        public ItemHolder(View itemView) {
            super(itemView);
            imghinhanhsanpham=itemView.findViewById(R.id.imageviewsanpham);
            txtgiasanpham=itemView.findViewById(R.id.textviewgiasanpham);
            txttensanpham=itemView.findViewById(R.id.textviewsanpham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent=new Intent(context, ChiTietSanPham.class);
                    intent.putExtra("thongtinsanpham",arrayList.get(getAdapterPosition()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    CheckConnection.ShowToast_Short(context,arrayList.get(getAdapterPosition()).getTensanpham());
                    context.startActivity(intent);
                }
            });
        }
    }

}
