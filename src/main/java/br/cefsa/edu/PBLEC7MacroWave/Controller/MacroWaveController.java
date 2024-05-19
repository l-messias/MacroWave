package br.cefsa.edu.PBLEC7MacroWave.Controller;
import br.cefsa.edu.PBLEC7MacroWave.Model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@Controller
public class MacroWaveController {


    @ModelAttribute("canais")
    public Canal[] canais() {
        return Canal.values();
    }

    @ModelAttribute("categorias")
    public Categoria[] categorias() {
        return Categoria.values();
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/wave")
    public String wave() {
        return "wave";
    }

    @GetMapping("/form-onda")
    public String formOnda() {
        return "form-onda";
    }

    @PostMapping("/criar-simulacao")
    public ResponseEntity<Map<String, Object>> postOnda(@RequestParam("categoria") Categoria categoria,
                                                        @RequestParam("frequencia") int frequencia,
                                                        @RequestParam("canal") Canal canal,
                                                        @RequestParam("frequenciaDeCorteSup") int frequenciaDeCorteSup,
                                                        @RequestParam(value = "frequenciaDeCorteInf", required = false) Integer frequenciaDeCorteInf) {
            frequencia = frequencia * 1000;
            frequenciaDeCorteInf = frequenciaDeCorteInf * 1000;
            frequenciaDeCorteSup = frequenciaDeCorteSup * 1000;

        Map<Double, Double> sinaisEntrada;
        Map<Double, Double> sinaisSaida;
        Map<Double, Double> respostaModuloCanal;
        Map<Double, Double> respostaFaseCanal;
        Map<Double, Double> amplitudePorFrequenciaEntrada;
        Map<Double, Double> fasePorFrequenciaEntrada;
        Map<Double, Double> amplitudePorFrequenciaSaida;
        Map<Double, Double> fasePorFrequenciaSaida;
        switch (categoria) {
            case SENOIDAL -> {
                sinaisEntrada = CalculoOndaSenoidalRetificada.getInstance().calcularSinalEntrada(frequencia);
                sinaisSaida = CalculoOndaSenoidalRetificada.getInstance().calcularSinalSaida(frequencia, canal, frequenciaDeCorteInf, frequenciaDeCorteSup);
                respostaModuloCanal = CalculoOndaSenoidalRetificada.getInstance().getModuloDaRespostaEmFrequencia();
                respostaFaseCanal = CalculoOndaSenoidalRetificada.getInstance().getFaseDaRespostaEmFrequencia();
                amplitudePorFrequenciaEntrada = CalculoOndaSenoidalRetificada.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaEntrada = CalculoOndaSenoidalRetificada.getInstance().getFasePorFrequenciaEntrada();
                amplitudePorFrequenciaSaida = CalculoOndaSenoidalRetificada.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaSaida = CalculoOndaSenoidalRetificada.getInstance().getFasePorFrequenciaEntrada();
            }
            case QUADRADA -> {
                sinaisEntrada = CalculoOndaQuadrada.getInstance().calcularSinalEntrada(frequencia);
                sinaisSaida = CalculoOndaQuadrada.getInstance().calcularSinalSaida(frequencia, canal, frequenciaDeCorteInf, frequenciaDeCorteSup);
                respostaModuloCanal = CalculoOndaQuadrada.getInstance().getModuloDaRespostaEmFrequencia();
                respostaFaseCanal = CalculoOndaQuadrada.getInstance().getFaseDaRespostaEmFrequencia();
                amplitudePorFrequenciaEntrada = CalculoOndaQuadrada.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaEntrada = CalculoOndaQuadrada.getInstance().getFasePorFrequenciaEntrada();
                amplitudePorFrequenciaSaida = CalculoOndaQuadrada.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaSaida = CalculoOndaQuadrada.getInstance().getFasePorFrequenciaEntrada();
            }
            case TRIANGULAR -> {
                sinaisEntrada = CalculoOndaTriangular.getInstance().calcularSinalEntrada(frequencia);
                sinaisSaida = CalculoOndaTriangular.getInstance().calcularSinalSaida(frequencia, canal, frequenciaDeCorteInf, frequenciaDeCorteSup);
                respostaModuloCanal = CalculoOndaTriangular.getInstance().getModuloDaRespostaEmFrequencia();
                respostaFaseCanal = CalculoOndaTriangular.getInstance().getFaseDaRespostaEmFrequencia();
                amplitudePorFrequenciaEntrada = CalculoOndaTriangular.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaEntrada = CalculoOndaTriangular.getInstance().getFasePorFrequenciaEntrada();
                amplitudePorFrequenciaSaida = CalculoOndaTriangular.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaSaida = CalculoOndaTriangular.getInstance().getFasePorFrequenciaEntrada();
            }
            case DENTEDESERRA -> {
                sinaisEntrada = CalculoOndaDenteSerra.getInstance().calcularSinalEntrada(frequencia);
                sinaisSaida = CalculoOndaDenteSerra.getInstance().calcularSinalSaida(frequencia, canal, frequenciaDeCorteInf, frequenciaDeCorteSup);
                respostaModuloCanal = CalculoOndaDenteSerra.getInstance().getModuloDaRespostaEmFrequencia();
                respostaFaseCanal = CalculoOndaDenteSerra.getInstance().getFaseDaRespostaEmFrequencia();
                amplitudePorFrequenciaEntrada = CalculoOndaDenteSerra.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaEntrada = CalculoOndaDenteSerra.getInstance().getFasePorFrequenciaEntrada();
                amplitudePorFrequenciaSaida = CalculoOndaDenteSerra.getInstance().getAmplitudePorFrequenciaEntrada();
                fasePorFrequenciaSaida = CalculoOndaDenteSerra.getInstance().getFasePorFrequenciaEntrada();
            }
            default -> {
                sinaisEntrada = new LinkedHashMap<>();
                sinaisSaida = new LinkedHashMap<>();
                respostaModuloCanal = new LinkedHashMap<>();
                respostaFaseCanal = new LinkedHashMap<>();
                amplitudePorFrequenciaEntrada = new LinkedHashMap<>();
                fasePorFrequenciaEntrada = new LinkedHashMap<>();
                amplitudePorFrequenciaSaida = new LinkedHashMap<>();
                fasePorFrequenciaSaida = new LinkedHashMap<>();
            }
        };


        Map<String, Object> response = new HashMap<>();
        response.put("sinaisEntrada", sinaisEntrada);
        response.put("sinaisSaida", sinaisSaida);
        response.put("respostaModuloCanal", respostaModuloCanal);
        response.put("respostaFaseCanal", respostaFaseCanal);
        response.put("amplitudePorFrequenciaEntrada", amplitudePorFrequenciaEntrada);
        response.put("fasePorFrequenciaEntrada", fasePorFrequenciaEntrada);
        response.put("amplitudePorFrequenciaSaida", amplitudePorFrequenciaSaida);
        response.put("fasePorFrequenciaSaida", fasePorFrequenciaSaida);

        return ResponseEntity.ok(response);
    }

}
