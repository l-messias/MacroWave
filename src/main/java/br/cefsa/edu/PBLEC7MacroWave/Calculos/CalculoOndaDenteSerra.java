package br.cefsa.edu.PBLEC7MacroWave.Calculos;

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
        double coeficiente = Math.pow(-1,(harmonicaAtual + 1));
        return (2 * getAMPLITUDEMAXIMA() * coeficiente) / (Math.PI * harmonicaAtual);
    }
}