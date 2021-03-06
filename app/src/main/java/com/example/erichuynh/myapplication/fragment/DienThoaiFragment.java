package com.example.erichuynh.myapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.activity.ChiTietSanPham;
import com.example.erichuynh.myapplication.adapter.DienthoaiAdapter;
import com.example.erichuynh.myapplication.model.Sanpham;
import com.example.erichuynh.myapplication.util.CheckConnection;
import com.example.erichuynh.myapplication.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class DienThoaiFragment extends Fragment {
    ListView lvdt;
    DienthoaiAdapter dienthoaiAdapter;
    ArrayList<Sanpham> mangdt;
    int iddt=0;
    int page=1;
    View footerview;
    boolean isLoading=false;
    mHandler mHandler;
    boolean limitdata=false;
    RequestQueue requestQueue;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_dien_thoai, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Anhxa(view);
        requestQueue = Volley.newRequestQueue(getActivity());
        if (CheckConnection.haveNetworkConnection(getActivity())){
            GetIdloaisp();
            GetData(page);
            LoadMoreData();
        }else {
            CheckConnection.ShowToast_Short(getActivity(),"Ban hay kiem tra lai internet");
            getActivity().finish();
        }
    }
    private void LoadMoreData() {
        lvdt.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(getActivity(),ChiTietSanPham.class);
                intent.putExtra("thongtinsanpham",mangdt.get(position));
                startActivity(intent);
            }
        });
        lvdt.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
               /* if (firstVisibleItem+visibleItemCount==totalItemCount&&totalItemCount!=0&& isLoading==false&&limitdata==false);
                isLoading=true;
                ThreadData threadData=new ThreadData();
                threadData.start();*/

            }
        });
    }



    private void GetData(int Page) {
        String duongdan= Server.Duongdandienthoai+String.valueOf(Page);
        StringRequest stringRequest=new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id=0;
                String Tendt="";
                int Giadt=0;
                String Hinhanhdt="";
                String Mota="";
                int Idspdt=0;
                if (response!=null&&response.length()!=2){
                    lvdt.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray=new JSONArray(response);
                        for (int i=0;i<jsonArray.length();i++){
                            JSONObject jsonObject=jsonArray.getJSONObject(i);
                            id=jsonObject.getInt("id");
                            Tendt=jsonObject.getString("tensp");
                            Giadt=jsonObject.getInt("giasp");
                            Hinhanhdt=jsonObject.getString("hinhanhsp");
                            Idspdt=jsonObject.getInt("idsanpham");
                            Mota=jsonObject.getString("motasp");
                            mangdt.add(new Sanpham(id,Tendt,Giadt,Hinhanhdt,Mota,Idspdt));
                            dienthoaiAdapter.notifyDataSetChanged();
                            Log.e("mangsanpham=", mangdt.toString());
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }

                else {
                    limitdata=true;
                    lvdt.removeFooterView(footerview);
                    CheckConnection.ShowToast_Short(getActivity(),"Da het du lieu");
                }
                dienthoaiAdapter.notifyDataSetChanged();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
                CheckConnection.ShowToast_Short(getActivity(),error.toString());
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param=new HashMap<String, String>();
                param.put("idsanpham",String.valueOf(iddt));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }


    private void GetIdloaisp() {
        iddt=getArguments().getInt("idloaisanpham",-1);
        Log.d("giatriloaisanpham",iddt+"");
    }

    private void Anhxa(View view) {
        lvdt=view.findViewById(R.id.listviewdienthoai);
        mangdt=new ArrayList<>();
        dienthoaiAdapter=new DienthoaiAdapter(getActivity(),mangdt);
        lvdt.setAdapter(dienthoaiAdapter);
        LayoutInflater inflater= (LayoutInflater) getActivity().getSystemService(getActivity().LAYOUT_INFLATER_SERVICE);
        footerview=inflater.inflate(R.layout.progressbar,null);
        mHandler=new mHandler();
    }
    public class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case 0:
                    lvdt.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading=false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message= mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
}
