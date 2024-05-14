package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalculoOnda {

    private static final int harmonicas = 100;
    private static final double amplitudeMaxima = 1;

    public static Map<Double, Double> calcularOndaSenoidal(double amplitudeMaxima, double frequencia, double fase, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude = amplitudeMaxima * Math.abs(Math.sin(2 * Math.PI * frequencia * t + fase));

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else {
                mapaAmplitudes.put(t * 1000, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo / 100; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOndaQuadrada(double amplitudeMaxima, double frequencia, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude = (t % periodo < periodo / 2) ? amplitudeMaxima : -amplitudeMaxima;

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else {
                mapaAmplitudes.put(t * 1000, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo / 100; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOndaTriangular(double amplitudeMaxima, double frequencia, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude = amplitudeMaxima - 2 * amplitudeMaxima * (Math.abs(t % periodo - periodo / 2) / (periodo / 2));
            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else {
                mapaAmplitudes.put(t * 1000, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo / 100; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOndaDenteDeSerra(double amplitudeMaxima, double frequencia, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        double duracao = periodo * harmonicas;
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude = (amplitudeMaxima * t) % periodo;

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t * 1000, amplitude);
            } else {
                mapaAmplitudes.put(t * 1000, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo / 100; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOnda(Categoria categoria, Canal canal, double frequencia, double frequenciaCorteSup, double frequenciaCorteInf) {
        frequencia = frequencia * 1000; // Convertendo kHz para Hz
        frequenciaCorteInf = frequenciaCorteInf * 1000; // Convertendo kHz para Hz
        frequenciaCorteSup = frequenciaCorteSup * 1000; // Convertendo kHz para Hz
        return switch (categoria) {
            case SENOIDAL -> calcularOndaSenoidal(amplitudeMaxima, frequencia, 0, canal, frequenciaCorteSup, frequenciaCorteInf);
            case QUADRADA -> calcularOndaQuadrada(amplitudeMaxima, frequencia, canal, frequenciaCorteSup, frequenciaCorteInf);
            case TRIANGULAR -> calcularOndaTriangular(amplitudeMaxima, frequencia, canal, frequenciaCorteSup, frequenciaCorteInf);
            case DENTEDESERRA -> calcularOndaDenteDeSerra(amplitudeMaxima, frequencia, canal, frequenciaCorteSup, frequenciaCorteInf);
            default -> new LinkedHashMap<>();
        };
    }
}
