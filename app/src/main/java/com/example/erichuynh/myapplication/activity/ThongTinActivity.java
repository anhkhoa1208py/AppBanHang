package com.example.erichuynh.myapplication.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.erichuynh.myapplication.R;
import com.example.erichuynh.myapplication.util.CheckConnection;
import com.example.erichuynh.myapplication.util.Server;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinActivity extends AppCompatActivity {
EditText edttenkhachhang,edtemail,edtsdt;
Button btnxacnhan,btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_activityu);
    Anhxa();
btntrove.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        finish();
    }
});
    if (CheckConnection.haveNetworkConnection(getApplicationContext())){
        EventButton();
    }else {
        CheckConnection.ShowToast_Short(getApplicationContext(),"Ban hay kiem tra lai ket noi");
    }
    }
    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String ten=edttenkhachhang.getText().toString().trim();
                final String sdt=edtsdt.getText().toString().trim();
                final String email=edtemail.getText().toString().trim();
                if (ten.length()>0 && sdt.length()>0 && email.length()>0){
                    RequestQueue requestQueue= Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest=new StringRequest(Request.Method.POST, Server.Duongdandonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhnag) {
                            Log.d("madonhang",madonhnag);
                            if (Integer.parseInt(madonhnag)>0){
                                RequestQueue queue=Volley.newRequestQueue(getApplicationContext());
                                StringRequest request=new StringRequest(Request.Method.POST, Server.Duongdanchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        if (response.equals("1")){
                                            MainActivity.manggiohang.clear();
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Ban da them du lieu gio hang thanh cong");
                                            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Moi ban tiep tuc mua hang");
                                        }else {
                                            CheckConnection.ShowToast_Short(getApplicationContext(),"Du lieu gio hang cua ban da bi loi");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {

                                    }
                                }){
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray=new JSONArray();
                                        for (int i=0;i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject=new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhnag);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());

                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap=new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {

                        }
                    }){
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap=new HashMap<String, String>();
                            hashMap.put("tenkhachhang",ten);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };
                    requestQueue.add(stringRequest);
                }else {
                    CheckConnection.ShowToast_Short(getApplicationContext(),"Hay kiem tra lai du lieu");
                }
            }
        });
    }


    private void Anhxa() {
        edttenkhachhang=findViewById(R.id.edittexttenkhachhang);
        edtemail=findViewById(R.id.edittextemail);
        edtsdt=findViewById(R.id.edittextsodienthoai);
        btnxacnhan=findViewById(R.id.buttonxacnhan);
        btntrove=findViewById(R.id.buttontrove);
    }
}
