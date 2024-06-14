package webapp.locadoracarros.Controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import webapp.locadoracarros.Model.Carros;
import webapp.locadoracarros.Model.Clientes;
import webapp.locadoracarros.Model.Reservas;
import webapp.locadoracarros.Repository.CarrosRepository;
import webapp.locadoracarros.Repository.ClientesRepository;
import webapp.locadoracarros.Repository.ReservasRepository;
import org.springframework.web.bind.annotation.GetMapping;


@Controller
public class IndexController {
    
    private final ReservasRepository reservasRepository;
    private final ClientesRepository clientesRepository;
    private final CarrosRepository carrosRepository;

    public IndexController(ReservasRepository reservasRepository, 
                           ClientesRepository clientesRepository,
                           CarrosRepository carrosRepository) {
        this.reservasRepository = reservasRepository;
        this.clientesRepository = clientesRepository;
        this.carrosRepository = carrosRepository;
    }

    @GetMapping("/")
    public String index() {
        return "index";
    }

    @GetMapping("/lista-reservas")
    public String listarreservas(Model model) {
        model.addAttribute("reservas", reservasRepository.findAll());
        return "internas/lista-reservas";
    }

    @GetMapping("/clientes")
    public String clientes() {
        return "internas/clientes";
    }

    @GetMapping("/carros")
    public String carros() {
        return "internas/carros";
    }

    @GetMapping("/reservas")
    public String showReservaForm(Model model) {
        List<Clientes> clientes = (List<Clientes>) clientesRepository.findAll();
        List<Carros> carros = (List<Carros>) carrosRepository.findAll();

        model.addAttribute("clientes", clientes);
        model.addAttribute("carros", carros);
        model.addAttribute("reserva", new Reservas());

        return "internas/reservas";
    }

    @GetMapping("/lista-clientes")
    public String listClientes(Model model) {
        List<Clientes> clientes = (List<Clientes>) clientesRepository.findAll();

        model.addAttribute("clientes", clientes);

        return "internas/lista-clientes";
    }
    
}
