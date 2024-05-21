package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CalculoOnda {
    public static final int harmonicas = 200;
    public static final double amplitudeMaxima = 1;

    public Map<Double, Double> amplitudePorFrequenciaEntrada;
    public Map<Double, Double> fasePorFrequenciaEntrada;
    public Map<Double, Double> faseDaRespostaEmFrequencia;
    public Map<Double, Double> moduloDaRespostaEmFrequencia;
    public Map<Double, Double> amplitudePorFrequenciaSaida;
    public Map<Double, Double> fasePorFrequenciaSaida;

    public double sinalInicial() {
        return 0;
    }

    public Map<Double, Double> calcularSinalEntrada(double frequencia) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        double fase =  - Math.PI / 2;
        double incrementoTempo = periodo / (harmonicas * 10);
        amplitudePorFrequenciaEntrada = new LinkedHashMap<Double, Double>();
        fasePorFrequenciaEntrada = new LinkedHashMap<Double, Double>();
        while(t < duracao)
        {
            double sinalEntrada = sinalInicial();

            for(int n = 1; n <= harmonicas; n++)
            {
                double amplitude = calculaAmplitude(n, frequencia);
                sinalEntrada += calculaSinal(amplitude,n,frequencia,t,fase);
                if(amplitudePorFrequenciaEntrada.size() <= harmonicas) {
                    amplitudePorFrequenciaEntrada.put(frequencia * n, amplitude);
                    fasePorFrequenciaEntrada.put(frequencia * n, Math.toDegrees(fase));
                }
            }
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo;
        }

        return mapaAmplitudes;
    }

    public  double calculaSinal(double amplitude,int n, double frequencia, double t, double fase){
        return (amplitude * Math.cos(2 * Math.PI * n * frequencia * t + fase));
    }

    public void ModuloFiltroPassaBaixas(double frequenciaFundamental, double frequenciaDeCorte) {
        moduloDaRespostaEmFrequencia = new LinkedHashMap<Double, Double>();
        faseDaRespostaEmFrequencia = new LinkedHashMap<Double, Double>();
        for(int n = 1; n <= harmonicas; n++){
            double frequenciaN = frequenciaFundamental * n;
            double coeficiente = 1 + Math.pow(((frequenciaN) / frequenciaDeCorte), 2);
            double resposta = 1 / Math.sqrt(coeficiente);
            double faseResposta = Math.atan2(-frequenciaN, frequenciaDeCorte);
            double faseEmGraus = Math.toDegrees(faseResposta);
            moduloDaRespostaEmFrequencia.put(frequenciaN, resposta);
            faseDaRespostaEmFrequencia.put(frequenciaN, faseEmGraus);
        }
    }

    public void ModuloFiltroPassaFaixas(double frequenciaFundamental, double frequenciaDeCorteSup, double frequenciaDeCorteInf) {
        moduloDaRespostaEmFrequencia = new LinkedHashMap<Double, Double>();
        faseDaRespostaEmFrequencia = new LinkedHashMap<Double, Double>();
        for(int n = 1; n <= harmonicas; n++){
            double frequenciaN = frequenciaFundamental * n;
            double coeficienteInf = 1 + Math.pow(((frequenciaN) / frequenciaDeCorteInf), 2);
            double coeficienteSup = 1 + Math.pow(((frequenciaN) / frequenciaDeCorteSup), 2);
            double resposta = (1/frequenciaDeCorteInf) * (frequenciaN / Math.sqrt(coeficienteInf * coeficienteSup));
            double coeficienteFase = (frequenciaN * (frequenciaDeCorteInf + frequenciaDeCorteSup)) / ((frequenciaDeCorteInf * frequenciaDeCorteSup) - (frequenciaN * frequenciaN));
            double faseResposta = -Math.PI/2  - Math.atan(coeficienteFase);
            moduloDaRespostaEmFrequencia.put(frequenciaN, resposta);
            faseDaRespostaEmFrequencia.put(frequenciaN, faseResposta);
        }
    }

    public Map<Double, Double> calcularSinalSaida(double frequencia, Canal canal, double frequenciaDeCorteInf, double frequenciaDeCorteSup) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        double fase =  - Math.PI / 2;
        double incrementoTempo = periodo / (harmonicas * 10);
        amplitudePorFrequenciaSaida = new LinkedHashMap<Double, Double>();
        fasePorFrequenciaSaida = new LinkedHashMap<Double, Double>();
        if(canal == Canal.PASSABAIXAS)
        {
            ModuloFiltroPassaBaixas(frequencia, frequenciaDeCorteSup);
        }
        if(canal == Canal.PASSAFAIXAS)
        {
            ModuloFiltroPassaFaixas(frequencia, frequenciaDeCorteSup, frequenciaDeCorteInf);
        }
        while(t < duracao)
        {
            double sinalEntrada = sinalInicial();

            for(int n = 1; n <= harmonicas; n++)
            {
                double amplitude = calculaAmplitude(n, frequencia);
                double frequenciaN = frequencia * n;
                amplitude = amplitude * moduloDaRespostaEmFrequencia.get(frequenciaN);
                fase = faseDaRespostaEmFrequencia.get(frequenciaN);
                if(amplitudePorFrequenciaSaida.size() <= harmonicas) {
                    amplitudePorFrequenciaSaida.put(frequenciaN, amplitude);
                    fasePorFrequenciaSaida.put(frequenciaN, fase);
                }
                fase = Math.toRadians(fase);
                sinalEntrada += calculaSinal(amplitude,n,frequencia,t,fase);

            }
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo;
        }

        return mapaAmplitudes;
    }

    public abstract double calculaAmplitude(int harmonicaAtual, double frequencia);

    public Map<Double, Double> getAmplitudePorFrequenciaEntrada() {
        return amplitudePorFrequenciaEntrada;
    }

    public Map<Double, Double> getFasePorFrequenciaEntrada() {
        return fasePorFrequenciaEntrada;
    }

    public Map<Double, Double> getFaseDaRespostaEmFrequencia() {
        return faseDaRespostaEmFrequencia;
    }

    public Map<Double, Double> getModuloDaRespostaEmFrequencia() {
        return moduloDaRespostaEmFrequencia;
    }

    public Map<Double, Double> getAmplitudePorFrequenciaSaida() {
        return amplitudePorFrequenciaSaida;
    }

    public Map<Double, Double> getFasePorFrequenciaSaida() {
        return fasePorFrequenciaSaida;
    }
}
