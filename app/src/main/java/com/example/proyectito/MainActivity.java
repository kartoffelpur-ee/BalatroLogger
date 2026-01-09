package com.example.proyectito;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.GravityCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.appbar.MaterialToolbar;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private MediaPlayer mediaPlayer;
    private DrawerLayout cajonDeNavegacion;
    private NavigationView vistaDeNavegacion;
    private MaterialToolbar barraDeHerramientas;
    private ImageView crtOverlay;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        cajonDeNavegacion = findViewById(R.id.drawer_layout);
        vistaDeNavegacion = findViewById(R.id.nav_view);
        barraDeHerramientas = findViewById(R.id.top_app_bar);
        crtOverlay = findViewById(R.id.crt_overlay);

        // Cargamos el GIF del filtro CRT en bucle
        Glide.with(this).asGif().load(R.drawable.scanlinesgif2).into(crtOverlay);

        // Anulamos el tinte de los íconos del menú
        vistaDeNavegacion.setItemIconTintList(null);

        ViewCompat.setOnApplyWindowInsetsListener(cajonDeNavegacion, (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        setSupportActionBar(barraDeHerramientas);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        ActionBarDrawerToggle alternador = new ActionBarDrawerToggle(
                this,
                cajonDeNavegacion,
                barraDeHerramientas,
                R.string.navigation_drawer_open,
                R.string.navigation_drawer_close);
        cajonDeNavegacion.addDrawerListener(alternador);
        alternador.syncState();

        vistaDeNavegacion.setNavigationItemSelectedListener(this);

        if (savedInstanceState == null) {
            vistaDeNavegacion.setCheckedItem(R.id.nav_home);
            reemplazarFragmento(new HomeFragment());
        }

        mediaPlayer = MediaPlayer.create(this, R.raw.main_theme);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void reemplazarFragmento(Fragment fragmento) {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.contenedorcito, fragmento)
                .commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragmento;
        int idElemento = menuItem.getItemId();

        if (idElemento == R.id.nav_registro) {
            fragmento = new AltaFragment();
        }
        else if (idElemento == R.id.nav_lista) {
            fragmento = new ListaFragment();
        }
        else if (idElemento == R.id.nav_estadisticas) {
            fragmento = new EstadisticasFragment();
        }
        else {
            fragmento = new HomeFragment();
        }

        reemplazarFragmento(fragmento);
        cajonDeNavegacion.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mediaPlayer != null) {
            mediaPlayer.start();
        }
    }

    @Override
    public void onPause() {
        super.onPause();

        if (mediaPlayer != null) {
            mediaPlayer.pause();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}