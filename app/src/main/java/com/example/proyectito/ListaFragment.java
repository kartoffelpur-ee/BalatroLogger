package com.example.proyectito;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class ListaFragment extends Fragment implements AdapterView.OnItemClickListener {
    ListView listView;
    ArrayList<RunBalatro> runsitas;
    ListViewAdapter adaptadito;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_lista, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listView = view.findViewById(R.id.lista_runs);
        runsitas = new ArrayList<>();

        cargarDatosBD();

        adaptadito = new ListViewAdapter(getContext(), runsitas);
        listView.setAdapter(adaptadito);

        listView.setOnItemClickListener(this);
    }

    private void cargarDatosBD() {
        Base admin = new Base(getContext(), "balatrito_db", null, 2);
        SQLiteDatabase db = admin.getReadableDatabase();

        Cursor fila = db.rawQuery("SELECT * FROM runs ORDER BY id ASC", null);

        if (fila.moveToFirst()) {
            do {
                RunBalatro run = new RunBalatro();

                run.setId(fila.getInt(0));
                run.setBaraja(fila.getString(1));
                run.setStake(fila.getString(2));
                run.setSeed(fila.getString(3));
                run.setMano(fila.getDouble(4));
                run.setAnte(fila.getInt(5));
                run.setNotas(fila.getString(6));

                runsitas.add(run);
            }
            while (fila.moveToNext());
        }
        else {
            Toast.makeText(getContext(),
                    "¡No hay partidas guardadas aún!",
                    Toast.LENGTH_SHORT)
                    .show();
        }
        db.close();
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        RunBalatro runSeleccionado = runsitas.get(i);

        Bundle bundle = new Bundle();
        bundle.putInt("runId", runSeleccionado.getId());

        DetallesFragment detallesFragment = new DetallesFragment();
        detallesFragment.setArguments(bundle);

        getParentFragmentManager().beginTransaction()
                .replace(R.id.contenedorcito, detallesFragment)
                .addToBackStack(null)
                .commit();
    }
}
