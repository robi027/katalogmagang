package com.robi.katalogmagang.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.robi.katalogmagang.Model.ModelData;
import com.robi.katalogmagang.R;

import java.util.ArrayList;

public class ListArr extends ArrayAdapter<ModelData>{

    private ArrayList<ModelData> listarr;
    private LayoutInflater inflater;
    private int res;

    public ListArr(Context context, int resource, ArrayList<ModelData> listarr){
        super(context, resource, listarr);
        this.listarr = listarr;
        this.inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.res = resource;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        MyHolder holder = null;

        if (convertView == null){
            convertView = inflater.inflate(res, parent, false);

            holder = new MyHolder();
            holder.id_tm = (TextView) convertView.findViewById(R.id.id_tm);
            holder.nm_tm = (TextView) convertView.findViewById(R.id.nama_tm);
            holder.alamat_tm = (TextView) convertView.findViewById(R.id.alamat_tm);
            holder.desc_tm = (TextView) convertView.findViewById(R.id.desc_tm);

            convertView.setTag(holder);

        } else {
            holder = (MyHolder) convertView.getTag();

        }

        holder.id_tm.setText(listarr.get(position).getId_tm());
        holder.nm_tm.setText(listarr.get(position).getNm_tm());
        holder.alamat_tm.setText(listarr.get(position).getAlamat_tm());
        holder.desc_tm.setText(listarr.get(position).getKet_tm());

        return convertView;

    }

    @Override
    public int getCount() {
        return listarr.size();
    }

    @Override
    public void remove(ModelData object) {
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

    static class MyHolder {
        TextView id_tm;
        TextView nm_tm;
        TextView alamat_tm;
        TextView desc_tm;
    }
}
