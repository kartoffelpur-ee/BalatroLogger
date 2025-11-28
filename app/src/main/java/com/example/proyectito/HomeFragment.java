package com.example.proyectito;

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
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;


public class HomeFragment extends Fragment {
    private ImageView imgJimbo;

    public HomeFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imgJimbo = view.findViewById(R.id.img_jimbo);
        Animation animacionEntrada = AnimationUtils.loadAnimation(getContext(), R.anim.jimbo);
        imgJimbo.startAnimation(animacionEntrada);

        imgJimbo.setOnClickListener(view1 -> {
            reproducirSonidoGracioso();
            animarRebote(view1);
        });
    }

    private void reproducirSonidoGracioso() {
        MediaPlayer effectPlayer = MediaPlayer.create(getContext(), R.raw.hit);

        if (effectPlayer != null) {
            effectPlayer.start();

            effectPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    mp.release();
                }
            });
        }
    }

    private void animarRebote(View view) {
        ScaleAnimation rebote = new ScaleAnimation(
                1f, 0.9f,
                1f, 0.9f,
                Animation.RELATIVE_TO_SELF, 0.5f,
                Animation.RELATIVE_TO_SELF, 0.5f);

        rebote.setDuration(100);
        rebote.setRepeatCount(1);
        rebote.setRepeatMode(Animation.REVERSE);

        view.startAnimation(rebote);
    }

}