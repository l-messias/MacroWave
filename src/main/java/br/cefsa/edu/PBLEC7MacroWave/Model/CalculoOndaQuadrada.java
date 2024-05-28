package br.cefsa.edu.PBLEC7MacroWave.Model;

import java.util.LinkedHashMap;
import java.util.Map;

public class CalculoOndaQuadrada extends CalculoOnda {
    private static CalculoOndaQuadrada instance;
    public static CalculoOndaQuadrada getInstance() {
        if (instance == null) {
            instance = new CalculoOndaQuadrada();
        }
        return instance;
    }
    @Override
    public double calculaAmplitude(int harmonicaAtual, double frequencia) {
        //Somente as harmônicas ímpares são consideradas.
        return harmonicaAtual % 2 == 0 ? 0 : (4 * getAmplitudeMaxima()) / (Math.PI * harmonicaAtual);
    }
}
