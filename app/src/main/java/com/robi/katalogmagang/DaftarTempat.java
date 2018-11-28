package com.robi.katalogmagang;

import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.robi.katalogmagang.API.Service;
import com.robi.katalogmagang.Adapter.ListArr;
import com.robi.katalogmagang.Model.ModelData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DaftarTempat extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, SearchView.OnQueryTextListener, AdapterView.OnItemClickListener {

    private ArrayList<ModelData> datatempat = new ArrayList<ModelData>();
    ListView listView;
    ListArr adapter;

    LinearLayout layout_loading;
    TextView ket_load;
    ImageView img_load;

    Button kategori;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daftar_tempat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        layout_loading = (LinearLayout) findViewById(R.id.layout_loading);
        ket_load = (TextView) findViewById(R.id.ket_load);
        img_load = (ImageView) findViewById(R.id.icon_load);
        kategori = (Button) findViewById(R.id.kategori);


        listView = (ListView) findViewById(R.id.listTempat);
        listView.setOnItemClickListener(DaftarTempat.this);
        listView.setDividerHeight(0);
    }

    public void setup() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);

        Call<List<ModelData>> call = service.getSemuaTempat();
        call.enqueue(new Callback<List<ModelData>>() {
            @Override
            public void onResponse(Call<List<ModelData>> call, Response<List<ModelData>> response) {
                datatempat.clear();

                if (response.isSuccessful()) {
                    int jumlah = response.body().size();

                    for (int i = 0; i < jumlah; i++) {
                        ModelData data = new ModelData(
                                response.body().get(i).getId_tm(),
                                response.body().get(i).getNm_tm(),
                                response.body().get(i).getAlamat_tm(),
                                response.body().get(i).getKet_tm());
                        datatempat.add(data);
                        Log.d("Respon", "onResponse" + response.body().get(i).getId_tm());
                    }
                    listView.setVisibility(View.VISIBLE);
                    adapter = new ListArr(DaftarTempat.this, R.layout.row_daftar_tempat, datatempat);
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
            public void onFailure(Call<List<ModelData>> call, Throwable t) {

                String error = "Gagal Pengambilan Data Dari Server !!!" + t.getMessage();
                ket_load.setText(error);
                Bitmap img = BitmapFactory.decodeResource(getResources(), R.drawable.ic_signal_wifi_off_black_24dp);
                img_load.setImageBitmap(img);

            }
        });
    }

            @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            super.onBackPressed();
        return;
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.daftar_tempat, menu);

//        Log.w("myApp", "onCreateOptionsMenu -started- ");

//        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        MenuItem menuItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);

//        searchView.setSearchableInfo( searchManager.getSearchableInfo(getComponentName()));
        searchView.setQueryHint("Search");

        searchView.setOnQueryTextListener(
                new SearchView.OnQueryTextListener(){
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        Log.w("myApp", "onQueryTextSubmit ");
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl(MainActivity.getInstance())
                                .addConverterFactory(GsonConverterFactory.create())
                                .build();

                        Service service = retrofit.create(Service.class);

                        Call<List<ModelData>> call = service.getdatasearch(newText);
                        call.enqueue(new Callback<List<ModelData>>() {
                            @Override
                            public void onResponse(Call<List<ModelData>> call, Response<List<ModelData>> response) {
                                datatempat.clear();

                                if (response.isSuccessful()) {
                                    int jumlah = response.body().size();

                                    for (int i =0; i <jumlah; i++){
                                        ModelData data = new ModelData(
                                                response.body().get(i).getId_tm(),
                                                response.body().get(i).getNm_tm(),
                                                response.body().get(i).getAlamat_tm(),
                                                response.body().get(i).getKet_tm());
                                        datatempat.add(data);
                                        Log.d("Respon", "onResponse" + response.body().get(i).getId_tm());
                                    }
                                    listView.setVisibility(View.VISIBLE);
                                    adapter = new ListArr(DaftarTempat.this, R.layout.row_daftar_tempat, datatempat);
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
                        return true;
                    }
                });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.tentang) {
            Intent intent = new Intent(DaftarTempat.this, Tentang.class);
            startActivity(intent);
            // Handle the camera action
        }
//        } else if (id == R.id.nav_gallery) {
//
//        } else if (id == R.id.nav_slideshow) {
//
//        } else if (id == R.id.nav_manage) {
//
//        } else if (id == R.id.nav_share) {
//
//        } else if (id == R.id.nav_send) {
//
//        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id_tm) {
        TextView id_list = (TextView) view.findViewById(R.id.id_tm);
        Intent intent = new Intent(DaftarTempat.this, DetailTempat.class);
        intent.putExtra(ModelData.id_tmagang, id_list.getText().toString());
        startActivityForResult(intent, 1);

    }

    @Override
    protected void onResume() {
        super.onResume();
        setup();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1){
//            adapter.clear();
            setup();
        }
    }

    public void halkategori(View view){

        Intent intent = new Intent(DaftarTempat.this, Kategori.class);
        startActivity(intent);
    }

}
