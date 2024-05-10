package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.HashMap;
import java.util.Map;

public class CalculoOnda {

    private static final double duracao = 1;
    private static final double amplitude = 1;

    public static Map<Double, Double> calcularOndaSenoidal(double amplitude, double frequencia, double fase, double duracao, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        Map<Double, Double> mapaAmplitudes = new HashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude_final = amplitude * Math.sin(2 * Math.PI * frequencia * t + fase);

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else {
                mapaAmplitudes.put(t, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo * 1000000; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOndaQuadrada(double amplitude, double frequencia, double duracao, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        Map<Double, Double> mapaAmplitudes = new HashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude_final = (t % periodo < periodo / 2) ? amplitude : -amplitude;

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else {
                mapaAmplitudes.put(t, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo / 100; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOndaTriangular(double amplitude, double frequencia, double duracao, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        Map<Double, Double> mapaAmplitudes = new HashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude_final;
            if (t % periodo < periodo / 2) {
                amplitude_final = (4 * amplitude / periodo) * t;
            } else {
                amplitude_final = (-4 * amplitude / periodo) * t + 4 * amplitude;
            }

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else {
                mapaAmplitudes.put(t, 0.0); // Frequência não permitida pelo canal
            }

            t += periodo / 100; // Dividindo o período em pequenos incrementos para obter uma representação suave da onda
        }
        return mapaAmplitudes;
    }

    public static Map<Double, Double> calcularOndaDenteDeSerra(double amplitude, double frequencia, double duracao, Canal canal, double frequenciaCorteSup, double frequenciaCorteInf) {
        double periodo = 1 / frequencia;
        Map<Double, Double> mapaAmplitudes = new HashMap<>();
        double t = 0;
        while (t < duracao) {
            double amplitude_final = (amplitude * t) % periodo;

            // Filtragem de acordo com o tipo de canal
            if (canal == Canal.PASSABAIXAS && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else if (canal == Canal.PASSAFAIXAS && frequencia >= frequenciaCorteInf && frequencia <= frequenciaCorteSup) {
                mapaAmplitudes.put(t, amplitude_final);
            } else {
                mapaAmplitudes.put(t, 0.0); // Frequência não permitida pelo canal
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
            case SENOIDAL -> calcularOndaSenoidal(amplitude, frequencia, 0, duracao, canal, frequenciaCorteSup, frequenciaCorteInf);
            case QUADRADA -> calcularOndaQuadrada(amplitude, frequencia, duracao, canal, frequenciaCorteSup, frequenciaCorteInf);
            case TRIANGULAR -> calcularOndaTriangular(amplitude, frequencia, duracao, canal, frequenciaCorteSup, frequenciaCorteInf);
            case DENTEDESERRA -> calcularOndaDenteDeSerra(amplitude, frequencia, duracao, canal, frequenciaCorteSup, frequenciaCorteInf);
            default -> new HashMap<>();
        };
    }
}
