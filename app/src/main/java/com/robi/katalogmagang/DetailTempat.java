package com.robi.katalogmagang;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.github.clans.fab.FloatingActionButton;
import com.github.clans.fab.FloatingActionMenu;
import com.robi.katalogmagang.API.Service;
import com.robi.katalogmagang.Model.ModelData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DetailTempat extends AppCompatActivity {

    String ID_TEMPATMAGANG;
    TextView nm_tm, alamat_tm, ket_tm, team_recruitment, bidang;
    String title;
    String no_hp, email;

    FloatingActionMenu floatingActionMenu;
    FloatingActionButton floatingActionButton1;
    FloatingActionButton floatingActionButton2;
    FloatingActionButton floatingActionButton3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_tempat);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle("Detail Tempat");

        ID_TEMPATMAGANG = getIntent().getStringExtra(ModelData.id_tmagang);

        nm_tm = (TextView) findViewById(R.id.nama_tm);
        alamat_tm = (TextView) findViewById(R.id.alamat_tm);
        ket_tm = (TextView) findViewById(R.id.ket_tm);
        team_recruitment = (TextView) findViewById(R.id.team_recruitment);
        bidang= (TextView) findViewById(R.id.bidang);

        bindData();
        actionbutton();
    }

    private void bindData() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(MainActivity.getInstance())
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Service service = retrofit.create(Service.class);
        Log.d("DetailTempat", ID_TEMPATMAGANG);

        Call<List<ModelData>> call = service.getSingleData(ID_TEMPATMAGANG);
        call.enqueue(new Callback<List<ModelData>>() {
            @Override
            public void onResponse(Call<List<ModelData>> call, Response<List<ModelData>> response) {

                Log.d("get", response.body().toString());

                if (response.isSuccessful()) {
                    for (int i = 0; i < response.body().size(); i++) {

                        nm_tm.setText(response.body().get(i).getNm_tm());
                        alamat_tm.setText(response.body().get(i).getAlamat_tm());
                        ket_tm.setText(response.body().get(i).getKet_tm());
                        team_recruitment.setText(response.body().get(i).getTeam_recruitment());
                        bidang.setText(response.body().get(i).getBidang());
                        no_hp = String.valueOf(response.body().get(i).getNo_hp());
                        email= String.valueOf(response.body().get(i).getEmail());

                    }
                }
            }

            @Override
            public void onFailure(Call<List<ModelData>> call, Throwable t) {
            }
        });
    }

    public void actionbutton() {
        floatingActionMenu = (FloatingActionMenu) findViewById(R.id.material_design_android_floating_action_menu);
        floatingActionButton1 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item1);
        floatingActionButton2 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item2);
        floatingActionButton3 = (FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);

        floatingActionButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + no_hp));
                if (ActivityCompat.checkSelfPermission(DetailTempat.this, android.Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(intent);

            }
        });

        floatingActionButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setType("vnd.android-dir/mms-sms");
                intent.putExtra("address", no_hp);
                intent.putExtra("sms_body","");
                startActivity(intent);
            }
        });

        floatingActionButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_EMAIL, new String[]{email});
                intent.setType("message/rcf822");
                startActivity(Intent.createChooser(intent,"Kirim Email Menggunakan"));
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
}

