package com.example.proyectito;

import android.animation.ValueAnimator;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.res.ResourcesCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.util.ArrayList;

public class EstadisticasFragment extends Fragment {
    TextView txtMaxMano, txtBarajaFav, txtAvgAnte, txtMaxAnte;
    ImageView imgBarajaFav;
    ImageView gifJimbola, gifSubeSube;
    LinearLayout datosFinales;
    PieChart pieChart;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_estadisticas, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Basurita basurita = new Basurita();

        txtMaxMano = view.findViewById(R.id.tv_max_mano);
        txtBarajaFav = view.findViewById(R.id.txt_baraja_fav);
        txtAvgAnte = view.findViewById(R.id.txt_avg_ante);
        txtMaxAnte = view.findViewById(R.id.txt_max_ante);
        imgBarajaFav = view.findViewById(R.id.img_baraja_fav);
        gifJimbola = view.findViewById(R.id.gif_jimbola);
        gifSubeSube = view.findViewById(R.id.gif_sube_sube);
        datosFinales = view.findViewById(R.id.layout_final);
        pieChart = view.findViewById(R.id.pie_chart_win_rate);

        Base admin = new Base(getContext(), "balatrito_db", null, 3);

        Animation anim = AnimationUtils.loadAnimation(getContext(), R.anim.flotar_suave);
        datosFinales.startAnimation(anim);

        Glide.with(this).load(R.drawable.jimbola).into(gifJimbola);
        Glide.with(this).load(R.drawable.sube_sube).into(gifSubeSube);

        double manoMax = admin.getMejorMano();
        String barajaFav = admin.getBarajaFavorita();
        int anteMax = admin.getMejorAnte();
        double anteAvg = admin.getPromedioAnte();
        int winRate = admin.getPorcentajeVictorias();

        setupPieChart(winRate);
        animarNumero(txtMaxMano, manoMax);

        if(!barajaFav.equals("Ninguna aun.")) {
            txtBarajaFav.setText(barajaFav);
            imgBarajaFav.setImageResource(basurita.obtenerImagenBaraja(barajaFav));
        } else {
            txtBarajaFav.setText("Ninguna aun.");
            imgBarajaFav.setImageResource(R.drawable.glass_card);
        }

        txtMaxAnte.setText(String.valueOf(anteMax));
        txtAvgAnte.setText(String.format("%.2f", anteAvg));

        admin.close();
    }

    private void setupPieChart(double winRate) {
        float victorias = (float) winRate;
        float derrotas = 100f - victorias;

        ArrayList<PieEntry> entries = new ArrayList<>();
        entries.add(new PieEntry(victorias, " Victorias"));
        entries.add(new PieEntry(derrotas, " Derrotas"));

        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(Color.rgb(76, 175, 80)); // Verde
        colors.add(Color.rgb(244, 67, 54));    // Rojo

        PieDataSet dataSet = new PieDataSet(entries, "Resultados");
        dataSet.setColors(colors);
        dataSet.setValueTextColor(Color.WHITE);
        dataSet.setValueTextSize(12f);
        dataSet.setValueTypeface(ResourcesCompat.getFont(getContext(), R.font.balatro));

        // Formateador personalizado para mostrar porcentaje y etiqueta con la fuente correcta
        dataSet.setValueFormatter(new ValueFormatter() {
            @Override
            public String getPieLabel(float value, PieEntry pieEntry) {
                if (pieChart.isUsePercentValuesEnabled()) {
                    return String.format("%.1f", value) + "%\n" + pieEntry.getLabel();
                }
                return pieEntry.getLabel();
            }
        });

        PieData data = new PieData(dataSet);
        data.setDrawValues(true);

        pieChart.setData(data);
        pieChart.setUsePercentValues(true);
        pieChart.getDescription().setEnabled(false);
        pieChart.getLegend().setEnabled(false);
        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.TRANSPARENT);
        pieChart.setDrawEntryLabels(false);

        pieChart.animateY(1400);
        pieChart.invalidate();
    }

    private void animarNumero(final TextView textView, double valorFinal) {
        ValueAnimator animator = ValueAnimator.ofFloat(0f, (float) valorFinal);
        animator.setDuration(2000);
        animator.setInterpolator(new android.view.animation.DecelerateInterpolator());
        animator.addUpdateListener(animation -> {
            float valorActual = (float) animation.getAnimatedValue();
            textView.setText(String.format("%,.0f", valorActual));
        });
        animator.start();
    }
}