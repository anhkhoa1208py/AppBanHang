package com.example.erichuynh.myapplication.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.GravityCompat;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.adapter.LoaispAdapter;
import com.example.erichuynh.myapplication.adapter.SanphamAdapter;
import com.example.erichuynh.myapplication.model.Loaisp;
import com.example.erichuynh.myapplication.model.Sanpham;
import com.example.erichuynh.myapplication.util.CheckConnection;
import com.example.erichuynh.myapplication.util.Server;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainFragment extends Fragment {

    ViewFlipper viewFlipper;
    RecyclerView recyclerview;


    ArrayList<Sanpham> mangsanpham;
    SanphamAdapter sanphamAdapter;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        if (CheckConnection.haveNetworkConnection(getActivity())) {
            ActionViewFlipper();
            GetDuLieuSPMoiNhat();
        } else {
            CheckConnection.ShowToast_Short(getActivity(), "Ban hay kiem tra lai ket noi");
            getActivity().finish();
        }

    }


    private void GetDuLieuSPMoiNhat() {
        final RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.Duongdansanphammoinhat, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null) {
                    int ID = 0;
                    String Tenloaisanpham = "";
                    Integer Giasanpham = 0;
                    String Hinhanhsanpham = "";
                    String Motasanpham = "";
                    int IDsanpham = 0;
                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            Tenloaisanpham = jsonObject.getString("tensp");
                            Giasanpham = jsonObject.getInt("giasp");
                            Hinhanhsanpham = jsonObject.getString("hinhanhsp");
                            Motasanpham = jsonObject.getString("motasp");
                            IDsanpham = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID, Tenloaisanpham, Giasanpham, Hinhanhsanpham, Motasanpham, IDsanpham));
                           //Log.e("mangsanpham=", mangsanpham.toString());
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }
                sanphamAdapter = new SanphamAdapter(getActivity(), mangsanpham);
                recyclerview.setAdapter(sanphamAdapter);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });
        requestQueue.add(jsonArrayRequest);
    }


    private void ActionViewFlipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://znews-photo-td.zadn.vn/w660/Uploaded/JAC2_N3/2014_07_01/Tuan_Hung_muon_lam_vua_quang_cao_1.jpg");
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQpVpxtYDJKRYh__3p72ob41ENCGqpgwZ8XRz43mtvGfqsS0t2vIQ");
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRfrn56tF_5Lt-UDgI_5YPxEqWXYmtjwVau_UZ_Y9CLNa2fFDLBjg");
        mangquangcao.add("https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTeUT_J915MsejM69tuT2Q8R7XCizrGIw4xDE4VHD3agLo-u38J");
        for (int i = 0; i < mangquangcao.size(); i++) {
            ImageView imageView = new ImageView(getActivity());
            Picasso.with(getActivity()).load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(5000);
        viewFlipper.setAutoStart(true);
        Animation animation_slide_in = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_in_right);
        Animation animation_slide_out = AnimationUtils.loadAnimation(getActivity(), R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_slide_in);
        viewFlipper.setOutAnimation(animation_slide_out);
    }

    private void Anhxa(View view) {
        viewFlipper = view.findViewById(R.id.viewlipper);
        recyclerview = view.findViewById(R.id.recyvlerView);
        mangsanpham = new ArrayList<>();
        recyclerview.setHasFixedSize(true);
        recyclerview.setLayoutManager(new GridLayoutManager(getActivity(), 2));
    }

}
