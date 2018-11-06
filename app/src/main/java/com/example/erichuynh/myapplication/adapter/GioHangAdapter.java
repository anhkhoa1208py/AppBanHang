package com.example.erichuynh.myapplication.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.activity.GioHang;
import com.example.erichuynh.myapplication.activity.MainActivity;
import com.example.erichuynh.myapplication.model.Giohang;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class GioHangAdapter extends BaseAdapter {
    ViewHolder viewHolder=null;

    Context context;
    ArrayList<Giohang>arraygiohang;

    public GioHangAdapter(Context context, ArrayList<Giohang> arraygiohang) {
        this.context = context;
        this.arraygiohang = arraygiohang;
    }

    @Override
    public int getCount() {
        return arraygiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arraygiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
public class ViewHolder{
        public TextView txttengiohang,txtgiagiohang;
        public ImageView imggiohang;
        public Button btnminus,btnvalues,btnplus;
}
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       if (convertView==null){
           viewHolder=new ViewHolder();
           LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
           convertView=inflater.inflate(R.layout.dong_giohang,null);
           viewHolder.txttengiohang=convertView.findViewById(R.id.textviewtengiohang);
           viewHolder.txtgiagiohang=convertView.findViewById(R.id.textviewgiagiohang);
           viewHolder.imggiohang=convertView.findViewById(R.id.imageviewgiohang);
           viewHolder.btnminus=convertView.findViewById(R.id.buttonminus);
           viewHolder.btnplus=convertView.findViewById(R.id.buttonplus);
           viewHolder.btnvalues=convertView.findViewById(R.id.buttonvalues);
           convertView.setTag(viewHolder);
       }else {
           viewHolder= (ViewHolder) convertView.getTag();
       }
       Giohang gioHang= (Giohang) getItem(position);
       viewHolder.txttengiohang.setText(gioHang.getTensp());
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        viewHolder.txtgiagiohang.setText(decimalFormat.format(gioHang.getGiasp())+"D");
        Picasso.with(context).load(gioHang.getHinhsp())
                .placeholder(R.drawable.anh1)
                .error(R.drawable.anh2)
                .into(viewHolder.imggiohang);
        viewHolder.btnvalues.setText(gioHang.getSoluongsp()+"");
       int sl= Integer.parseInt(viewHolder.btnvalues.getText().toString());
       if (sl>=10){
           viewHolder.btnplus.setVisibility(View.INVISIBLE);
           viewHolder.btnminus.setVisibility(View.VISIBLE);
       }else if (sl<=1){
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
       }else if (sl>=1){
           viewHolder.btnminus.setVisibility(View.VISIBLE);
           viewHolder.btnplus.setVisibility(View.VISIBLE);
       }

        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int slmoinhat=Integer.parseInt(viewHolder.btnvalues.getText().toString())+1;
            int slht= MainActivity.manggiohang.get(position).getSoluongsp();
            long giaht=MainActivity.manggiohang.get(position).getGiasp();
            MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
            long giamoinhat=(giaht*slmoinhat)/slht;
            MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
               DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
               viewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+"D");
                GioHang.EventUltil();
                if (slmoinhat>9){
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnplus.setVisibility(View.INVISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoinhat));


                }else {
                    viewHolder.btnminus.setVisibility(View.VISIBLE);
                    viewHolder.btnplus.setVisibility(View.VISIBLE);
                    viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
                }
           }
       });
       viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               int slmoinhat=Integer.parseInt(viewHolder.btnvalues.getText().toString())-1;
               int slht= MainActivity.manggiohang.get(position).getSoluongsp();
               long giaht=MainActivity.manggiohang.get(position).getGiasp();
               MainActivity.manggiohang.get(position).setSoluongsp(slmoinhat);
               long giamoinhat=(giaht*slmoinhat)/slht;
               MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
               DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
               viewHolder.txtgiagiohang.setText(decimalFormat.format(giamoinhat)+"D");
               GioHang.EventUltil();
               if (slmoinhat<2){
                   viewHolder.btnminus.setVisibility(View.INVISIBLE);
                   viewHolder.btnplus.setVisibility(View.VISIBLE);
                   viewHolder.btnvalues.setText(String.valueOf(slmoinhat));


               }else {
                   viewHolder.btnminus.setVisibility(View.VISIBLE);
                   viewHolder.btnplus.setVisibility(View.VISIBLE);
                   viewHolder.btnvalues.setText(String.valueOf(slmoinhat));
               }
           }
       });
        return convertView;
    }
}
