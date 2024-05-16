package br.cefsa.edu.PBLEC7MacroWave.Controller;
import br.cefsa.edu.PBLEC7MacroWave.Model.*;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
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

        Map<Double, Double> sinaisEntrada =  new LinkedHashMap<>();
        switch (categoria) {
            case SENOIDAL -> sinaisEntrada = CalculoOndaSenoidal.getInstance().calcularOnda(frequencia);
            case QUADRADA -> sinaisEntrada = CalculoOndaQuadrada.getInstance().calcularOnda(frequencia);
            case TRIANGULAR -> sinaisEntrada = CalculoOndaTriangular.getInstance().calcularOnda(frequencia);
            case DENTEDESERRA -> sinaisEntrada = CalculoOndaDenteSerra.getInstance().calcularOnda(frequencia);
            default -> sinaisEntrada = new LinkedHashMap<>();
        };


        Map<String, Object> response = new HashMap<>();
        response.put("sinaisEntrada", sinaisEntrada);

        return ResponseEntity.ok(response);
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
