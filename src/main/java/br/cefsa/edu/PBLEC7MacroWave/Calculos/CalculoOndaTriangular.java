package br.cefsa.edu.PBLEC7MacroWave.Calculos;

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

        //Harmônicas pares terão amplitude de 0
        if(harmonicaAtual % 2 == 0 ) {
            return 0;
        }

        double expoente = ((double)harmonicaAtual - 1)/2;
        double coeficiente = Math.pow(-1, expoente);
        double nQuadrado = Math.pow(harmonicaAtual, 2);
        double piQuadrado = Math.pow(Math.PI, 2);
        return (8 * getAMPLITUDEMAXIMA() * coeficiente) / (piQuadrado * nQuadrado);
    }
}
