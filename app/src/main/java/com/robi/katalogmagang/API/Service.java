package com.robi.katalogmagang.API;

import com.robi.katalogmagang.Model.ModelData;
import com.robi.katalogmagang.Model.ModelDataKategori;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {

//    @GET("bo-getdata.php")
//    Call<List<ModelData>> getSemuaTempat();

    @GET("bo-getdatanew.php")
    Call<List<ModelData>> getSemuaTempat();

    @GET("bo-getalldata.php")
    Call<List<ModelData>> getSingleData(@Query("id_tm") String id_tm);

//    @GET("bo-getdatakategori.php")
//    Call<List<ModelData>> getdatakategori(@Query("bidang") String bidang);

    @GET("bo-getallkategori.php")
    Call<List<ModelDataKategori>> getSemuaKategori();

    @GET("bo-getdatakategorinew.php")
    Call<List<ModelData>> getdatakategori(@Query("id_kategoritm") String id_kategoritm);

    @GET("bo-getdatasearch.php")
    Call<List<ModelData>> getdatasearch(@Query("searchdata") String searchdata);
}
