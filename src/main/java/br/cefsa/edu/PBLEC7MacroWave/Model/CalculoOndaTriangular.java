package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalculoOndaTriangular extends CalculoOnda {
    private static CalculoOndaTriangular instance;

    public static CalculoOndaTriangular getInstance() {
        if (instance == null) {
            instance = new CalculoOndaTriangular();
        }
        return instance;
    }
    @Override
    public double calculaAmplitude(int harmonicaAtual, double frequencia) {
        double amplitude = 8 * amplitudeMaxima / (Math.PI * Math.PI * harmonicaAtual * harmonicaAtual);
        if(harmonicaAtual % 2 != 0 ) {
            if ((harmonicaAtual - 1) / 2 % 2 != 0) {
                amplitude = -amplitude;
            }
            return amplitude;
        }
        return 0;
    }
}
