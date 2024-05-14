package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Onda {
    //region Variáveis
    private int id;
    private int frequencia;
    private Categoria categoria;
    private Canal canal;
    private int frequenciaDeCorteInf; // Frequência de Corte Inferior
    private int frequenciaDeCorteSup; // Frequência de Corte Superior (Caso o canal seja Passa-Faixas)
    //endregion

    //region Construtores
    public Onda() {
    }

    public Onda(int frequenciaDeCorteSup, Canal canal, Categoria categoria, int frequencia) {
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
        this.canal = canal;
        this.categoria = categoria;
        this.frequencia = frequencia;
    }

    public Onda(int frequencia, Categoria categoria, Canal canal, int frequenciaDeCorteInf, int frequenciaDeCorteSup) {
        this.frequencia = frequencia;
        this.categoria = categoria;
        this.canal = canal;
        this.frequenciaDeCorteInf = frequenciaDeCorteInf;
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
    }

    public Onda(int id, int frequencia, Categoria categoria, Canal canal, int frequenciaDeCorteSup) {
        this.id = id;
        this.frequencia = frequencia;
        this.categoria = categoria;
        this.canal = canal;
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
    }

    public Onda(int id, int frequencia, Categoria categoria, Canal canal, int frequenciaDeCorteInf, int frequenciaDeCorteSup) {
        this.id = id;
        this.frequencia = frequencia;
        this.categoria = categoria;
        this.canal = canal;
        this.frequenciaDeCorteInf = frequenciaDeCorteInf;
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
    }
    //endregion

    //region Getters e Setters
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

    public Categoria getCategoria() {
        return categoria;
    }

    public void setCategoria(Categoria categoria) {
        this.categoria = categoria;
    }

    public Canal getCanal() {
        return canal;
    }

    public void setCanal(Canal canal) {
        this.canal = canal;
    }

    public int getFrequenciaDeCorteInf() {
        return frequenciaDeCorteInf;
    }

    public void setFrequenciaDeCorteInf(int frequenciaDeCorteInf) {
        this.frequenciaDeCorteInf = frequenciaDeCorteInf;
    }

    public int getFrequenciaDeCorteSup() {
        return frequenciaDeCorteSup;
    }

    public void setFrequenciaDeCorteSup(int frequenciaDeCorteSup) {
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
    }
    //endregion

    //region Métodos para cálculos (Considerando 100 primeiras harmônicas)
    // Método para calcular a amplitude das componentes de frequência do sinal de entrada
    public Map<Integer, Double> calcularAmplitudeFourier() {
        Map<Integer, Double> amplitudes = new HashMap<>();

        // Frequência fundamental
        double f0 = frequencia * 1000; // Converter kHz para Hz

        // Amplitude da componente de frequência fundamental
        double amplitudeFundamental = calcularAmplitudeFundamental(f0);
        amplitudes.put(1, amplitudeFundamental);

        // Calcular as amplitudes das harmônicas
        for (int i = 2; i <= 100; i++) {
            double frequenciaHarmonica = f0 * i;
            double amplitudeHarmonica = calcularAmplitudeHarmonica(frequenciaHarmonica);
            amplitudes.put(i, amplitudeHarmonica);
        }

        return amplitudes;
    }

    // Método para calcular a fase das componentes de frequência do sinal de entrada
    public Map<Integer, Double> calcularFaseFourier() {
        Map<Integer, Double> fases = new HashMap<>();

        // Fase da componente de frequência fundamental (normalmente é zero)
        fases.put(1, 0.0);

        // Calcular as fases das harmônicas (pode ser zero para ondas simétricas)
        for (int i = 2; i <= 100; i++) {
            // Defina as fases conforme necessário
            // Por exemplo, fases.put(i, Math.PI / 2.0) para defasagem de 90 graus
            fases.put(i, 0.0); // Para simplificar, estamos assumindo que as fases são zero
        }

        return fases;
    }

    // Método auxiliar para calcular a amplitude da componente de frequência fundamental
    private double calcularAmplitudeFundamental(double frequencia) {
        double amplitude = 0.0;

        // Implemente os cálculos para cada tipo de onda
        switch (categoria) {
            case QUADRADA:
                amplitude = 4 / Math.PI; // Ajuste conforme necessário
                break;
            case TRIANGULAR:
                amplitude = 8 / (Math.PI * Math.PI); // Ajuste conforme necessário
                break;
            case DENTEDESERRA:
                amplitude = 2 / Math.PI; // Ajuste conforme necessário
                break;
            case SENOIDAL:
                amplitude = 1.0; // Amplitude padrão para uma senoide
                break;
        }

        return amplitude;
    }

    // Método auxiliar para calcular a amplitude das harmônicas
    private double calcularAmplitudeHarmonica(double frequencia) {
        double amplitude = 0.0;

        // Implemente os cálculos para cada tipo de onda
        switch (categoria) {
            case QUADRADA:
                amplitude = 4 / (Math.PI * frequencia); // Ajuste conforme necessário
                break;
            case TRIANGULAR:
                amplitude = 8 / (Math.PI * Math.PI * frequencia * frequencia); // Ajuste conforme necessário
                break;
            case DENTEDESERRA:
                amplitude = 2 / (Math.PI * frequencia); // Ajuste conforme necessário
                break;
            case SENOIDAL:
                amplitude = 1.0; // Amplitude padrão para uma senoide
                break;
        }

        return amplitude;
    }

    public Map<Integer, Double> calcularModuloRespostaCanal() {
        Map<Integer, Double> moduloResposta = new HashMap<>();

        // Frequência de corte
        double fc = canal == Canal.PASSABAIXAS ? frequenciaDeCorteSup : 0.0; // Para Passa-Baixas
        double f1 = canal == Canal.PASSAFAIXAS ? frequenciaDeCorteInf : 0.0; // Para Passa-Faixas
        double f2 = canal == Canal.PASSAFAIXAS ? frequenciaDeCorteSup : 0.0; // Para Passa-Faixas

        // Calcular o módulo da resposta em frequência para cada frequência
        for (int i = 1; i <= 100; i++) {
            double frequencia = i * 1000; // Converter kHz para Hz

            // Implemente os cálculos conforme necessário para cada tipo de canal
            double modulo = calcularModuloResposta(frequencia, fc, f1, f2);
            moduloResposta.put(i, modulo);
        }

        return moduloResposta;
    }

    // Método para calcular a fase da resposta em frequência do canal
    public Map<Integer, Double> calcularFaseRespostaCanal() {
        Map<Integer, Double> faseResposta = new HashMap<>();

        // Defina as fases conforme necessário
        // Por exemplo, faseResposta.put(1, Math.PI / 2.0) para defasagem de 90 graus

        // Ajuste conforme necessário para lidar com o tipo de canal (Passa-Baixas ou Passa-Faixas)

        return faseResposta;
    }

    // Método auxiliar para calcular o módulo da resposta em frequência do canal
    private double calcularModuloResposta(double frequencia, double fc, double f1, double f2) {
        double modulo = 0.0;

        // Implemente os cálculos para cada tipo de canal
        if (canal == Canal.PASSABAIXAS) {
            // Para um canal Passa-Baixas
            modulo = 1 / (1 + Math.pow(frequencia / fc, 2));
        } else if (canal == Canal.PASSAFAIXAS) {
            // Para um canal Passa-Faixas
            modulo = (1 / (1 + Math.pow(frequencia / f1, 2))) * (1 / (1 + Math.pow(f2 / frequencia, 2)));
        }

        return modulo;
    }

    // Método para calcular a amplitude das componentes de frequência do sinal resultante
    public Map<Integer, Double> calcularAmplitudeSinalResultante(Map<Integer, Double> amplitudesEntrada, Map<Integer, Double> moduloCanal) {
        Map<Integer, Double> amplitudesResultante = new HashMap<>();

        // Calcular a amplitude do sinal resultante para cada componente de frequência
        for (int i = 1; i <= 100; i++) {
            double amplitudeEntrada = amplitudesEntrada.getOrDefault(i, 0.0);
            double modulo = moduloCanal.getOrDefault(i, 0.0);
            double amplitudeResultante = amplitudeEntrada * modulo;
            amplitudesResultante.put(i, amplitudeResultante);
        }

        return amplitudesResultante;
    }

    // Método para calcular a fase das componentes de frequência do sinal resultante
    public Map<Integer, Double> calcularFaseSinalResultante(Map<Integer, Double> fasesEntrada, Map<Integer, Double> faseCanal) {
        Map<Integer, Double> fasesResultante = new HashMap<>();

        // Calcular a fase do sinal resultante para cada componente de frequência
        for (int i = 1; i <= 100; i++) {
            double faseEntrada = fasesEntrada.getOrDefault(i, 0.0);
            double fase = faseCanal.getOrDefault(i, 0.0);
            double faseResultante = faseEntrada + fase;
            fasesResultante.put(i, faseResultante);
        }

        return fasesResultante;
    }

    //endregion
}
