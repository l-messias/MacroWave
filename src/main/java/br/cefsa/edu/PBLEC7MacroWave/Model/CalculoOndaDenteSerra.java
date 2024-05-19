package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalculoOndaDenteSerra extends CalculoOnda{
    private static CalculoOndaDenteSerra instance;

    public static CalculoOndaDenteSerra getInstance() {
        if (instance == null) {
            instance = new CalculoOndaDenteSerra();
        }
        return instance;
    }

    @Override
    public double calculaAmplitude(int harmonicaAtual, double frequencia) {
        double amplitude = (2 * amplitudeMaxima) / (Math.PI * harmonicaAtual);
        if (harmonicaAtual % 2 == 0) {
            amplitude = -amplitude;
        }
        return amplitude;
    }
}
