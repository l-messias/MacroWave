package br.cefsa.edu.PBLEC7MacroWave.Controller;
import br.cefsa.edu.PBLEC7MacroWave.Model.CalculoOnda;
import br.cefsa.edu.PBLEC7MacroWave.Model.Canal;
import br.cefsa.edu.PBLEC7MacroWave.Model.Categoria;
import br.cefsa.edu.PBLEC7MacroWave.Model.Onda;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.HashMap;
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

        // Calcula os sinais e demais dados usando os parâmetros enviados pelo usuário
        Map<Double, Double> sinaisEntrada = CalculoOnda.calcularOnda(categoria, canal, frequencia, frequenciaDeCorteSup, frequenciaDeCorteInf);


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
