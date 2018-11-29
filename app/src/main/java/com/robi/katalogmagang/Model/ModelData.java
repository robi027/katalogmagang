package com.robi.katalogmagang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Robi on 5/2/2017.
 */

public class ModelData {

    @SerializedName("id_tm")
    @Expose
    private String id_tm;

    @SerializedName("nm_tm")
    @Expose
    private String nm_tm;

    @SerializedName("alamat_tm")
    @Expose
    private String alamat_tm;

    @SerializedName("ket_tm")
    @Expose
    private String ket_tm;

    @SerializedName("no_telp")
    @Expose
    private String no_hp;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("team_recruitment")
    @Expose
    private String team_recruitment;

    @SerializedName("bidang")
    @Expose
    private String bidang;

    @SerializedName("jumlah_like")
    @Expose
    private String jumlah_like;

    @SerializedName("latitude")
    @Expose
    private String latitude;

    @SerializedName("longitude")
    @Expose
    private String longitude;


    public static final String id_tmagang = "ID_TEMPATMAGANG";
    public static final String kategori = "KATEGORI";

    public ModelData(String id_tm, String nm_tm, String alamat_tm, String ket_tm){
        this.id_tm = id_tm;
        this.nm_tm = nm_tm;
        this.alamat_tm = alamat_tm;
        this.ket_tm = ket_tm;
    }

    public String getId_tm() {
        return id_tm;
    }

    public void setId_tm(String id_tm) {
        this.id_tm = id_tm;
    }

    public String getNm_tm() {
        return nm_tm;
    }

    public void setNm_tm(String nm_tm) {
        this.nm_tm = nm_tm;
    }

    public String getAlamat_tm() {
        return alamat_tm;
    }

    public void setAlamat_tm(String alamat_tm) {
        this.alamat_tm = alamat_tm;
    }

    public String getKet_tm() {
        return ket_tm;
    }

    public void setKet_tm(String ket_tm) {
        this.ket_tm = ket_tm;
    }

    public String getNo_hp() {
        return no_hp;
    }

    public void setNo_hp(String no_hp) {
        this.no_hp = no_hp;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTeam_recruitment() {
        return team_recruitment;
    }

    public void setTeam_recruitment(String team_recruitment) {
        this.team_recruitment = team_recruitment;
    }

    public String getBidang() {
        return bidang;
    }

    public void setBidang(String bidang) {
        this.bidang = bidang;
    }

    public String getJumlah_like() {
        return jumlah_like;
    }

    public void setJumlah_like(String jumlah_like) {
        this.jumlah_like = jumlah_like;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }
}

