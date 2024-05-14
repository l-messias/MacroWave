package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class CalculoOnda {

    private static final int harmonicas = 200;
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

    /*
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

     */

    public static Map<Double, Double> calcularOndaQuadrada(double amplitudeMaxima, double frequencia, int harmonicas) {
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

                if(n % 2 != 0)
                {
                    double amplitude = 4 * amplitudeMaxima / (Math.PI * n);
                    sinalEntrada += (amplitude * Math.cos(2 * Math.PI * n * frequencia * t + fase));
                }

            }
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo;
        }

        return mapaAmplitudes;
    }


    public static Map<Double, Double> calcularOndaTriangular(double amplitudeMaxima, double frequencia, int harmonicas) {
        // Cálculo do período da onda
        double periodo = 1 / frequencia;
        // Definindo uma duração que mostra um número completo de períodos
        double duracao = periodo * harmonicas;

        // Mapa para armazenar os valores de tempo e amplitude
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        double fase = -Math.PI / 2; // Fase inicial

        // Incremento de tempo para resolução adequada
        double incrementoTempo = periodo / (harmonicas * 10);

        // Loop através do tempo até atingir a duração
        while (t < duracao) {
            double sinalEntrada = 0;

            // Soma das harmônicas ímpares da série de Fourier
            for (int n = 1; n <= harmonicas; n++) {
                if (n % 2 != 0) { // Apenas harmônicas ímpares
                    double amplitude = 8 * amplitudeMaxima / (Math.PI * Math.PI * n * n);
                    sinalEntrada += (Math.pow(-1, (double) (n - 1) / 2) * amplitude * Math.cos(2 * Math.PI * n * frequencia * t + fase));
                }
            }

            // Armazenando o tempo e a amplitude calculada no mapa
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo; // Incremento de tempo
        }

        return mapaAmplitudes;
    }


    public static Map<Double, Double> calcularOndaDenteDeSerra(double amplitudeMaxima, double frequencia, int harmonicas) {
        // Cálculo do período da onda
        double periodo = 1 / frequencia;
        // Definindo uma duração que mostra um número completo de períodos
        double duracao = periodo * harmonicas;

        // Mapa para armazenar os valores de tempo e amplitude
        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();
        double t = 0;
        double fase = -Math.PI / 2; // Fase inicial

        // Incremento de tempo para resolução adequada
        double incrementoTempo = periodo / (harmonicas * 10);

        // Loop através do tempo até atingir a duração
        while (t < duracao) {
            double sinalEntrada = 0;

            // Soma das harmônicas ímpares da série de Fourier
            for (int n = 1; n <= harmonicas; n++) {
                double amplitude = (2 * amplitudeMaxima) / (Math.PI * n);
                //sinalEntrada += ((Math.pow(-1, (n + 1)) / n) * amplitude * Math.sin(2 * Math.PI * n * frequencia * t + fase));
                sinalEntrada += ((Math.pow(-1, (n + 1)) / n) * amplitude * Math.sin(2 * Math.PI * n * frequencia * t + fase));
            }

            // Armazenando o tempo e a amplitude calculada no mapa
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo; // Incremento de tempo
        }

        return mapaAmplitudes;
    }





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
}
