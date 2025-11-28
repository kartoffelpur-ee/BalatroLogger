package com.example.proyectito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class MazoAdapter extends ArrayAdapter<String> {

    private Context mContext;
    private String[] mNombres;
    private int[] mImagenes;

    public MazoAdapter(Context context, String[] nombres, int[] imagenes) {
        super(context, R.layout.item_spinner_mazo, R.id.txt_nombre_mazo, nombres);
        this.mContext = context;
        this.mNombres = nombres;
        this.mImagenes = imagenes;
    }

    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return getCustomView(position, convertView, parent);
    }

    public View getCustomView(int position, View convertView, ViewGroup parent) {
        View row = convertView;
        if(row == null) {
            LayoutInflater inflater = LayoutInflater.from(mContext);
            row = inflater.inflate(R.layout.item_spinner_mazo, parent, false);

        }

        TextView label = row.findViewById(R.id.txt_nombre_mazo);
        ImageView icon = row.findViewById(R.id.img_mazo);

        label.setText(mNombres[position]);
        icon.setImageResource(mImagenes[position]);

        return row;
    }
}