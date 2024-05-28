package br.cefsa.edu.PBLEC7MacroWave.Model;

public class CalculoOndaSenoidalRetificada extends CalculoOnda{
    private static CalculoOndaSenoidalRetificada instance;

    public static CalculoOndaSenoidalRetificada getInstance() {
        if (instance == null) {
            instance = new CalculoOndaSenoidalRetificada();
        }
        return instance;
    }

    //Esse tipo de onda possui um componente a0 diferente de 0
    @Override
    public double sinalInicial() {
        return (2 * getAmplitudeMaxima()) / Math.PI;
    }

    @Override
    public double calculaAmplitude(int harmonicaAtual, double frequencia) {
        //Somente as harmônicas ímpares são consideradas
        if (harmonicaAtual % 2 == 0) {
            return 0;
        }
        int nQuadrado = harmonicaAtual * harmonicaAtual;
        return (4 * getAmplitudeMaxima()) / ((1 - 4 * nQuadrado) * Math.PI);
    }

}
