package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.ArrayList;
import java.util.List;

public class Onda {
    //region Variáveis
    private int id;
    private int amplitude;
    private int frequencia;
    private int fase;
    private Categoria categoria;
    private Canal canal;
    private int frequenciaDeCorteInf; // Frequência de Corte Inferior
    private int frequenciaDeCorteSup; // Frequência de Corte Superior (Caso o canal seja Passa-Faixas)
    //endregion

    //region Construtores
    public Onda() {
    }

    public Onda(int frequenciaDeCorteSup, Canal canal, Categoria categoria, int fase, int frequencia, int amplitude) {
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
        this.canal = canal;
        this.categoria = categoria;
        this.fase = fase;
        this.frequencia = frequencia;
        this.amplitude = amplitude;
    }

    public Onda(int amplitude, int frequencia, Categoria categoria, int fase, Canal canal, int frequenciaDeCorteInf, int frequenciaDeCorteSup) {
        this.amplitude = amplitude;
        this.frequencia = frequencia;
        this.categoria = categoria;
        this.fase = fase;
        this.canal = canal;
        this.frequenciaDeCorteInf = frequenciaDeCorteInf;
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
    }

    public Onda(int id, int amplitude, int frequencia, int fase, Categoria categoria, Canal canal, int frequenciaDeCorteSup) {
        this.id = id;
        this.amplitude = amplitude;
        this.frequencia = frequencia;
        this.fase = fase;
        this.categoria = categoria;
        this.canal = canal;
        this.frequenciaDeCorteSup = frequenciaDeCorteSup;
    }

    public Onda(int id, int amplitude, int frequencia, int fase, Categoria categoria, Canal canal, int frequenciaDeCorteInf, int frequenciaDeCorteSup) {
        this.id = id;
        this.amplitude = amplitude;
        this.frequencia = frequencia;
        this.fase = fase;
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

    public int getAmplitude() {
        return amplitude;
    }

    public void setAmplitude(int amplitude) {
        this.amplitude = amplitude;
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

    // Método para calcular o sinal de entrada ao longo do tempo
    public List<Double> calcularSinalEntrada(int duracao, double intervalo) {
        List<Double> sinalEntrada = new ArrayList<>();

        for (int i = 0; i < duracao; i++) {
            double tempo = i * intervalo;
            double valor = 0.0;

            switch (categoria) {
                case QUADRADA:
                    valor = calcularQuadrada(tempo);
                    break;
                case TRIANGULAR:
                    valor = calcularTriangular(tempo);
                    break;
                case DENTEDESERRA:
                    valor = calcularDenteDeSerra(tempo);
                    break;
                case SENOIDAL:
                    valor = calcularSenoidalRetificada(tempo);
                    break;
            }

            if (canal == Canal.PASSABAIXAS) {
                valor = filtrarPassaBaixas(valor);
            } else if (canal == Canal.PASSAFAIXAS) {
                valor = filtrarPassaFaixas(valor);
            }

            sinalEntrada.add(valor);
        }

        return sinalEntrada;
    }

    // Métodos para calcular cada tipo de onda
    private double calcularQuadrada(double tempo) {
        double valor = 0.0;
        for (int n = 1; n <= 100; n++) {
            valor += (4 * amplitude / (Math.PI * n)) * (Math.sin(2 * Math.PI * n * frequencia * tempo + fase));
        }
        return valor;
    }

    private double calcularTriangular(double tempo) {
        double valor = 0.0;
        for (int n = 1; n <= 100; n++) {
            if (n % 2 != 0) {
                valor += (8 * amplitude / (Math.PI * Math.PI * n * n)) * (1 - Math.pow(-1, (n - 1) / 2)) * Math.sin(2 * Math.PI * n * frequencia * tempo + fase - Math.PI / 2);
            }
        }
        return valor;
    }

    private double calcularDenteDeSerra(double tempo) {
        double valor = 0.0;
        for (int n = 1; n <= 100; n++) {
            valor += (2 * amplitude / Math.PI) * (1 / n) * Math.sin(2 * Math.PI * n * frequencia * tempo + fase);
        }
        return valor;
    }

    private double calcularSenoidalRetificada(double tempo) {
        double valor = 0.0;
        for (int n = 1; n <= 100; n++) {
            valor += (amplitude / n) * Math.sin(2 * Math.PI * n * frequencia * tempo + fase + Math.atan(1 / n));
        }
        return valor;
    }

    // Método para filtrar o sinal para um canal Passa-Baixas
    private double filtrarPassaBaixas(double valor) {
        if (frequencia > frequenciaDeCorteSup) {
            return 0.0; // Se a frequência é maior que a frequência de corte superior, filtramos para zero
        }
        return valor;
    }

    // Método para filtrar o sinal para um canal Passa-Faixas
    private double filtrarPassaFaixas(double valor) {
        if (frequencia < frequenciaDeCorteInf || frequencia > frequenciaDeCorteSup) {
            return 0.0; // Se a frequência está fora da faixa, filtramos para zero
        }
        return valor;
    }

    // Método para calcular a amplitude das componentes do sinal de entrada usando a decomposição em série de Fourier
    public List<Double> calcularAmplitudeEntrada(int duracao, double intervalo) {
        List<Double> amplitudeEntrada = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            double amplitudeHarmonica = calcularAmplitudeHarmonica(i);
            amplitudeEntrada.add(amplitudeHarmonica);
        }

        return amplitudeEntrada;
    }

    private double calcularAmplitudeHarmonica(int n) {
        double amplitudeHarmonica = 0.0;

        switch (categoria) {
            case QUADRADA:
                amplitudeHarmonica = (4 * amplitude / (Math.PI * n)) * (Math.sin(Math.PI * n / 2));
                break;
            case TRIANGULAR:
                if (n % 2 != 0) {
                    amplitudeHarmonica = (8 * amplitude / (Math.PI * Math.PI * n * n)) * (1 - Math.pow(-1, (n - 1) / 2));
                }
                break;
            case DENTEDESERRA:
                amplitudeHarmonica = (2 * amplitude / Math.PI) * (1 / n);
                break;
            case SENOIDAL:
                amplitudeHarmonica = amplitude / n;
                break;
        }

        return amplitudeHarmonica;
    }

    // Método para calcular a fase das componentes do sinal de entrada usando a decomposição em série de Fourier
    public List<Double> calcularFaseEntrada(int duracao, double intervalo) {
        List<Double> faseEntrada = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            double faseHarmonica = calcularFaseHarmonica(i);
            faseEntrada.add(faseHarmonica);
        }

        return faseEntrada;
    }

