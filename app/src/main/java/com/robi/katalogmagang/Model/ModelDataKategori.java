package com.robi.katalogmagang.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Robi on 5/15/2017.
 */

public class ModelDataKategori {

    @SerializedName("id_kategoritm")
    @Expose
    private String id_kategoritm;

    @SerializedName("kategoritm")
    @Expose
    private String kategoritm;

    public static final String id_kategori = "SEMUAKATEGORI";

    public ModelDataKategori(String id_kategoritm, String kategoritm){
        this.id_kategoritm = id_kategoritm;
        this.kategoritm = kategoritm;

    }

    public String getId_kategoritm() {
        return id_kategoritm;
    }

    public void setId_kategoritm(String id_kategoritm) {
        this.id_kategoritm = id_kategoritm;
    }

    public String getKategoritm() {
        return kategoritm;
    }

    public void setKategoritm(String kategoritm) {
        this.kategoritm = kategoritm;
    }

}
