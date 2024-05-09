package br.cefsa.edu.PBLEC7MacroWave.Model;

public enum Categoria {
    QUADRADA("Quadrada"), TRIANGULAR("Triangular"), DENTEDESERRA("Dente-de-serra"), SENOIDAL( "Senoidal Retificada");

    private final String value;

    Categoria(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
