package com.example.proyectito;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
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
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;


public class AltaFragment extends Fragment implements View.OnClickListener {
    Spinner spinnerMazos;
    Button btnGuardar;
    RadioGroup rgStake, rgStake2;
    RadioButton rbRed, rbWhite, rbGreen, rbBlack, rbBlue, rbPurple, rbOrange, rbGold;
    EditText etSeed, etMano, etAnte, etNotas;

    int idEdit = -1;
    boolean editando = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alta, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Basurita basurita = new Basurita();

        spinnerMazos = view.findViewById(R.id.spinner_mazos);
        btnGuardar = view.findViewById(R.id.btn_guardar);
        rgStake = view.findViewById(R.id.radioGroup_stake);
        rgStake2 = view.findViewById(R.id.radioGroup_stake2);
        rbRed = view.findViewById(R.id.rb_red);
        rbWhite = view.findViewById(R.id.rb_white);
        rbGreen = view.findViewById(R.id.rb_green);
        rbBlack = view.findViewById(R.id.rb_black);
        rbBlue = view.findViewById(R.id.rb_blue);
        rbPurple = view.findViewById(R.id.rb_purple);
        rbOrange = view.findViewById(R.id.rb_orange);
        rbGold = view.findViewById(R.id.rb_gold);
        etSeed = view.findViewById(R.id.et_seed);
        etMano = view.findViewById(R.id.et_mano);
        etAnte = view.findViewById(R.id.et_ante);
        etNotas = view.findViewById(R.id.et_notas);

        String[] mazosTextito = basurita.traeBarajitas();
        int[] mazosImagenes = basurita.traeImagenes();

        btnGuardar.setOnClickListener(this);
        rbRed.setOnClickListener(this);
        rbWhite.setOnClickListener(this);
        rbGreen.setOnClickListener(this);
        rbBlack.setOnClickListener(this);
        rbBlue.setOnClickListener(this);
        rbPurple.setOnClickListener(this);
        rbOrange.setOnClickListener(this);
        rbGold.setOnClickListener(this);

        MazoAdapter adaptadito = new MazoAdapter(getContext(), mazosTextito, mazosImagenes);
        spinnerMazos.setAdapter(adaptadito);

        if(getArguments() != null && getArguments().containsKey("idEditadito")) {
            taEditando();
        }
    }

    private void taEditando() {
        editando = true;
        idEdit = getArguments().getInt("idEditadito");

        btnGuardar.setText("ACTUALIZAR RUN");

        String barajita = getArguments().getString("baraja");
        MazoAdapter adaptadito = (MazoAdapter) spinnerMazos.getAdapter();
        spinnerMazos.setSelection(adaptadito.getPosition(barajita));

        String stakesito = getArguments().getString("stake");
        buscarStakesito(stakesito, rgStake, rgStake2);
        
        etSeed.setText(getArguments().getString("seed"));
        etMano.setText(getArguments().getString("mano"));
        etAnte.setText(getArguments().getString("ante"));
        etNotas.setText(getArguments().getString("notas"));
    }

    private void buscarStakesito(String textito, RadioGroup... grupos) {
        for(RadioGroup grupo: grupos) {
            for(int i=0; i < grupo.getChildCount(); i++) {
                View v = grupo.getChildAt(i);
                if(v instanceof RadioButton) {
                    RadioButton rb = (RadioButton) v;

                    if( idAString(rb.getId()).equals(textito) ) {
                        rb.setChecked(true);
                        return;
                    };
                }
            }
        }
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();

        if (id == R.id.btn_guardar) {
            insertaBD();
            return;
        }
        

        if(id==R.id.rb_red||id==R.id.rb_white||id==R.id.rb_green||id==R.id.rb_black){
            rgStake2.clearCheck();
        }
        else if (id==R.id.rb_blue||id==R.id.rb_purple||id==R.id.rb_orange||id==R.id.rb_gold){
            rgStake.clearCheck();
        }

    }

    private void insertaBD() {
        Base admin = new Base(getContext(), "balatrito_db", null, 3);
        SQLiteDatabase db = admin.getWritableDatabase();

        String seedTxt = etSeed.getText().toString().toUpperCase();
        String manoTxt = etMano.getText().toString();
        String anteTxt = etAnte.getText().toString();
        String notasTxt = etNotas.getText().toString();

        if (seedTxt.isEmpty() || manoTxt.isEmpty() || anteTxt.isEmpty()) {
            Toast.makeText(getContext(), "¡Faltan datos (Seed, mano o ante)!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (seedTxt.contains("o") || seedTxt.contains("O") || seedTxt.contains("0")) {
            Toast.makeText(getContext(), "Las seeds en balatro no contienen la letra O ni el número cero.", Toast.LENGTH_SHORT).show();
            return;
        }

        String barajaTxt = "";
        if (spinnerMazos.getSelectedItem() != null) {
            barajaTxt = spinnerMazos.getSelectedItem().toString();
        }

        String stakeTxt = obtenerStake();
        if (stakeTxt.isEmpty()) {
            Toast.makeText(getContext(), "¡Selecciona una dificultad!", Toast.LENGTH_SHORT).show();
            return;
        }

        ContentValues registro = new ContentValues();
        registro.put("baraja", barajaTxt);
        registro.put("stake", stakeTxt);
        registro.put("seed", seedTxt);
        registro.put("mano", Double.parseDouble(manoTxt));
        registro.put("ante", Integer.parseInt(anteTxt));
        registro.put("notas", notasTxt);

        if(!editando) {
            db.insert("runs", null, registro);
            db.close();
            Toast.makeText(getContext(), "Se guardó la run con éxito.", Toast.LENGTH_SHORT).show();
            limpia();
        }
        else {
            int cant = db.update("runs", registro, "id="+idEdit, null);
            db.close();

            if(cant>0) {
                Toast.makeText(getContext(), "Se actualizó la run con éxito.", Toast.LENGTH_SHORT).show();
            }
            getParentFragmentManager().popBackStack();
        }
    }

    private void limpia() {
        etSeed.setText("");
        etMano.setText("");
        etAnte.setText("");
        etNotas.setText("");
        rgStake.clearCheck();
        rgStake2.clearCheck();
        spinnerMazos.setSelection(0);
    }

    private String obtenerStake() {
        int id1 = rgStake.getCheckedRadioButtonId();
        if (id1 != -1) return idAString(id1);

        int id2 = rgStake2.getCheckedRadioButtonId();
        if (id2 != -1) return idAString(id2);

        return "";
    }

    private String idAString(int id) {
        if (id == R.id.rb_white) return "Pozo blanco";
        else if (id == R.id.rb_red) return "Pozo rojo";
        else if (id == R.id.rb_green) return "Pozo verde";
        else if (id == R.id.rb_black) return "Pozo negro";
        else if (id == R.id.rb_blue) return "Pozo azul";
        else if (id == R.id.rb_purple) return "Pozo morado";
        else if (id == R.id.rb_orange) return "Pozo naranja";
        else if (id == R.id.rb_gold) return "Pozo dorado";
        else return "";
    }
}