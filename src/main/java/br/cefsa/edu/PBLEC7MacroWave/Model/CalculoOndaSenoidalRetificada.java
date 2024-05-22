package br.cefsa.edu.PBLEC7MacroWave.Model;

public class CalculoOndaSenoidalRetificada extends CalculoOnda{
    private static CalculoOndaSenoidalRetificada instance;

    @Override
    public double sinalInicial() {
        return (2 * amplitudeMaxima) / Math.PI;
    }

    public static CalculoOndaSenoidalRetificada getInstance() {
        if (instance == null) {
            instance = new CalculoOndaSenoidalRetificada();
        }
        return instance;
    }


    @Override
    public double calculaAmplitude(int harmonicaAtual, double frequencia) {
        if (harmonicaAtual % 2 != 0) {
            return (-4 * amplitudeMaxima) / (Math.PI * ((4 * (harmonicaAtual * harmonicaAtual)) - 1));
        }
        return 0;
    }

        /*
    @Override
    public  double calculaSinal(double amplitude,int n, double frequencia, double t, double fase){
        return (amplitude * Math.cos(2 * Math.PI * n * frequencia * t + fase));
    }
     */
}
