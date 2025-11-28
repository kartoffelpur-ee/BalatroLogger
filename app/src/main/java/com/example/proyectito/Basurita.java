package com.example.proyectito;

public class Basurita {

    public int[] traeImagenes() {
        return new int[] {
                R.drawable.red_deck,
                R.drawable.blue_deck,
                R.drawable.yellow_deck,
                R.drawable.green_deck,
                R.drawable.black_deck,
                R.drawable.magic_deck,
                R.drawable.nebula_deck,
                R.drawable.ghost_deck,
                R.drawable.abandoned_deck,
                R.drawable.checkered_deck,
                R.drawable.zodiac_deck,
                R.drawable.painted_deck,
                R.drawable.anaglyph_deck,
                R.drawable.plasma_deck,
                R.drawable.erratic_deck
        };
    }

    public String[] traeBarajitas() {
        return new String[] {
                "Baraja roja",
                "Baraja azul",
                "Baraja amarilla",
                "Baraja verde",
                "Baraja negra",
                "Baraja mágica",
                "Baraja nébula",
                "Baraja fantasma",
                "Baraja abandonada",
                "Baraja cuadriculada",
                "Baraja zodiacal",
                "Baraja pintada",
                "Baraja anaglífo",
                "Baraja plasmática",
                "Baraja errática"
        };
    }

    public int obtenerImagenBaraja(String baraja) {
        if (baraja.equals("Baraja roja")) return R.drawable.red_deck;
        if (baraja.equals("Baraja azul")) return R.drawable.blue_deck;
        if (baraja.equals("Baraja amarilla")) return R.drawable.yellow_deck;
        if (baraja.equals("Baraja verde")) return R.drawable.green_deck;
        if (baraja.equals("Baraja negra")) return R.drawable.black_deck;
        if (baraja.equals("Baraja mágica")) return R.drawable.magic_deck;
        if (baraja.equals("Baraja nébula")) return R.drawable.nebula_deck;
        if (baraja.equals("Baraja fantasma")) return R.drawable.ghost_deck;
        if (baraja.equals("Baraja abandonada")) return R.drawable.abandoned_deck;
        if (baraja.equals("Baraja cuadriculada")) return R.drawable.checkered_deck;
        if (baraja.equals("Baraja zodiacal")) return R.drawable.zodiac_deck;
        if (baraja.equals("Baraja pintada")) return R.drawable.painted_deck;
        if (baraja.equals("Baraja anaglífo")) return R.drawable.anaglyph_deck;
        if (baraja.equals("Baraja plasmática")) return R.drawable.plasma_deck;
        if (baraja.equals("Baraja errática")) return R.drawable.erratic_deck;

        return R.drawable.cards;
    }
}
