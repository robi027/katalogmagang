package com.robi.katalogmagang;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.ScaleGestureDetector;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.robi.katalogmagang.API.Service;
import com.robi.katalogmagang.Adapter.ListArr;
import com.robi.katalogmagang.Model.ModelData;
import com.robi.katalogmagang.Model.ModelDataKategori;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaftarKatTempat extends AppCompatActivity implements AdapterView.OnItemClickListener{

    String SEMUAKATEGORI;
    private ArrayList<ModelData> datakattempat = new ArrayList<ModelData>();
    ListView listView;
    ListArr adapter;

    LinearLayout layout_loading;
    TextView ket_load;
    ImageView img_load;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_kat_tempat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("List Tempat");

        SEMUAKATEGORI = getIntent().getStringExtra(ModelDataKategori.id_kategori);
//        TextView pilih = (TextView) findViewById(R.id.kategori_pili);
//        pilih.setText(KATEGORI);

        layout_loading = (LinearLayout) findViewById(R.id.layout_loading);
        ket_load = (TextView) findViewById(R.id.ket_load);
        img_load = (ImageView) findViewById(R.id.icon_load);

        listView = (ListView) findViewById(R.id.listKatTempat);
        listView.setOnItemClickListener(DaftarKatTempat.this);
        listView.setDividerHeight(0);

    }

    public void setup(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.getInstance())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();

        Service service = retrofit.create(Service.class);
        Call<List<ModelData>> call = service.getdatakategori(SEMUAKATEGORI);
        call.enqueue(new Callback<List<ModelData>>() {
            @Override
            public void onResponse(Call<List<ModelData>> call, Response<List<ModelData>> response) {
                datakattempat.clear();
                if (response.isSuccessful()) {
                    int jumlah = response.body().size();

                    for (int i =0; i <jumlah; i++){
                        ModelData data = new ModelData(
                                response.body().get(i).getId_tm(),
                                response.body().get(i).getNm_tm(),
                                response.body().get(i).getAlamat_tm(),
                                response.body().get(i).getKet_tm());
                        datakattempat.add(data);
                        Log.d("Respon", "onResponse" + response.body().get(i).getId_tm());
                    }
                    listView.setVisibility(View.VISIBLE);
                    adapter = new ListArr(DaftarKatTempat.this, R.layout.row_daftar_tempat, datakattempat);
                    listView.setAdapter(adapter);

                    if (adapter.getCount()< 1){
                        layout_loading.setVisibility(View.VISIBLE);
                        String error = "Daftar Tempat Magang Kosong";
                        ket_load.setText(error);
                        Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_announcement_black_24dp);
                        img_load.setImageBitmap(img);
                    }else {
                        layout_loading.setVisibility(View.GONE);
                    }
                } else{
                    String error = "Gagal Pengambilan Data Dari Server !!!";
                    ket_load.setText(error);
                    Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_signal_wifi_off_black_24dp);
                    img_load.setImageBitmap(img);
                }
            }

            @Override
            public void onFailure(Call<List<ModelData>> call, Throwable t) {
                String error = "Gagal Pengambilan Data Dari Server !!!" + t.getMessage();
                ket_load.setText(error);
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_signal_wifi_off_black_24dp);
                img_load.setImageBitmap(img);
            }
        });
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
    protected void onResume() {
        super.onResume();
        setup();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id_tm) {
        TextView id_list = (TextView) view.findViewById(R.id.id_tm);
        Intent intent = new Intent(DaftarKatTempat.this, DetailTempat.class);
        intent.putExtra(ModelData.id_tmagang, id_list.getText().toString());
        startActivityForResult(intent, 1);
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
}
