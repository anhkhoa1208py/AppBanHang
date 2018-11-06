package com.example.erichuynh.myapplication.activity;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.support.v7.widget.Toolbar;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.adapter.LoaispAdapter;
import com.example.erichuynh.myapplication.callback.ClickLoaiSp;
import com.example.erichuynh.myapplication.fragment.DienThoaiFragment;
import com.example.erichuynh.myapplication.fragment.LaptopFragment;
import com.example.erichuynh.myapplication.fragment.LienHeFragment;
import com.example.erichuynh.myapplication.fragment.MainFragment;
import com.example.erichuynh.myapplication.model.Giohang;
import com.example.erichuynh.myapplication.model.Loaisp;
import com.example.erichuynh.myapplication.util.CheckConnection;
import com.example.erichuynh.myapplication.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements ClickLoaiSp {
    Toolbar toolbar;
    DrawerLayout drawerLayout;
    RecyclerView rcv_loaisp;
    ArrayList<Loaisp> mangloaisp;
    public static ArrayList<Giohang> manggiohang=new ArrayList<>();
    int id = 0;
    String tenloaisp = "";
    String hinhanhloaisp = "";
    LoaispAdapter loaispAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initFragment(new MainFragment(),null);
        initData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(), GioHang.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbarmanhinhchinh);
        drawerLayout = findViewById(R.id.drowerlayout);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        rcv_loaisp = findViewById(R.id.rcv_loaisp);
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0, new Loaisp(0, "Trang chính", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcS63jHyL0uIY4vSc-LxFeOi6f3JBawzro2CaoKuR0OYsVVFavIYBw"));
        rcv_loaisp.setHasFixedSize(true);
        rcv_loaisp.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        loaispAdapter = new LoaispAdapter(this, mangloaisp, this);
        rcv_loaisp.setAdapter(loaispAdapter);
    }

    private void initFragment(Fragment fragment, Bundle bundle){
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        if(bundle!=null){
            fragment.setArguments(bundle);
        }
        ft.replace(R.id.fragment, fragment);
        ft.commit();
    }
    private void initData(){
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdanloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id, tenloaisp, hinhanhloaisp));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(3, new Loaisp(0, "Liên hệ", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQJGSfpHh3aafHQb-btSt-soLUnQHL_7aapVVmVn2WF3Nk3ZaBT"));
                    mangloaisp.add(4, new Loaisp(0, "Thông tin", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTJ9X_5lUfZjDi1VN6RguXmrEKEQut4uvlT4mJw9kFSBSwiGaSS"));
                    //Log.e("mangloaisp=",mangloaisp.toString());
                    loaispAdapter.notifyDataSetChanged();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(MainActivity.this, error.toString());
            }
        });

        requestQueue.add(jsonArrayRequest);
    }

    @Override
    public void click_Loai_SP(int position) {
        switch (position) {
            case 0:
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    Intent intent = new Intent(MainActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Ban hay kiem tra lai ket noi");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 1:
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    Bundle bundle=new Bundle();
                    bundle.putInt("idloaisanpham",mangloaisp.get(position).getId());
                    initFragment(new DienThoaiFragment(),bundle);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Ban hay kiem tra lai ket noi");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 2:
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    Bundle bundle=new Bundle();
                    bundle.putInt("idloaisanpham",mangloaisp.get(position).getId());
                    initFragment(new LaptopFragment(),bundle);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Ban hay kiem tra lai ket noi");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 3:
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    initFragment(new LienHeFragment(),null);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Ban hay kiem tra lai ket noi");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
            case 4:
                if (CheckConnection.haveNetworkConnection(getApplicationContext())) {
                    Intent intent=new Intent(MainActivity.this,MapsActivity.class);
//                  intent.putExtra("idloaisanpham", mangloaisp.get(position).getId());
                    startActivity(intent);
                } else {
                    CheckConnection.ShowToast_Short(getApplicationContext(), "Ban hay kiem tra lai ket noi");
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                break;
        }

    }
}
