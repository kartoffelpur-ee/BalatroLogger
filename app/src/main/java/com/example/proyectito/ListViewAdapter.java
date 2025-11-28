package com.example.proyectito;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class ListViewAdapter extends BaseAdapter {
    Context contexto;
    ArrayList<RunBalatro> listita;
    LayoutInflater inflater;

    public ListViewAdapter(Context contexto, ArrayList<RunBalatro> listita) {
        this.contexto = contexto;
        this.listita = listita;
        this.inflater = LayoutInflater.from(contexto);
    }

    @Override
    public int getCount() {
        return listita.size();
    }

    @Override
    public Object getItem(int position) {
        return listita.get(position);
    }

    @Override
    public long getItemId(int position) {
        return listita.get(position).getId();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = inflater.inflate(R.layout.list_view_basurita, null);

        Basurita basurita = new Basurita();
        RunBalatro run = listita.get(position); // esto va a traer el objetito del arraysito

        ImageView imgBaraja = convertView.findViewById(R.id.img_baraja_item);
        TextView txtBarajaStake = convertView.findViewById(R.id.txt_stake);
        TextView txtSeedAnte = convertView.findViewById(R.id.txt_ante);
        TextView txtManoMaxima = convertView.findViewById(R.id.txt_mano_maxima);
        TextView txtId = convertView.findViewById(R.id.txt_id);

        txtBarajaStake.setText(run.getStake());
        txtSeedAnte.setText("Ante: "+run.getAnte());
        txtManoMaxima.setText("Mano más alta: "+String.format("%,.0f", run.getMano()));
        txtId.setText("#"+run.getId());

        int idImagen = basurita.obtenerImagenBaraja(run.getBaraja());
        imgBaraja.setImageResource(idImagen);

        return convertView;
    }
}
