package com.robi.katalogmagang;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.robi.katalogmagang.API.Service;
import com.robi.katalogmagang.Adapter.ListArrKategori;
import com.robi.katalogmagang.Model.ModelData;
import com.robi.katalogmagang.Model.ModelDataKategori;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Kategori extends AppCompatActivity implements AdapterView.OnItemClickListener{

    private ArrayList<ModelDataKategori> datakategori = new ArrayList<ModelDataKategori>();
    ListView listView;
    ListArrKategori adapter;

    LinearLayout layout_loading;
    TextView ket_load;
    ImageView img_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.kategori);

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Kategori Bidang Keahlian");

        layout_loading = (LinearLayout) findViewById(R.id.layout_loading);
        ket_load = (TextView) findViewById(R.id.ket_load);
        img_load = (ImageView) findViewById(R.id.icon_load);

        listView = (ListView) findViewById(R.id.listKategori);
        listView.setOnItemClickListener(Kategori.this);
        listView.setDividerHeight(0);
    }

    public void setup(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Call<List<ModelDataKategori>> call = service.getSemuaKategori();
        call.enqueue(new Callback<List<ModelDataKategori>>() {
            @Override
            public void onResponse(Call<List<ModelDataKategori>> call, Response<List<ModelDataKategori>> response) {
                datakategori.clear();

                if (response.isSuccessful()){
                    int jumlah = response.body().size();

                    for (int i=0; i < jumlah; i++){
                        ModelDataKategori data = new ModelDataKategori(
                                response.body().get(i).getId_kategoritm(),
                                response.body().get(i).getKategoritm());
                        datakategori.add(data);
                        Log.d("Respon", "onResponse" + response.body().get(i).getId_kategoritm());
                    }
                    listView.setVisibility(View.VISIBLE);
                    adapter = new ListArrKategori(Kategori.this, R.layout.row_daftar_kategori, datakategori);
                    listView.setAdapter(adapter);

                    if (adapter.getCount() < 1) {
                        layout_loading.setVisibility(View.VISIBLE);
                        String error = "Daftar Tempat Magang Kosong";
                        ket_load.setText(error);
                        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_announcement_black_24dp);
                        img_load.setImageBitmap(img);
                    } else {
                        layout_loading.setVisibility(View.GONE);
                    }
                } else {
                    String error = "Gagal Pengambilan Data Dari Server !!!";
                    ket_load.setText(error);
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_signal_wifi_off_black_24dp);
                    img_load.setImageBitmap(img);
                }
            }

            @Override
            public void onFailure(Call<List<ModelDataKategori>> call, Throwable t) {
                String error = "Gagal Pengambilan Data Dari Server !!!" + t.getMessage();
                ket_load.setText(error);
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_signal_wifi_off_black_24dp);
                img_load.setImageBitmap(img);
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1) {
//            adapter.clear();
            setup();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id_kategotitm) {
        TextView id_kategoritm = (TextView) view.findViewById(R.id.id_kategoritm);
        Intent intent = new Intent(Kategori.this, DaftarKatTempat.class);
        intent.putExtra(ModelDataKategori.id_kategori, id_kategoritm.getText().toString());
        startActivityForResult(intent, 1);
//                Toast.makeText(Kategori.this, id_kategoritm.getText().toString(), Toast.LENGTH_SHORT).show();

    }
}
