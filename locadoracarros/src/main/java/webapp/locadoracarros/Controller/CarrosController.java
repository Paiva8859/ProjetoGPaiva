package webapp.locadoracarros.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Repository.CarrosRepository;

@Controller
public class CarrosController {

    private final CarrosRepository carrosRepository;

    public CarrosController(CarrosRepository carrosRepository) {
        this.carrosRepository = carrosRepository;
    }

    @PostMapping("/cadastrar-carro")
    public String cadastrarCarro(@ModelAttribute Carros carro) {
        carrosRepository.save(carro);
        return "index";
    }

    @GetMapping("/lista-carros-disponiveis")
    public String listaCarrosDisponiveis(Model model) {
        List<Carros> carrosDisponiveis = carrosRepository.findByDisponivelTrue();
        model.addAttribute("carros", carrosDisponiveis);
        return "internas/lista-carros-disponiveis";
    }
}
