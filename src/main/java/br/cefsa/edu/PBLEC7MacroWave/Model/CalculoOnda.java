package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CalculoOnda {
    public static final int harmonicas = 200;
    public static final double amplitudeMaxima = 1;

    public Map<Double, Double> calcularOnda( double frequencia) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        double fase =  - Math.PI / 2;
        double incrementoTempo = periodo / (harmonicas * 10);
        while(t < duracao)
        {
            double sinalEntrada = 0;

            for(int n = 1; n <= harmonicas; n++)
            {
                double amplitude = calculaAmplitude(n);
                sinalEntrada += calculaSinal(amplitude,n,frequencia,t,fase);
            }
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo;
        }

        return mapaAmplitudes;
    }

    public  double calculaSinal(double amplitude,int n, double frequencia, double t, double fase){
        return (amplitude * Math.cos(2 * Math.PI * n * frequencia * t + fase));
    }
    public abstract double calculaAmplitude(int harmonicaAtual);
    /*
    public static Map<Double, Double> calcularOnda(Categoria categoria, Canal canal, double frequencia, double frequenciaCorteSup, double frequenciaCorteInf) {
        frequencia = frequencia * 1000; // Convertendo kHz para Hz
        frequenciaCorteInf = frequenciaCorteInf * 1000; // Convertendo kHz para Hz
        frequenciaCorteSup = frequenciaCorteSup * 1000; // Convertendo kHz para Hz
        return switch (categoria) {
            case SENOIDAL -> calcularOndaSenoidal(amplitudeMaxima, frequencia, 0, canal, frequenciaCorteSup, frequenciaCorteInf);
            case QUADRADA -> calcularOndaQuadrada(amplitudeMaxima, frequencia, harmonicas);
            case TRIANGULAR -> calcularOndaTriangular(amplitudeMaxima, frequencia, harmonicas);
            case DENTEDESERRA -> calcularOndaDenteDeSerra(amplitudeMaxima, frequencia, harmonicas);
            default -> new LinkedHashMap<>();
        };
    }
    */

}
