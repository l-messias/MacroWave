package br.cefsa.edu.PBLEC7MacroWave.Controller;

import br.cefsa.edu.PBLEC7MacroWave.Model.Canal;
import br.cefsa.edu.PBLEC7MacroWave.Model.Categoria;
import br.cefsa.edu.PBLEC7MacroWave.Model.Onda;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MacroWaveController {


    @ModelAttribute("novaOnda")
    public Onda novaOnda() {
        return new Onda();
    }

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

    @PostMapping("/")
    public String postOnda(@RequestParam("categoria-select") Categoria categoria,
                           @RequestParam("frequencia") int frequencia,
                           @RequestParam("canal-select") Canal canal,
                           @RequestParam("frequencia-corte-sup") int frequenciaDeCorteSup,
                           @RequestParam(value = "frequencia-corte-inf", required = false) Integer frequenciaDeCorteInf,
                           Model model) {

        // Cria um novo objeto Onda e configura seus atributos com os parâmetros do formulário
        Onda onda = new Onda();
        onda.setCategoria(categoria);
        onda.setFrequencia(frequencia);
        onda.setCanal(canal);
        onda.setFrequenciaDeCorteSup(frequenciaDeCorteSup);
        if (frequenciaDeCorteInf != null) {
            onda.setFrequenciaDeCorteInf(frequenciaDeCorteInf);
        }

        // Calcula os sinais e demais dados usando os métodos da classe Onda
        List<Double> sinalEntrada = onda.calcularSinalEntrada(1000, 0.001);
        List<Double> amplitudes = onda.calcularAmplitudeEntrada(1000, 0.001);
        List<Double> fases = onda.calcularFaseEntrada(1000, 0.001);
        List<Double> moduloResposta = onda.calcularModuloRespostaFrequencia();
        List<Double> faseResposta = onda.calcularFaseRespostaFrequencia();
        List<Double> amplitudesSaida = onda.calcularAmplitudeSaida(1000, 0.001);
        List<Double> fasesSaida = onda.calcularFaseSaida(1000, 0.001);
        List<Double> sinalRecebido = onda.calcularSinalRecebido(1000, 0.001);

        // Adiciona os dados ao Model
        model.addAttribute("sinalEntrada", sinalEntrada);
        model.addAttribute("amplitudes", amplitudes);
        model.addAttribute("fases", fases);
        model.addAttribute("moduloResposta", moduloResposta);
        model.addAttribute("faseResposta", faseResposta);
        model.addAttribute("amplitudesSaida", amplitudesSaida);
        model.addAttribute("fasesSaida", fasesSaida);
        model.addAttribute("sinalRecebido", sinalRecebido);

        return "onda";
    }

    /*
    @GetMapping("/wave")
    public Collection<Onda> listarOndas() {
        return ondas.values();
    }

    @GetMapping("/wave/{id}")
    public Onda getOnda(@PathVariable int id) {
        Onda onda = ondas.get(id);
        if(onda == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        return onda;
    }

    @DeleteMapping("/wave/{id}")
    public void deleteOnda(@PathVariable int id) {
        Onda onda = ondas.get(id);
        if(onda == null)
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        ondas.remove(id);
    }

    @PostMapping("/wave")
    public Onda postOnda(@RequestBody Onda onda) {
        onda.setId(ondas.size() + 1);
        ondas.put(onda.getId(), onda);
        return onda;
    }

     */
}
