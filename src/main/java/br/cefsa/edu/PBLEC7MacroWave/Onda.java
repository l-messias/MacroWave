package br.cefsa.edu.PBLEC7MacroWave;

public class Onda {

    private int id;
    private int amplitude;
    private int frequencia;
    private int fase;
    private String categoria;

    public Onda() {
    }

    public Onda(int amplitude, int frequencia, int fase, String categoria) {
        this.amplitude = amplitude;
        this.frequencia = frequencia;
        this.fase = fase;
        this.categoria = categoria;
    }

    public Onda(int id, int amplitude, int frequencia, int fase, String categoria) {
        this.id = id;
        this.amplitude = amplitude;
        this.frequencia = frequencia;
        this.fase = fase;
        this.categoria = categoria;
    }

    public int getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getFrequencia() {
        return frequencia;
    }

    public void setFrequencia(int frequencia) {
        this.frequencia = frequencia;
    }

    public int getFase() {
        return fase;
    }

    public void setFase(int fase) {
        this.fase = fase;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

}