    private double calcularFaseHarmonica(int n) {
        double faseHarmonica = 0.0;

        switch (categoria) {
            case QUADRADA:
                faseHarmonica = 0.0; // A fase das harmônicas da onda quadrada é sempre zero
                break;
            case TRIANGULAR:
                faseHarmonica = -Math.PI / 2; // A fase das harmônicas da onda triangular é sempre -π/2
                break;
            case DENTEDESERRA:
                faseHarmonica = 0.0; // A fase das harmônicas da onda dente-de-serra é sempre zero
                break;
            case SENOIDAL:
                faseHarmonica = Math.atan(1 / n); // A fase das harmônicas da onda senoidal retificada é atan(1/n)
                break;
        }

        return faseHarmonica;
    }

    // Método para calcular o módulo da resposta em frequência do canal de comunicações
    public List<Double> calcularModuloRespostaFrequencia() {
        List<Double> moduloRespostaFrequencia = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            moduloRespostaFrequencia.add(1.0); // Valor fictício para o módulo da resposta em frequência
        }

        return moduloRespostaFrequencia;
    }

    // Método para calcular a fase da resposta em frequência do canal de comunicações
    public List<Double> calcularFaseRespostaFrequencia() {
        List<Double> faseRespostaFrequencia = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            faseRespostaFrequencia.add(0.0); // Valor fictício para a fase da resposta em frequência
        }

        return faseRespostaFrequencia;
    }

    // Método para calcular a amplitude das componentes do sinal de saída usando a decomposição em série de Fourier
    public List<Double> calcularAmplitudeSaida(int duracao, double intervalo) {
        List<Double> amplitudeSaida = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            double amplitudeHarmonica = calcularAmplitudeHarmonicaSaida(i);
            amplitudeSaida.add(amplitudeHarmonica);
        }

        return amplitudeSaida;
    }

    private double calcularAmplitudeHarmonicaSaida(int n) {
        double amplitudeHarmonica = calcularAmplitudeHarmonica(n); // A amplitude das harmônicas de saída é igual às de entrada
        double moduloResposta = calcularModuloRespostaFrequencia().get(n - 1); // Supondo que cada harmônica tem o mesmo módulo de resposta

        return amplitudeHarmonica * moduloResposta;
    }

    // Método para calcular a fase das componentes do sinal de saída usando a decomposição em série de Fourier
    public List<Double> calcularFaseSaida(int duracao, double intervalo) {
        List<Double> faseSaida = new ArrayList<>();

        for (int i = 1; i <= 100; i++) {
            double faseHarmonica = calcularFaseHarmonicaSaida(i);
            faseSaida.add(faseHarmonica);
        }

        return faseSaida;
    }

    private double calcularFaseHarmonicaSaida(int n) {
        double faseHarmonica = calcularFaseHarmonica(n); // A fase das harmônicas de saída é igual às de entrada
        double faseResposta = calcularFaseRespostaFrequencia().get(n - 1); // Supondo que cada harmônica tem a mesma fase de resposta

        return faseHarmonica + faseResposta;
    }

    // Método para calcular o sinal recebido ao longo do tempo
    public List<Double> calcularSinalRecebido(int duracao, double intervalo) {
        List<Double> sinalRecebido = new ArrayList<>();

        List<Double> sinalEntrada = calcularSinalEntrada(duracao, intervalo);
        List<Double> amplitudeSaida = calcularAmplitudeSaida(duracao, intervalo);
        List<Double> faseSaida = calcularFaseSaida(duracao, intervalo);

        for (int i = 0; i < duracao; i++) {
            double tempo = i * intervalo;
            double sinal = 0.0;

            for (int n = 1; n <= 100; n++) {
                double valorHarmonico = amplitudeSaida.get(n - 1) * Math.sin(2 * Math.PI * n * frequencia * tempo + faseSaida.get(n - 1));
                sinal += valorHarmonico;
            }

            sinalRecebido.add(sinal);
        }

        return sinalRecebido;
    }
    //endregion
}
