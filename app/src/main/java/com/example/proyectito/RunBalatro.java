package com.example.proyectito;

public class RunBalatro {
    private int id;
    private String baraja;
    private String stake;
    private String seed;
    private double mano;
    private int ante;
    private String notas;
    private String fecha;

    public RunBalatro() {

    }

    public RunBalatro(int id, String baraja, String stake, String seed, double mano, int ante, String notas) {
        this.id = id;
        this.baraja = baraja;
        this.stake = stake;
        this.seed = seed;
        this.mano = mano;
        this.ante = ante;
        this.notas = notas;
    }

    public RunBalatro(String baraja, String stake, String seed, double mano, int ante, String notas) {
        this.baraja = baraja;
        this.stake = stake;
        this.seed = seed;
        this.mano = mano;
        this.ante = ante;
        this.notas = notas;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBaraja() {
        return baraja;
    }

    public void setBaraja(String baraja) {
        this.baraja = baraja;
    }

    public String getStake() {
        return stake;
    }

    public void setStake(String stake) {
        this.stake = stake;
    }

    public String getSeed() {
        return seed;
    }

    public void setSeed(String seed) {
        this.seed = seed;
    }

    public double getMano() {
        return mano;
    }

    public void setMano(double mano) {
        this.mano = mano;
    }

    public int getAnte() {
        return ante;
    }

    public void setAnte(int ante) {
        this.ante = ante;
    }

    public String getNotas() {
        return notas;
    }

    public void setNotas(String notas) {
        this.notas = notas;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public String toString() {
        return baraja + " (" + stake + ") - Ante " + ante;
    }
}
