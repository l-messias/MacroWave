package br.cefsa.edu.PBLEC7MacroWave.Calculos;
import br.cefsa.edu.PBLEC7MacroWave.Model.Canal;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class CalculoOnda {

    //Definido pelo programador
    public final int HARMONICAS = 100;
    public final double AMPLITUDEMAXIMA = 1;
    public double coeficienteDuracao = 100;

    //Com base nos dados do usuário
    private double periodo;
    private double duracao;
    private double fase;
    private double incrementoTempo;

    //Criação dos mapas para preenchimento dos gráficos de onda
    private Map<Double, Double> amplitudePorFrequenciaEntrada;
    private Map<Double, Double> fasePorFrequenciaEntrada;
    private Map<Double, Double> faseDaRespostaEmFrequencia;
    private Map<Double, Double> moduloDaRespostaEmFrequencia;
    private Map<Double, Double> amplitudePorFrequenciaSaida;
    private Map<Double, Double> fasePorFrequenciaSaida;


    public void setFase(double fase) {
        this.fase = fase;
    }

    //Fase padrão para ondas quadradas, dentes-de-serra e triangulares
    public void defineFase() {
        setFase(-Math.PI/2);
    }

    public void definePeriodo(double frequencia) {
        this.periodo = 1 / frequencia;
    }

    public void defineDuracao() {
        this.duracao = periodo * coeficienteDuracao;
    }

    //Ao final de cada loop, será incrementando ao instante 't' até que alcance a duração. Define, portanto, a quantidade de amostras.
    public void defineIncrementoTempo() {
        this.incrementoTempo = periodo / (10 * coeficienteDuracao);
    }

    //Definindo os valores com base na frequência do usuário
    public void preencheInformacoes(double frequencia) {
        defineFase();
        definePeriodo(frequencia);
        defineDuracao();
        defineIncrementoTempo();
    }

    //Instanciando os mapas para posteriormente preenchê-los
    public void instanciaMapas() {
        amplitudePorFrequenciaEntrada = new LinkedHashMap<Double, Double>();
        fasePorFrequenciaEntrada = new LinkedHashMap<Double, Double>();
        amplitudePorFrequenciaSaida = new LinkedHashMap<Double, Double>();
        fasePorFrequenciaSaida = new LinkedHashMap<Double, Double>();
        faseDaRespostaEmFrequencia = new LinkedHashMap<Double, Double>();
        moduloDaRespostaEmFrequencia = new LinkedHashMap<Double, Double>();
    }

    //Calculo das Amplitudes do Sinal de Entrada
    public Map<Double, Double> calcularSinalEntrada(double frequencia) {

        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();

        //Instancia Novos Mapas toda vez que for chamado
        instanciaMapas();

        //Preenche Informações necessárias a partir da frequência fundamental definida pelo usuário
        preencheInformacoes(frequencia);

        //Série de Fourier para um certo número de períodos definido anteriormente (Duração = Período * Coeficiente de Duração)
        for(double t = 0; t <= duracao; t+= incrementoTempo)
        {
            //Define o componente a0
            double sinalEntrada = sinalInicial();

            //Soma dos componentes an
            for(int n = 1; n <= HARMONICAS; n++)
            {
                //Define a amplitude para o tipo de onda escolhido
                double amplitude = calculaAmplitude(n, frequencia);

                //Calcula o componente an a partir da amplitude e o soma para cada harmônica
                sinalEntrada += calculaSinal(amplitude,n,frequencia,t,fase);

                //Preenche mapas referentes ao sinal de entrada com base na frequencia (fn) atual;
                preencheAmplitudeFasePorFrequencia(true, amplitude, frequencia, n, fase);
            }

            //Insere no mapa a Amplitude do Sinal com base no instante 't'
            mapaAmplitudes.put(t, sinalEntrada);
        }

        return mapaAmplitudes;
    }

    //Calculo das Amplitudes do Sinal de Saída afetado pelo canal
    public Map<Double, Double> calcularSinalSaida(double frequencia, Canal canal, double frequenciaDeCorteInf, double frequenciaDeCorteSup) {

        Map<Double, Double> mapaAmplitudes = new LinkedHashMap<>();

        //Calcula a resposta com base no canal escolhido pelo usuário;


        //Valores da resposta caso a frequência seja 0
        preencheMapaResposta(0, 1.0, 0);

        //Série de Fourier para um certo número de períodos definido anteriormente (Duração = Período * Coeficiente de Duração)
        for(double t = 0; t < duracao; t+= incrementoTempo)
        {
            //Define o componente a0
            double sinalSaida = sinalInicial();

            //Variáveis da resposta são instanciadas com valores placeholder
            double moduloResposta = 1;
            double faseResposta = 0;

            //Soma dos componentes an
            for(int n = 1; n <= HARMONICAS; n++)
            {
                double amplitude = calculaAmplitude(n, frequencia);

                //fn = frequencia fundamental multiplicada pelo número da harmonica atual
                double frequenciaN = frequencia * n;

                //Os valores da resposta são então redefinidos com base no canal escolhido
                switch(canal) {
                    case PASSABAIXAS:
                        moduloResposta = moduloFiltroPassaBaixas(frequenciaN, frequenciaDeCorteSup);
                        faseResposta = faseFiltroPassaBaixas(frequenciaN, frequenciaDeCorteSup);
                        break;
                    case PASSAFAIXAS:
                        moduloResposta = moduloFiltroPassaFaixas(frequenciaN, frequenciaDeCorteSup, frequenciaDeCorteInf);
                        faseResposta = faseFiltroPassaFaixas(frequenciaN, frequenciaDeCorteSup, frequenciaDeCorteInf);
                        break;
                    default:
                        break;
                }

                //A amplitude é multiplicada pelo módulo da resposta do canal com base na frequência
                amplitude = amplitude * moduloResposta;

                //A fase é somada pela resposta do canal com base na frequência
                double faseN = fase + faseResposta;

                //Preenche mapas referentes a resposta do canal com base na frequencia (fn) atual e as frequências de corte.
                preencheMapaResposta(frequenciaN, moduloResposta, Math.toDegrees(faseResposta));

                //Preenche mapas referentes ao sinal de saída com base na frequencia (fn) atual;
                preencheAmplitudeFasePorFrequencia(false, amplitude, frequencia, n, Math.toDegrees(faseN));

                sinalSaida += calculaSinal(amplitude,n,frequencia,t,faseN);

            }

            //Insere no mapa a Amplitude do Sinal com base no instante 't'
            mapaAmplitudes.put(t, sinalSaida);
        }

        return mapaAmplitudes;
    }

    //Componente a0 da Série de Fourier
    public double sinalInicial() {
        return 0;
    }

    //Componente an da Série de Fourier
    public  double calculaSinal(double amplitude,int n, double frequencia, double t, double fase){
        return (amplitude * Math.cos(2 * Math.PI * n * frequencia * t + fase));
    }

    //Preenche os gráficos de Amplitude e Fase de Entrada ou Saída com base na Frequência
    public void preencheAmplitudeFasePorFrequencia(boolean entrada, double amplitude, double frequencia, int n, double faseN) {
        if (entrada) {
            if (amplitudePorFrequenciaEntrada.size() <= HARMONICAS) {
                amplitudePorFrequenciaEntrada.put(frequencia * n, amplitude);
                //Se a amplitude for 0, a fase também é 0.
                fasePorFrequenciaEntrada.put(frequencia * n, amplitude == 0 ? 0.0 : faseN);
            }
        } else {
            if (amplitudePorFrequenciaSaida.size() <= HARMONICAS) {
                amplitudePorFrequenciaSaida.put(frequencia * n, amplitude);
                fasePorFrequenciaSaida.put(frequencia * n, amplitude == 0 ? 0.0 : faseN);
            }
        }
    }

    //Resposta do canal passa-baixas com base na fórmula ilustrada no documento do PBL
    public double moduloFiltroPassaBaixas(double frequenciaN, double frequenciaDeCorte) {
            double coeficiente = 1 + Math.pow(((frequenciaN) / frequenciaDeCorte), 2);
            return 1 / Math.sqrt(coeficiente);
    }

    //Fase da resposta do canal passa-baixas com base na fórmula ilustrada no documento do PBL
    public double faseFiltroPassaBaixas(double frequenciaN, double frequenciaDeCorte) {
        return Math.atan2(-frequenciaN, frequenciaDeCorte);
    }

    //Resposta do canal passa-faixas com base na fórmula ilustrada no documento do PBL
    public double moduloFiltroPassaFaixas(double frequenciaN, double frequenciaDeCorteSup, double frequenciaDeCorteInf) {
        double coeficienteInf = 1 + Math.pow(((frequenciaN) / frequenciaDeCorteInf), 2);
        double coeficienteSup = 1 + Math.pow(((frequenciaN) / frequenciaDeCorteSup), 2);
        return (1/frequenciaDeCorteInf) * (frequenciaN / Math.sqrt(coeficienteInf * coeficienteSup));
    }

    //Fase da resposta do canal passa-faixas com base na fórmula ilustrada no documento do PBL
    public double faseFiltroPassaFaixas(double frequenciaN, double frequenciaDeCorteSup, double frequenciaDeCorteInf) {
        double coeficienteFaseNumerador = (frequenciaN * (frequenciaDeCorteInf + frequenciaDeCorteSup));
        double coeficienteFaseDenominador = ((frequenciaDeCorteInf * frequenciaDeCorteSup) - (frequenciaN * frequenciaN));
        return -Math.PI/2  - Math.atan2(coeficienteFaseNumerador, coeficienteFaseDenominador);
    }

    //Preenche os mapas da resposta com base nos valores calculados
    public void preencheMapaResposta(double frequenciaN, double resposta, double faseEmGraus) {
        if(moduloDaRespostaEmFrequencia.size() <= HARMONICAS) {
            moduloDaRespostaEmFrequencia.put(frequenciaN, resposta);
            faseDaRespostaEmFrequencia.put(frequenciaN, faseEmGraus);
        }
    }

    //Cada tipo de onda tem o seu modo de calcular a amplitude
    public abstract double calculaAmplitude(int harmonicaAtual, double frequencia);

    public Map<Double, Double> getAmplitudePorFrequenciaEntrada() {
        return amplitudePorFrequenciaEntrada;
    }

    public Map<Double, Double> getFasePorFrequenciaEntrada() {
        return fasePorFrequenciaEntrada;
    }

    public Map<Double, Double> getFaseDaRespostaEmFrequencia() {
        return faseDaRespostaEmFrequencia;
    }

    public Map<Double, Double> getModuloDaRespostaEmFrequencia() {
        return moduloDaRespostaEmFrequencia;
    }

    public Map<Double, Double> getAmplitudePorFrequenciaSaida() {
        return amplitudePorFrequenciaSaida;
    }

    public Map<Double, Double> getFasePorFrequenciaSaida() {
        return fasePorFrequenciaSaida;
    }

    public double getAMPLITUDEMAXIMA() {
        return AMPLITUDEMAXIMA;
    }
}
