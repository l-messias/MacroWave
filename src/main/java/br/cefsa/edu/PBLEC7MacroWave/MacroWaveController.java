package br.cefsa.edu.PBLEC7MacroWave;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Controller
public class MacroWaveController {

    private Onda onda =  new Onda(1, 20, 2000, 90, "quadrada");
    private Onda onda2 =  new Onda(2, 40, 1000, 90, "senoide");
    private Map<Integer, Onda> ondas = new HashMap<>();
    {
        ondas.put(onda.getId(), onda);
        ondas.put(onda2.getId(), onda2);

    }


    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/wave")
    public String wave() {
        return "wave";
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
