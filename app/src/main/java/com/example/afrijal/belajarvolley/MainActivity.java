package com.example.afrijal.belajarvolley;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //deklarasi variable
    private EditText noid, bulan, tahun;
    private Button btn;
    private TextView t1, t2, tk1, tk2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //ambil ID
        noid = (EditText) findViewById(R.id.noId);
        bulan = (EditText) findViewById(R.id.bulan);
        tahun = (EditText) findViewById(R.id.tahun);
        btn = (Button) findViewById(R.id.btn);
        t1 = (TextView)findViewById(R.id.t1);
        t2 = (TextView)findViewById(R.id.t2);
        tk1 = (TextView) findViewById(R.id.tk1);
        tk2 = (TextView) findViewById(R.id.tk2);

        btn.setOnClickListener(this);
    }

    //ambil data dari api
    private void getDataPasien(String noid, String tahun, String bulan) {
        String URL = "http://ibacor.com/api/tagihan-pln?idp="+noid+"&thn="+tahun+"&bln="+bulan;

        StringRequest request = new StringRequest(Request.Method.GET, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.e("DATA", response.toString());

                try {
                    //convert data json ke string
                    JSONObject object = new JSONObject(response.toString());

                    //ambil data json dari api
                    JSONObject data = object.getJSONObject("data");

                    //mengambil string nama dari data pelanggan
                    String nama = data.getString("nama");

                    //mengambil string dari tagihan data pelanggan
                    String tagihan = data.getString("tagihan");

                    //mengubah tulisan menjadi data

                    String konversi = convert(Integer.parseInt(tagihan));

                    tk1.setText(nama);
                    tk2.setText(konversi);

                    String Nama = "Nama";
                    String Besar = "Besar Tagihan";

                    t1.setText(Nama);
                    t2.setText(Besar);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        },     new Response.ErrorListener() {
            public void onErrorResponse(VolleyError error) {
                Log.e("DATA", error.toString());
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }

    //jika button diklik
    public void onClick(View view) {
        if (view == btn) {

            String id = noid.getText().toString();
            String bln = bulan.getText().toString();
            String thn = tahun.getText().toString();

            if (id.isEmpty() || bln.isEmpty() || thn.isEmpty()){
                Toast.makeText(getApplicationContext(),"Anda mengisi dengan benar", Toast.LENGTH_SHORT).show();

            }
            try {
                getDataPasien(id, thn, bln);
            } catch (Exception e) {
                Toast.makeText(getApplicationContext(), "Koneksi Error", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // konversi string ke mata uang
    private String convert (int number){
        DecimalFormat df = (DecimalFormat) DecimalFormat.getCurrencyInstance();
        DecimalFormatSymbols dfs = new DecimalFormatSymbols();
        dfs.setCurrencySymbol("Rp ");
        dfs.setMonetaryDecimalSeparator(',');
        dfs.setGroupingSeparator('.');
        df.setDecimalFormatSymbols(dfs);

        return df.format(number);
    }

}