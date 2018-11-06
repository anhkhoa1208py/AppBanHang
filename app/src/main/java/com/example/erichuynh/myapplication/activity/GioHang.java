package com.example.erichuynh.myapplication.activity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.support.v7.widget.Toolbar;

import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.adapter.GioHangAdapter;
import com.example.erichuynh.myapplication.model.Giohang;
import com.example.erichuynh.myapplication.util.CheckConnection;

import java.text.DecimalFormat;

public class GioHang extends AppCompatActivity {
ListView lvgiohang;
TextView txtthongbao;
    static TextView txttongtien;
Button btnthanhtoan,btntieptucmua;
Toolbar toolbargiohang;
GioHangAdapter gioHangAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gio_hang);
        Anhxa();
        ActionToolbar();
        CheckData();
        EventUltil();
        CatchOnItemListView();
        EventButton();
    }

    private void EventButton() {
        btntieptucmua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                startActivity(intent);
            }
        });
        btnthanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MainActivity.manggiohang.size()>0){
                    Intent intent=new Intent(getApplicationContext(),ThongTinActivity.class);
                    startActivity(intent);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Gio hang cua ban chua co san pham");

                }
            }
        });
    }

    private void CatchOnItemListView() {
        lvgiohang.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                AlertDialog.Builder builder=new AlertDialog.Builder(GioHang.this);
                builder.setTitle("Xac nhan xoa san pham");
                builder.setMessage("Ban co chac chan muon xoa san pham nay");
                builder.setPositiveButton("Co", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (MainActivity.manggiohang.size()<=0){
                            txtthongbao.setVisibility(View.VISIBLE);
                        }else {
                            MainActivity.manggiohang.remove(position);
                            gioHangAdapter.notifyDataSetChanged();
                            EventUltil();
                            if (MainActivity.manggiohang.size()<=0){
                                txtthongbao.setVisibility(View.VISIBLE);
                            }else {
                                txtthongbao.setVisibility(View.VISIBLE);
                                gioHangAdapter.notifyDataSetChanged();
                                EventUltil();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Khong", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        gioHangAdapter.notifyDataSetChanged();
                        EventUltil();
                    }
                });
                builder.show();
                return true;
            }
        });
    }

    public static void EventUltil() {
        long tongtien=0;
        for (int i=0;i<MainActivity.manggiohang.size();i++){
            tongtien+=MainActivity.manggiohang.get(i).getGiasp();

        }
        DecimalFormat decimalFormat=new DecimalFormat("###,###,###");
        txttongtien.setText(decimalFormat.format(tongtien)+"D");
    }

    private void CheckData() {
        if (MainActivity.manggiohang.size()<=0){
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.INVISIBLE)
            ;
        }
        else {
            gioHangAdapter.notifyDataSetChanged();
            txtthongbao.setVisibility(View.INVISIBLE);
            lvgiohang.setVisibility(View.VISIBLE)
            ;
        }
    }

    private void ActionToolbar() {
        setSupportActionBar(toolbargiohang);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbargiohang.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void Anhxa() {
        lvgiohang=findViewById(R.id.listviewgiohang);
        txtthongbao=findViewById(R.id.textviewthongbao);
        txttongtien=findViewById(R.id.textviewtongtien);
        btnthanhtoan=findViewById(R.id.buttonthanhtoangiohang);
        btntieptucmua=findViewById(R.id.buttontieptucmuahang);
        toolbargiohang=findViewById(R.id.toolbargiohang);
        gioHangAdapter=new GioHangAdapter(GioHang.this,MainActivity.manggiohang);
        lvgiohang.setAdapter(gioHangAdapter);
    }
}
