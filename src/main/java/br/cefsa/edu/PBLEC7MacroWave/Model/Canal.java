package br.cefsa.edu.PBLEC7MacroWave.Model;

public enum Canal {
    PASSABAIXAS("Passa-Baixas"), PASSAFAIXAS("Passa-Faixas");

    private final String value;

    Canal(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}