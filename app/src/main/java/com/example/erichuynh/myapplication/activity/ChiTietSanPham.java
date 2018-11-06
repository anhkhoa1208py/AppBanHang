package com.example.erichuynh.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.model.Giohang;
import com.example.erichuynh.myapplication.model.Sanpham;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;

public class ChiTietSanPham extends AppCompatActivity {
Toolbar toolbarChitiet;
ImageView imgChitiet;
TextView txtten,txtgia,txtmota;
Spinner spinner;
Button btndatmua;
    int id=0;
    String TenChiTiet="";
    int GiaChiTiet=0;
    String MotaChitiet="";
    String HinhanhChitiet="";
    int Idsanpham=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_san_pham);
        Anhxa();
        ActionToolbar();
        GetInformation();
        CatchEventSpinner();
        EventButton();
    }

    private void EventButton() {
        btndatmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    int soluong=Integer.parseInt(spinner.getSelectedItem().toString());
                    for (int i=0;i<MainActivity.manggiohang.size();i++){
                        if (MainActivity.manggiohang.get(i).getIdsp()==id){
                            MainActivity.manggiohang.get(i).setSoluongsp(MainActivity.manggiohang.get(i).getSoluongsp()+soluong);
                            if (MainActivity.manggiohang.get(i).getSoluongsp()>=10){
                                MainActivity.manggiohang.get(i).setSoluongsp(10);
                            }
                            MainActivity.manggiohang.get(i).setGiasp(GiaChiTiet*MainActivity.manggiohang.get(i).getSoluongsp());
                        }
                    }
                    long Giamoi=soluong*GiaChiTiet;
                    Log.e("addGioHang=",TenChiTiet+"soluong="+soluong+"Giamoi="+Giamoi);
                    MainActivity.manggiohang.add(new Giohang(id,TenChiTiet,Giamoi,HinhanhChitiet,soluong));
                }else {
                    int soluong= Integer.parseInt(spinner.getSelectedItem().toString());
                    long Giamoi=soluong*GiaChiTiet;
                    Log.e("addGioHang222=",TenChiTiet+"soluong="+soluong+"Giamoi="+Giamoi);
                    MainActivity.manggiohang.add(new Giohang(id,TenChiTiet,Giamoi,HinhanhChitiet,soluong));

                }
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
            }
        });
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent=new Intent(getApplicationContext(),GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void CatchEventSpinner() {
        Integer[] soluong=new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> arrayAdapter=new ArrayAdapter<Integer>(this,android.R.layout.simple_spinner_dropdown_item,soluong);
        spinner.setAdapter(arrayAdapter);
    }

    private void GetInformation() {
        Sanpham sanpham= (Sanpham) getIntent().getSerializableExtra("thongtinsanpham");
        id=sanpham.getID();
        TenChiTiet=sanpham.getTensanpham();
        GiaChiTiet=sanpham.getGiasanpham();
        HinhanhChitiet=sanpham.getHinhanhsanpham();
        Idsanpham=sanpham.getIDSanpham();
        MotaChitiet=sanpham.getMotasanpham();
        txtten.setText(TenChiTiet);
         DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txtgia.setText("Gia: "+decimalFormat.format(GiaChiTiet)+"D");
        txtmota.setText(MotaChitiet);
        Picasso.with(getApplicationContext()).load(HinhanhChitiet)
                .placeholder(R.drawable.anh1)
                .error(R.drawable.anh2)
                .into(imgChitiet);

    }

    private void ActionToolbar() {
        setSupportActionBar(toolbarChitiet);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarChitiet.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        toolbarChitiet=findViewById(R.id.toolbarchitietsanpham);
        imgChitiet=findViewById(R.id.imageviewchitietsanpham);
        txtten=findViewById(R.id.textviewtensanpham);
        txtgia=findViewById(R.id.textviewgiachitietsanpham);
        txtmota=findViewById(R.id.textviewmotachitietsanpham);
        spinner=findViewById(R.id.spinner);
        btndatmua=findViewById(R.id.buttondatmua);
    }
}
