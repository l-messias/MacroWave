package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalculoOndaSenoidal extends CalculoOnda{
    private static CalculoOndaSenoidal instance;

    public static CalculoOndaSenoidal getInstance() {
        if (instance == null) {
            instance = new CalculoOndaSenoidal();
        }
        return instance;
    }
    @Override
    public  double calculaSinal(double amplitude,int n, double frequencia, double t, double fase){
        return (amplitude * Math.cos(2 * Math.PI * frequencia * t + fase));
    }
    @Override
    public double calculaAmplitude(int harmonicaAtual) {
        return amplitudeMaxima;
    }

    @Override
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
            double amplitude = calculaAmplitude(1);
            sinalEntrada += calculaSinal(amplitude,1,frequencia,t,fase);
            mapaAmplitudes.put(t, sinalEntrada);
            t += incrementoTempo;
        }

        return mapaAmplitudes;
    }
}
