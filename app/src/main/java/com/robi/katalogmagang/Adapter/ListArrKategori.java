package com.robi.katalogmagang.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.robi.katalogmagang.Model.ModelDataKategori;
import com.robi.katalogmagang.R;

import java.util.ArrayList;

public class ListArrKategori extends ArrayAdapter<ModelDataKategori> {

    private ArrayList<ModelDataKategori> listarrkategori;
    private LayoutInflater inflater;
    private int res;

    public ListArrKategori(Context context, int resource, ArrayList<ModelDataKategori> listarrkategori){
        super(context, resource, listarrkategori);
        this.listarrkategori = listarrkategori;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        HolderSaya holderSaya = null;

        if (convertView == null){
            convertView = inflater.inflate(res, parent, false);

            holderSaya = new HolderSaya();
            holderSaya.id_kategoritm = (TextView) convertView.findViewById(R.id.id_kategoritm);
            holderSaya.kategoritm = (TextView) convertView.findViewById(R.id.kategoritm);

            convertView.setTag(holderSaya);
        }else{
            holderSaya = (HolderSaya) convertView.getTag();
        }

        holderSaya.id_kategoritm.setText(listarrkategori.get(position).getId_kategoritm());
        holderSaya.kategoritm.setText(listarrkategori.get(position).getKategoritm());

        return convertView;
    }

    @Override
    public int getCount() {
        return listarrkategori.size();
    }

    @Override
    public void remove(ModelDataKategori object) {
        super.remove(object);
    }

    @Override
    public void clear() {
        super.clear();
    }

    @Override
    public void notifyDataSetChanged() {
        super.notifyDataSetChanged();
    }

    static class HolderSaya{
        TextView id_kategoritm;
        TextView kategoritm;

    }
}
