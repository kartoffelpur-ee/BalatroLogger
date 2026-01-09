package com.example.proyectito;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class DetallesFragment extends Fragment implements View.OnClickListener {

    TextView tvBaraja, tvStake, tvSeed, tvMano, tvAnte, tvNotas, tvFecha;
    LinearLayout volver;
    ImageView imgJimbo;
    TextView tvJimboMensaje;
    Button btnElimina, btnModifica;
    int runId;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_detalles, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        tvBaraja = view.findViewById(R.id.detalles_baraja);
        tvStake = view.findViewById(R.id.detalles_stake);
        tvSeed = view.findViewById(R.id.detalles_seed);
        tvMano = view.findViewById(R.id.detalles_mano);
        tvAnte = view.findViewById(R.id.detalles_ante);
        tvNotas = view.findViewById(R.id.detalles_notas);
        tvFecha = view.findViewById(R.id.detalles_fecha);
        volver = view.findViewById(R.id.layout_volver);
        btnElimina = view.findViewById(R.id.btn_elimina);
        btnModifica = view.findViewById(R.id.btn_modifica);

        imgJimbo = view.findViewById(R.id.img_jimbo_reaccion);
        tvJimboMensaje = view.findViewById(R.id.tv_jimbo);

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.flotar_suave);
        imgJimbo.startAnimation(anim);
        tvJimboMensaje.startAnimation(anim);

        Glide.with(this).load(R.drawable.gira_jimbo_gira).into(imgJimbo);

        Bundle args = getArguments();
        if (args != null) {
            runId = args.getInt("runId", -1);
            if (runId != -1) {
                cargarDetallesRun(runId);
            }
        }

        volver.setOnClickListener(this);
        btnElimina.setOnClickListener(this);
        btnModifica.setOnClickListener(this);
    }

    private void cargarDetallesRun(int runId) {
        Base admin = new Base(getContext(), "balatrito_db", null, 3);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM runs WHERE id = " + runId, null);

        if (fila.moveToFirst()) {
            String baraja = fila.getString(1);
            String stake = fila.getString(2);
            String seed = fila.getString(3);
            double mano = fila.getDouble(4);
            int ante = fila.getInt(5);
            String notas = fila.getString(6);
            String fecha = fila.getString(fila.getColumnIndexOrThrow("fecha"));

            tvBaraja.setText(baraja);
            tvStake.setText(stake);
            tvSeed.setText(seed);
            tvMano.setText(String.format("%,.0f", mano));
            tvAnte.setText(String.valueOf(ante));

            if(notas.isEmpty()) {
                tvNotas.setText("No se agregaron notas adicionales.");
            }
            else {
                tvNotas.setText(notas);
            }
            tvFecha.setText("Jugada el " + fecha);

            tvJimboMensaje.setText(mensajitoJimbo(ante));
        }
        else {
            Toast.makeText(getContext(), "No se encontró la partida.", Toast.LENGTH_SHORT).show();
        }
        db.close();
    }

    private String mensajitoJimbo(int ante) {
        if (ante<8) {
            return "Al menos podrías haber ganado, ¿sabes?";
        }
        else if (ante>=8 && ante<11) {
            return "¡Yo podría haberlo hecho mejor!";
        }
        else if (ante>=11 && ante<16) {
            return "Hoy la casa no ganó.";
        }
        else if (ante>16) {
            return "alv papu";
        }
        return "";
    }

    @Override
    public void onClick(View view) {
        if( view.getId() == R.id.layout_volver) {
            getParentFragmentManager().popBackStack();
        }
        else if ( view.getId() == R.id.btn_elimina ) {
            confirmaEliminacion();
        }
        else if ( view.getId() == R.id.btn_modifica ) {
            editaRun();
        }

    }

    private void editaRun() {
        AltaFragment fragmentitoEditadito = new AltaFragment();

        Bundle pasadito = new Bundle();
        pasadito.putInt("idEditadito", runId);
        pasadito.putString("baraja", tvBaraja.getText().toString());
        pasadito.putString("stake", tvStake.getText().toString());
        pasadito.putString("seed", tvSeed.getText().toString());
        pasadito.putString("mano", tvMano.getText().toString().replaceAll("[^0-9]", ""));
        pasadito.putString("ante", tvAnte.getText().toString());
        pasadito.putString("notas", tvNotas.getText().toString());

        fragmentitoEditadito.setArguments(pasadito);

        getParentFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorcito, fragmentitoEditadito)
                .addToBackStack(null)
                .commit();
    }

    private void confirmaEliminacion() {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        View viewDialogo = inflater.inflate(R.layout.dialoguito, null);

        ImageView gifsito = viewDialogo.findViewById(R.id.gif_alerta);
        Glide.with(this).asGif().load(R.drawable.nope).into(gifsito);

        new AlertDialog.Builder(getContext())
                .setView(viewDialogo)
                .setPositiveButton("¡Bórralo!", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        eliminaBasesita();
                    }
                })
                .setNegativeButton("Cancelar", null)
                .show();
    }

    private void eliminaBasesita() {
        Base admin = new Base(getContext(), "balatrito_db", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();

        int filasEliminadas = db.delete("runs", "id="+runId, null);
        db.close();

        if(filasEliminadas>0) {
            Toast.makeText(getContext(), "¡Run eliminada correctamente!", Toast.LENGTH_SHORT).show();
            getParentFragmentManager().popBackStack();
        }
        else {
            Toast.makeText(getContext(), "Error: No se pudo eliminar.", Toast.LENGTH_SHORT).show();
        }
    }
}