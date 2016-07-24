package com.example.afrijal.vollydemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class MainActivity extends AppCompatActivity {
    Button btn;
    TextView tv;
    String server_url = "http://192.168.1.24/greeting.php";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btn = (Button)findViewById(R.id.btn);
        tv = (TextView)findViewById(R.id.sr);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        final RequestQueue requestQueue = Volley.newRequestQueue(MainActivity.this);

                        StringRequest stringRequest = new StringRequest(Request.Method.GET, server_url,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        tv.setText(response);
                                        requestQueue.stop();
                                    }
                                }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                tv.setText("Something went wrong");
                                error.printStackTrace();
                                requestQueue.stop();
                            }
                        });
                        requestQueue.add(stringRequest);

                    }
                });
    }
}
