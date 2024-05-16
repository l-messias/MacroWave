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
    public double calculaAmplitude(int harmonicaAtual) {
        if(harmonicaAtual % 2 == 0) {
            return amplitudeMaxima;
        }
        return 0;
    }
}
